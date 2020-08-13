package com.leyou.cart.service;

import com.leyou.auth.entity.UserInfo;
import com.leyou.cart.entity.Cart;
import com.leyou.cart.interceptor.LoginInterceptor;
import com.leyou.client.GoodsClient;
import com.leyou.common.utils.JsonUtils;
import com.leyou.item.pojo.Sku;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: Gray
 */
@Log4j2
@Service
public class CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private GoodsClient goodsClient;

    static final String KEY_PREFIX = "ly:cart:uid:";

    public void addCart(Cart cart) {
        //获取登录用户
        UserInfo user = LoginInterceptor.getLoginUser();
        //Redis的key
        String key = KEY_PREFIX + user.getId();
        //获取hash操作对象
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);
        //查询是否存在
        Long skuId = cart.getSkuId();
        Integer num = cart.getNum();
        Boolean boo = hashOps.hasKey(skuId.toString());
        if(boo){
            //存在，获取购物车数据
            String json = Objects.requireNonNull(hashOps.get(skuId.toString())).toString();
            cart = JsonUtils.parse(json, Cart.class);
            //修改 购物车数量
            cart.setNum(cart.getNum() + num);
        }else{
            //不存在，新增购物车数据
            cart.setUserId(user.getId());
            //其他商品信息，需要从查询商品服务
            Sku sku = this.goodsClient.querySkuById(skuId);
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
            cart.setPrice(sku.getPrice());
            cart.setTitle(sku.getTitle());
            cart.setOwnSpec(sku.getOwnSpec());
        }
        //将购物车数据写到redis
        hashOps.put(cart.getSkuId().toString(), JsonUtils.serialize(cart));
    }

    public List<Cart> queryCart() {
        //获取登录用户信息
        UserInfo user = LoginInterceptor.getLoginUser();

        //判断是否存在购物车
        String key = KEY_PREFIX + user.getId();
        if(!this.redisTemplate.hasKey(key)){
            //不存在，直接返回
            return null;
        }
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);
        List<Object> carts = hashOps.values();
        //判断是否有数据
        if(Collections.isEmpty(carts)){
            return null;
        }
        //查询购物车数据
        return carts.stream().map(o -> JsonUtils.parse(o.toString(), Cart.class)).collect(Collectors.toList());
    }

    public void updateNum(Long skuId, Integer num) {
        //查询当前skuId对应的购物车对象，然后该具体的数值
        //改完后继续要存入redis

        // 获取登录用户
        UserInfo user = LoginInterceptor.getLoginUser();
        String key = KEY_PREFIX + user.getId();
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);
        // 获取购物车
        String json = hashOps.get(skuId.toString()).toString();
        Cart cart = JsonUtils.parse(json, Cart.class);
        cart.setNum(num);
        // 写入购物车
        hashOps.put(skuId.toString(), JsonUtils.serialize(cart));

    }

    public void deleteCart(Long skuId) {
        //获取用户信息
        UserInfo user = LoginInterceptor.getLoginUser();
        String key = KEY_PREFIX + user.getId();
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);
        hashOps.delete(skuId.toString());
    }
}
