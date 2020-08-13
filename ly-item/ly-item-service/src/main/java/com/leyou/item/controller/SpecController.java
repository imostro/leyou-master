package com.leyou.item.controller;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecService;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("spec")
public class SpecController {

    @Autowired
    private SpecService specService;


    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecByCid(@PathVariable("cid") Long cid){
        List<SpecGroup> specGroupList = specService.querySpecGroupNameByCategoryId(cid);
        if(CollectionUtils.isEmpty(specGroupList)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(specGroupList);
    }

    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> querySpecParamByGid(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "searching", required = false) Boolean searching,
            @RequestParam(value = "generic", required = false) Boolean generic) {

        List<SpecParam> specParamList = specService.querySpecParamByGid(gid,cid,searching,generic);

        if (null == specParamList || 0 == specParamList.size()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(specParamList);
    }

    @PutMapping("group")
    public ResponseEntity<Void> updateGroupName(@RequestBody SpecGroup specGroup){

        if(StringUtils.isEmpty(specGroup.getName())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        specService.updateGroupName(specGroup);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("group")
    public ResponseEntity<Void> saveGroupName(@RequestBody SpecGroup specGroup){
        if(StringUtils.isEmpty(specGroup.getName()) || specGroup.getCid() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        specService.saveGroupName(specGroup);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("group/{gid}")
    public ResponseEntity<Void> deleteSpecGroupByGroupId(@PathVariable("gid")Long gid){
        if(gid == null || gid.longValue()<=0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        specService.deleteSpecGroupByGroupId(gid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("param")
    public ResponseEntity<Void> updateParamName(@RequestBody SpecParam specParam){

        if(StringUtils.isEmpty(specParam.getName())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        specService.updateParamName(specParam);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("param")
    public ResponseEntity<Void> saveParamName(@RequestBody SpecParam specParam){
        if(StringUtils.isEmpty(specParam.getName()) || specParam.getCid() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        specService.saveParamName(specParam);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("param/{id}")
    public ResponseEntity<Void> deleteSpecParamByParamId(@PathVariable("id")Long id){
        if(id == null || id <=0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        specService.deleteSpecParamByParamId(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("params/category")
    public ResponseEntity<List<SpecParam>> querySepcParamsByCategoryId(@RequestParam(name = "cid") Long cid){
        List<SpecParam> specParams = specService.querySpecParamsByCategoryId(cid);
        if(specParams.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(specParams);
    }


    @GetMapping("{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecsByCid(@PathVariable("cid") Long cid){
        List<SpecGroup> list = specService.querySpecsByCid(cid);
        if(list == null || list.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }


}
