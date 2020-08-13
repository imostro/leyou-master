package com.leyou.item.service.impl;

import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Gray
 */

@Service
public class SpecServiceImpl implements SpecService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;


    @Override
    public List<SpecGroup> querySpecGroupNameByCategoryId(Long cid) {

        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> specGroupList = specGroupMapper.select(specGroup);

        specGroupList.forEach(item->{
            SpecParam specParam = new SpecParam();
            specParam.setGroupId(item.getId());
            item.setParams(specParamMapper.select(specParam));
        });

        return specGroupList;
    }

    @Override
    public List<SpecParam> querySpecParamsByGroupId(Long gid) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);

        return specParamMapper.select(specParam);
    }

    @Override
    public void updateGroupName(SpecGroup specGroup) {
        specGroupMapper.updateByPrimaryKey(specGroup);
    }

    @Override
    public void saveGroupName(SpecGroup specGroup) {
        specGroupMapper.insert(specGroup);
    }

    @Override
    public void deleteSpecGroupByGroupId(Long gid) {
        specGroupMapper.deleteByPrimaryKey(gid);
    }

    @Override
    public void updateParamName(SpecParam specParam) {
        specParamMapper.updateByPrimaryKey(specParam);
    }

    @Override
    public void saveParamName(SpecParam specParam) {
        specParamMapper.insert(specParam);
    }

    @Override
    public void deleteSpecParamByParamId(Long id) {
        specParamMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<SpecParam> querySpecParamsByCategoryId(Long cid) {
        SpecParam specParam = new SpecParam();
        specParam.setCid(cid);
        return specParamMapper.select(specParam);
    }

    @Override
    public List<SpecParam> querySpecParamByGid(Long gid, Long cid, Boolean searching, Boolean generic) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        specParam.setGeneric(generic);

        return specParamMapper.select(specParam);
    }

    @Override
    public List<SpecGroup> querySpecsByCid(Long cid) {
        SpecGroup group = new SpecGroup();
        group.setCid(cid);
        List<SpecGroup> specGroups = specGroupMapper.select(group);
        specGroups.forEach(g->{
            g.setParams(querySpecParamsByGroupId(g.getId()));
        });
        return specGroups;
    }

}
