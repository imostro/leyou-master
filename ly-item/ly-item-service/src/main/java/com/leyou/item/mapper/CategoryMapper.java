package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: Gray
 */

@Repository
public interface CategoryMapper extends Mapper<Category>, SelectByIdListMapper<Category, Long> {

    /**
     * 根据商品id查询分类id
     * @param bid
     * @return
     */
    @Select("select category_id from tb_category_brand where brand_id = #{bid}")
    List<Long> queryCidByBid(@Param("bid") Long bid);

}
