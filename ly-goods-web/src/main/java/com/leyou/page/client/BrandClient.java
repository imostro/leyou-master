package com.leyou.page.client;

import com.leyou.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: Gray
 */
@FeignClient("item-service")
public interface BrandClient extends BrandApi {
}
