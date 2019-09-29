package com.swt.modules.sys.controller;


import com.swt.common.annotation.SysLog;
import com.swt.common.controller.AbstractController;
import com.swt.common.responses.Responses;
import com.swt.common.utils.PageInfo;
import com.swt.common.utils.PageUtils;
import com.swt.common.validator.ValidatorUtils;
import com.swt.modules.sys.entity.SysConfigEntity;
import com.swt.modules.sys.service.ISysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 系统配置信息
 * 
 * @author RoXuLo
 * @email @shuweitech.com
 * @date 2016年12月4日 下午6:55:53
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {
	@Autowired
	private ISysConfigService sysConfigService;
	
	/**
	 * 所有配置列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:config:list")
	public Responses<PageUtils> list(PageInfo pageInfo, String paramKey){
		PageUtils page = sysConfigService.queryPage(pageInfo, paramKey);

		return success(page);
	}
	
	
	/**
	 * 配置信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:config:info")
	public Responses<SysConfigEntity> info(@PathVariable("id") Long id){
		SysConfigEntity config = sysConfigService.getById(id);

		return success(config);
	}
	
	/**
	 * 保存配置
	 */
	@SysLog("保存配置")
	@RequestMapping("/save")
	@RequiresPermissions("sys:config:save")
	public Responses<SysConfigEntity> save(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);

		sysConfigService.save(config);

		return success(HttpStatus.CREATED, config);
	}
	
	/**
	 * 修改配置
	 */
	@SysLog("修改配置")
	@RequestMapping("/update")
	@RequiresPermissions("sys:config:update")
	public Responses<SysConfigEntity> update(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);
		
		sysConfigService.update(config);

		return success(config);
	}
	
	/**
	 * 删除配置
	 */
	@SysLog("删除配置")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:config:delete")
	public Responses<Void> delete(@RequestBody Long[] ids){
		sysConfigService.deleteBatch(ids);

		return success(HttpStatus.NO_CONTENT);
	}

}
