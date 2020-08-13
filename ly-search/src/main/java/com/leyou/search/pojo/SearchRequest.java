package com.leyou.search.pojo;

import lombok.Data;
import lombok.Getter;

import java.util.Map;

/**
 * @Author: Gray
 */
@Data
public class SearchRequest {

    /**
     * 搜索条件
     */
    private String key;

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 排序方式
     */
    private String sortBy;

    /**
     * 升序或降序
     */
    private Boolean descending;

    /**
     * 过滤条件
     */
    private Map<String, String> filter;

    /**
     * 固定页面条数
     */
    private static final Integer DEFAULT_SIZE = 20;

    /**
     * 默认页
     */
    private static final Integer DEFAULT_PAGE = 1;

    public  Integer getSize() {
        return DEFAULT_SIZE;
    }

    public  Integer getPage() {
        if(this.page == null){
            return DEFAULT_PAGE;
        }
        return this.page;
    }
}
