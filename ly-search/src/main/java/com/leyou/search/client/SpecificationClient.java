package com.leyou.search.client;

import com.leyou.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author ASUS
 */
@FeignClient(value = "item-service")
public interface SpecificationClient extends SpecificationApi {
}