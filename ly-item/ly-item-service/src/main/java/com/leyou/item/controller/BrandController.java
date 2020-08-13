package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
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
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping(value = "page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(name = "key",required = false)String  key,
            @RequestParam(name = "page", defaultValue = "1")Integer page,
            @RequestParam(name = "rows", defaultValue = "5")Integer rows,
            @RequestParam(name = "sortBy", required = false)String sortBy,
            @RequestParam(name = "desc", defaultValue = "false") Boolean desc
    ){
        PageResult<Brand> brandPageResult = brandService.queryBrandByPageAndSort(page, rows, sortBy, desc, key);
        if (brandPageResult == null || brandPageResult.getItems().size() == 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brandPageResult);
    }


    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand,@RequestParam(name = "cids") List<Long> cids){
        brandService.saveBrand(brand, cids);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateBrand(Brand brand, @RequestParam(name = "cids") List<Long> cids){
        brandService.updateBrand(brand, cids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{bid}")
    public ResponseEntity<Void> deleteBrand(@PathVariable(name = "bid")Long bid){
        brandService.deleteBrandByBid(bid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCategoryId(@PathVariable("cid") Long cid){
        List<Brand> brands = brandService.queryBrandByCategoryId(cid);

        if(brands.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(brands);
    }

    @GetMapping(path = "list")
    public ResponseEntity<List<Brand>> queryBrandByIds(@RequestParam(name = "brandIds") List<Long> brandIds){
        List<Brand> brands = brandService.queryBrandByIds(brandIds);
        if(CollectionUtils.isEmpty(brands)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(brands);
    }
}
