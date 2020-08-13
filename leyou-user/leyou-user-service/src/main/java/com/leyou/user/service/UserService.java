package com.leyou.user.service;

import com.leyou.common.utils.NumberUtils;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.user.utils.CodecUtils;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    static final String KEY_PREFIX = "user:code:phone:";

    //要检验的数据内容，type为校验种类，2，手机，1,代表用户名
    //没有查到返回true，查到返回false
    public Boolean checkData(String data, Integer type) {
        User user = new User();
        if (1 == type) {
            user.setUsername(data);
        } else if (2 == type) {
            user.setPhone(data);
        }

        return this.userMapper.selectCount(user) == 0;
    }

    //要发送短信
    public Boolean sendSms(String phone) {

        String code = NumberUtils.generateCode(6);

        try {
            //生成一个随机的验证码
            Map<String, String> msg = new HashMap<>();
            msg.put("code", code);
            msg.put("phone", phone);
            amqpTemplate.convertAndSend("ly.sms.exchange", "sms.verify.code", msg);

            //把数据存入redis并且5min有效
            this.redisTemplate.opsForValue().set("ly." + phone, code, 5, TimeUnit.MINUTES);
            return true;
        } catch (AmqpException e) {
            log.error("发送短信失败。phone：{}， code：{}", phone, code);
            return false;
        }
    }


    /**
     * 首先根据phone去redis中取值，取完之后校验，校验成功继续
     *
     * @param user
     * @param code
     * @return
     */
    public Boolean register(User user, String code) {

        //没有输入验证码不做操作
        if (StringUtils.isBlank(code)) {
            return false;
        }

        String key = "ly." + user.getPhone();
        //存在服务器中的验证码
        String storeCode = redisTemplate.opsForValue().get(key);

        if (!code.equals(storeCode)) {
            return false;
        }
        //所有的校验都完成之后可以做数据保存

        String salt = CodecUtils.generateSalt();

        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));

        user.setSalt(salt);

        user.setId(null);
        user.setCreated(new Date());

        boolean boo = this.userMapper.insertSelective(user) == 1;
        if(boo){
            try {
                this.redisTemplate.delete(key);
            }catch (Exception e){
                 log.error("删除缓存验证码失败, code: {}", code, e);
            }
        }

        return boo;
    }

    /**
     * 根据用户名和密码查询，由于密码加盐加密无法直接查询
     * 依据用户名去查询，如果有，所有的对应的数据都能9被查出，取出盐值，进行输入的密码加密，和查询出来的比对
     * @param user
     * @return
     */
    public User queryUserByUsernameAndPassword(String username,String passwrod) {
        User queryUser = new User();
        queryUser.setUsername(username);

        User storeUser = this.userMapper.selectOne(queryUser);

        if (null==storeUser){
            return null;
        }

        String password = CodecUtils.md5Hex(passwrod,storeUser.getSalt());

        if (password.equals(storeUser.getPassword())){
            return storeUser;
        }

        return null;
    }
}