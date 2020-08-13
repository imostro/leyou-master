package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;

import java.util.List;

/**
 * @Author: Gray
 */
public interface BrandService {

    /**
     * 根据商品
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    PageResult<Brand> queryBrandByPageAndSort(Integer page, Integer rows, String sortBy, Boolean desc, String key);

    /**
     * 保存商品
     * @param brand
     * @param cids
     */
    void saveBrand(Brand brand, List<Long> cids);


    /**
     * 更新商品
     * @param brand
     * @param cids
     */
    void updateBrand(Brand brand, List<Long> cids);

    /**
     * 根据商品Id删除商品信息
     * @param bid
     */
    void deleteBrandByBid(Long bid);


    /**
     * 根据分类Id查询品牌
     * @param cid
     * @return
     */
    List<Brand> queryBrandByCategoryId(Long cid);

    /**
     * 根据Ids查询商品
     * @param bids
     * @return
     */
    List<Brand> queryBrandByIds(List<Long> bids);
}
