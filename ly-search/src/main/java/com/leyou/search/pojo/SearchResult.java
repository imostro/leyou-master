package com.leyou.search.pojo;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @Author: Gray
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult extends PageResult<Goods> {

    /**
     * 商品分类信息
     */
    private List<Category> categories;

    /**
     * 品牌信息
     */
    private List<Brand> brands;

    /**
     * 规格参数过滤条件
     */
    private List<Map<String, Object>> specs;

    public SearchResult(long total, long totalPages, List<Goods> content, List<Category> categories, List<Brand> brands, List<Map<String, Object>> specs) {
        super(total, totalPages, content);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }
}
