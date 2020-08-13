package com.leyou.common.pojo;

import lombok.*;

import java.util.List;

/**
 * @Author: Gray
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    private Long total;

    private Long totalPage;

    private List<T> items;

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }
}
