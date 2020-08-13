package com.leyou.item.service;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;

import java.util.List;

/**
 * @Author: Gray
 */

public interface SpecService {

    /**
     * 根据分类Id查询规格模板
     * @param cid
     * @return List<SpecGroup>
     */
    List<SpecGroup> querySpecGroupNameByCategoryId(Long cid);

    /**
     *  根据分组Id查询Spec参数
     * @param gid
     * @return
     */
    List<SpecParam> querySpecParamsByGroupId(Long gid);

    /**
     * 更新SKU分组名字
     * @param specGroup
     */
    void updateGroupName(SpecGroup specGroup);

    /**
     * 保存新的分组
     * @param specGroup
     */
    void saveGroupName(SpecGroup specGroup);


    /**
     * 根据GroupId删除SpecGroup
     * @param gid
     */
    void deleteSpecGroupByGroupId(Long gid);

    /**
     * 更新SpecParam
     * @param specParam
     */
    void updateParamName(SpecParam specParam);

    /**
     * 保存新的SpecParam
     * @param specParam
     */
    void saveParamName(SpecParam specParam);

    /**
     * 根据ParamId删除SpecParam
     * @param id
     */
    void deleteSpecParamByParamId(Long id);


    /**
     * 根据分类Id查询SpecParams
     * @param cid
     * @return List<SpecParam>
     */
    List<SpecParam> querySpecParamsByCategoryId(Long cid);

    /**
     *  查找SpecParam
     * @param gid
     * @param cid
     * @param searching
     * @param generic
     * @return
     */
    List<SpecParam> querySpecParamByGid(Long gid, Long cid, Boolean searching, Boolean generic);

    /**
     * 根据分类Id查找分组信息
     * @param cid
     * @return
     */
    List<SpecGroup> querySpecsByCid(Long cid);
}
