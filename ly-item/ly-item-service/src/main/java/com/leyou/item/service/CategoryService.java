package com.leyou.item.service;

import com.leyou.item.pojo.Category;

import java.util.List;

/**
 * @Author: Gray
 */
public interface CategoryService {

    /**
     * 根据分类Id查询分类商品
     * @param pid
     * @return
     */
    List<Category> queryCategoryListByParentId(long pid);

    /**
     * 根据品牌id查询商品分类
     * @param id
     * @return
     */
    List<Category> queryCategoryByBid(Long id);

    /**
     * 根据分类Id查询分类名
    * @param cids
     * @return
     */
    List<String> queryCategoryNameByCids(List<Long> cids);

}
