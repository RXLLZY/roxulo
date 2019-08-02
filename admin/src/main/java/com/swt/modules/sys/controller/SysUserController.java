package com.swt.modules.sys.controller;


import com.swt.common.annotation.SysLog;
import com.swt.common.controller.AbstractController;
import com.swt.common.responses.Responses;
import com.swt.common.utils.PageUtils;
import com.swt.common.utils.R;
import com.swt.common.validator.Assert;
import com.swt.common.validator.ValidatorUtils;
import com.swt.common.validator.group.AddGroup;
import com.swt.common.validator.group.UpdateGroup;
import com.swt.modules.sys.entity.SysUserEntity;
import com.swt.modules.sys.form.PasswordForm;
import com.swt.modules.sys.service.SysUserRoleService;
import com.swt.modules.sys.service.SysUserService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 
 * @author RoXuLo
 * @email @shuweitech.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public Responses<PageUtils> list(@RequestParam Map<String, Object> params){
		PageUtils page = sysUserService.queryPage(params);

		return success(page);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public Responses<SysUserEntity> info(){

		return success(getUser());
	}
	
	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@RequestMapping("/password")
	public Responses<Void> password(@RequestBody PasswordForm form){
		Assert.isBlank(form.getNewPassword(), "新密码不为能空");

		//sha256加密
		String password = new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex();
		//sha256加密
		String newPassword = new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex();

		//更新密码
		boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(!flag){
			R.error("原密码不正确");
		}

		return success();
	}
	
	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public Responses<SysUserEntity> info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.getById(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);

		return success(user);
	}
	
	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public Responses<SysUserEntity> save(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, AddGroup.class);
		
		sysUserService.save(user);

		return success(user);
	}
	
	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public Responses<SysUserEntity> update(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);

		sysUserService.update(user);

		return success(user);
	}
	
	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public Responses<Void> delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			R.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			R.error("当前用户不能删除");
		}

		sysUserService.deleteBatchIds(Arrays.asList(userIds));

		return success(HttpStatus.NO_CONTENT);
	}
}
