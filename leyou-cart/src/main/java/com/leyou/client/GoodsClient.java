package com.leyou.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: Gray
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
