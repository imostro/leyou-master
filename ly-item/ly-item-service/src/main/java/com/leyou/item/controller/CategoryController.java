package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * @Author: Gray
 */

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     *  根据分类的父Id查询分类
    * @param pid
     * @return
     */
    @GetMapping(path = "list")
    public ResponseEntity<List<Category>> queryCategoryListByParentId(@RequestParam(name = "pid", defaultValue = "0")Long pid){

        try{
            if(pid == null || pid < 0){
                return ResponseEntity.badRequest().build();
            }

            List<Category> categories = categoryService.queryCategoryListByParentId(pid);
            if(CollectionUtils.isEmpty(categories)){
                ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(categories);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 根据品牌信息查询商品分类
     * @param id
     * @return
     */
    @GetMapping("bid/{id}")
    public ResponseEntity<List<Category>> queryCategoryByBid(@PathVariable Long id){
        List<Category> categories = categoryService.queryCategoryByBid(id);
        return ResponseEntity.ok(categories);
    }
    /**
     * @param ids
     * @return
             */
    @GetMapping("name")
    public List<String> queryNameByIds(@RequestParam("ids")List<Long> ids){
        return categoryService.queryCategoryNameByCids(ids);
    }
}
