package com.leyou.page.client;

import com.leyou.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: Gray
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {
}
