package com.swt.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swt.modules.sys.entity.SysDeptEntity;

import java.util.List;

/**
 * 部门管理
 * 
 * @author RoXuLo
 * @email @shuweitech.com
 * @date 2017-06-20 15:23:47
 */
public interface SysDeptDao extends BaseMapper<SysDeptEntity> {

    /**
     * 查询子部门ID列表
     * @param parentId  上级部门ID
     */
    List<Long> queryDetpIdList(Long parentId);

}
