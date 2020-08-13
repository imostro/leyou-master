package com.leyou.client;

import com.leyou.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: Gray
 */
@FeignClient(value = "user-service")
public interface UserClient extends UserApi {

}
