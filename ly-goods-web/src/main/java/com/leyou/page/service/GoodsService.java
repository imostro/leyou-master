package com.leyou.page.service;


import java.util.Map;

/**
 * @Author: Gray
 */
public interface GoodsService {

    /**
     *
     * @param spuId
     * @return
     */
    Map<String, Object> loadData(Long spuId);
}
