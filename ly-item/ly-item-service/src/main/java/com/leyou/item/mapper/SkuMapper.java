package com.leyou.item.mapper;

import com.leyou.item.pojo.Sku;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author: Gray
 */
@Repository
public interface SkuMapper extends Mapper<Sku>, SelectByIdListMapper<Sku, Long> {
}
