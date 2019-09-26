package com.swt.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swt.common.annotation.DataFilter;
import com.swt.common.utils.Constant;
import com.swt.modules.sys.dao.SysDeptDao;
import com.swt.modules.sys.entity.SysDeptEntity;
import com.swt.modules.sys.service.SysDeptService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {
	
	@Override
	@DataFilter(subDept = true, user = false)
	public List<SysDeptEntity> queryList(Map<String, Object> params){
		List<SysDeptEntity> deptList =
			this.list(new QueryWrapper<SysDeptEntity>()
			.apply(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER)));

		for(SysDeptEntity sysDeptEntity : deptList){
			SysDeptEntity parentDeptEntity =  this.getById(sysDeptEntity.getParentId());
			if(parentDeptEntity != null){
				sysDeptEntity.setParentName(parentDeptEntity.getName());
			}
		}
		return deptList;
	}

	@Override
	public List<Long> queryDetpIdList(Long parentId) {
		return baseMapper.queryDetpIdList(parentId);
	}

	@Override
	public List<Long> getSubDeptIdList(Long deptId){
		//部门及子部门ID列表
		List<Long> deptIdList = new ArrayList<>();

		//获取子部门ID
		List<Long> subIdList = queryDetpIdList(deptId);
		getDeptTreeList(subIdList, deptIdList);

		return deptIdList;
	}

	/**
	 * 递归
	 */
	private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList){
		for(Long deptId : subIdList){
			List<Long> list = queryDetpIdList(deptId);
			if(list.size() > 0){
				getDeptTreeList(list, deptIdList);
			}

			deptIdList.add(deptId);
		}
	}
}
