package com.leyou.item.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBO;
import com.leyou.item.mapper.*;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.pojo.Stock;
import com.leyou.item.service.CategoryService;
import com.leyou.item.service.GoodsService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author: Gray
 */

@Log4j2
@Service
public class GoodsServiceImpl implements GoodsService {


    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public PageResult<SpuBO> queryAllGoods(String key, Boolean saleable, Integer page, Integer rows) {

        PageHelper.startPage(page, rows);

        Example example = new Example(Spu.class);

        if(StringUtils.isNotEmpty(key)){
            example.createCriteria().andLike("title", "%" + key +"%");
        }

        if(saleable != null){
            example.createCriteria().andEqualTo("saleable", saleable);
        }

        Page<Spu> pageInfo =(Page<Spu>) spuMapper.selectByExample(example);

        //把这其中得spu变成spubo

        List<SpuBO> list = new ArrayList<>();

        //以下操作就是把spu转为SpuBO

        for (Spu spu : pageInfo.getResult()) {
            SpuBO spuBO = new SpuBO();

            //把spu中的所有值赋值给spuBO
            BeanUtils.copyProperties(spu,spuBO);

            //把spu中的品牌id取出，执行查询，把查到的品牌对象的名称赋值给spuBO
            spuBO.setBname(brandMapper.selectByPrimaryKey(spu.getBrandId()).getName());

            List<String> names = categoryService.queryCategoryNameByCids(Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3()));

            String join = StringUtils.join(names, "/");

            spuBO.setCname(join);

            list.add(spuBO);

        }

        return new PageResult<>(pageInfo.getTotal(),list);
    }

    @Override
    @Transactional

    public void saveGoods(SpuBO spuBO) {

        //保存spu
        spuBO.setSaleable(true);
        spuBO.setValid(true);
        spuBO.setCreateTime(new Date());
        spuBO.setLastUpdateTime(new Date());
        spuMapper.insert(spuBO);

        //保存sku
        saveSkuAndStock(spuBO.getSkus(), spuBO.getId());

        //发送消息
        this.sendMessage(spuBO.getId(), "insert");
    }
    @Override
    public Spu querySpuById(Long id) {
        return spuMapper.selectByPrimaryKey(id);
    }

    private void saveSkuAndStock(List<Sku> skus, Long spuId){
        skus.forEach(sku->{
            sku.setSpuId(spuId);
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(new Date());
            skuMapper.insert(sku);

            Stock stock = new Stock();
            stock.setStock(sku.getStock());
            stock.setSkuId(sku.getId());
            stockMapper.insert(stock);
        });
    }

    @Override
    public List<Sku> querySkuBySpuId(Long id) {
        Sku sku = new Sku();
        sku.setSpuId(id);
        return skuMapper.select(sku);
    }

    @Override
    public SpuDetail querySpuDetailById(Long id) {

        return spuDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public Sku querySkuById(Long id) {
        return skuMapper.selectByPrimaryKey(id);
    }

    private void sendMessage(Long id, String type){
        //发送消息
        try{
            this.amqpTemplate.convertAndSend("item." + type, id);
        }catch (Exception e){
            log.error("{}商品消息发送异常，商品id：{}", type, id, e);
        }
    }
}
