package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: Gray
 */
@Repository
public interface BrandMapper extends Mapper<Brand>, SelectByIdListMapper<Brand, Long> {

    /**
     * 把商品的分类插入关系表中
     * @param cid
     * @param bid
     */
    @Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES (#{cid},#{bid}) ")
    void insertCategoryAndBrand(@Param("cid") Long cid, @Param("bid") Long bid);

    /**
     * 根据商品id删除分类id
     * @param bid
     */
    @Delete("DELETE from tb_category_brand where brand_id = #{bid}")
    void deleteCidByBid(@Param("bid")Long bid);

    /**
     * 根据分类id查询商品id
     * @param cid
     * @return int
     */
    @Select("SELECT  brand_id FROM tb_category_brand where category_id = #{cid}")
    List<Long> queryBrandIdByCategoryId(@Param("cid") Long cid);
}
