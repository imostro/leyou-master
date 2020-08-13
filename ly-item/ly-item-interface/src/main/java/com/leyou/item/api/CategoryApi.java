package com.leyou.item.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: Gray
 */
@RequestMapping("category")
public interface CategoryApi {

    /**
     * 根据分类Id返回分类名字
     * @param ids
     * @return
     */
    @GetMapping("name")
    List<String> queryNameByIds(@RequestParam("ids")List<Long> ids);
}
