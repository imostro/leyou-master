package com.leyou.item.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author: Gray
 */
@Service
public class BrandServiceImpl  implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageResult<Brand> queryBrandByPageAndSort(Integer page, Integer rows, String sortBy, Boolean desc, String key) {

        //开始分页
        PageHelper.startPage(page, rows);
        //过滤
        Example example = new Example(Brand.class);
        if(!StringUtils.isEmpty(key)){
            example.createCriteria().andLike("name","%"+key + "%")
                    .orEqualTo("letter", key);
        }

        //排序
        if(!StringUtils.isEmpty(sortBy)){
            String orderByClause = sortBy + (desc? " DESC": " ASC");
            example.setOrderByClause(orderByClause);
        }
        //查询
        Page<Brand> pageInfo = (Page<Brand>) brandMapper.selectByExample(example);
        //返回结果
        return new PageResult<>(pageInfo.getTotal(), pageInfo);
    }


    @Override
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        int i = brandMapper.insert(brand);

        cids.forEach(cid-> brandMapper.insertCategoryAndBrand(cid, brand.getId()));

    }

    @Override
    @Transactional
    public void updateBrand(Brand brand, List<Long> cids) {
        brandMapper.updateByPrimaryKey(brand);

        brandMapper.deleteCidByBid(brand.getId());

        cids.forEach(cid-> brandMapper.insertCategoryAndBrand(cid, brand.getId()));
    }

    @Override
    @Transactional
    public void deleteBrandByBid(Long bid) {
        brandMapper.deleteByPrimaryKey(bid);

        brandMapper.deleteCidByBid(bid);
    }

    @Override
    public List<Brand> queryBrandByCategoryId(Long cid) {
        List<Long> bids = brandMapper.queryBrandIdByCategoryId(cid);
        if(!CollectionUtils.isEmpty(bids)){
            return brandMapper.selectByIdList(bids);
        }
        return null;
    }

    @Override
    public List<Brand> queryBrandByIds(List<Long> bids) {
        return brandMapper.selectByIdList(bids);
    }
}
