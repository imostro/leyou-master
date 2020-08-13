package com.leyou.item.service.impl;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Gray
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> queryCategoryListByParentId(long pid) {
        Category category = new Category();
        category.setParentId(pid);
        return categoryMapper.select(category);
    }


    @Override
    public List<Category> queryCategoryByBid(Long bid) {

        List<Long> cids = categoryMapper.queryCidByBid(bid);

        List<Category> categories = categoryMapper.selectByIdList(cids);

        return categories;
    }

    @Override
    public List<String> queryCategoryNameByCids(List<Long> cids) {

        List<Category> categoryList = categoryMapper.selectByIdList(cids);

        List<String> names = new ArrayList<>();

        for (Category category : categoryList) {
            names.add(category.getName());
        }


        return names ;
    }
}
