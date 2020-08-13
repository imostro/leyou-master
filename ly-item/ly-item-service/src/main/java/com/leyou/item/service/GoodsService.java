package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBO;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


/**
 * @Author: Gray
 */
public interface GoodsService {

    /**
     *  查询所有的商品信息
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return PageResult<Spu>
     */
    PageResult<SpuBO> queryAllGoods(String key, Boolean saleable, Integer page, Integer rows);

    /**
     * 保存商品信息
     * @param spuBO
     */
    void saveGoods(SpuBO spuBO);

    /**
     * 根据Id查找Spu
     * @param id
     * @return
     */
    Spu querySpuById(Long id);

    /**
     * 根据SpuId查找Sku
     * @param id
     * @return
     */
    List<Sku> querySkuBySpuId(Long id);

    /**
     * 根据SpuId查找SpuDetail
     * @param id
     * @return
     */
    SpuDetail querySpuDetailById(Long id);

    /**
     * 通过skuId查询sku
     * @param id
     * @return
     */
    Sku querySkuById(@PathVariable("id") Long id);

}
