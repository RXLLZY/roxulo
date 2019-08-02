package com.swt.modules.sys.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色与部门对应关系
 *
 * @author RoXuLo
 * @email @shuweitech.com
 * @date 2017年6月21日 23:28:13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_role_dept")
public class SysRoleDeptEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@TableId
	private Long id;

	/**
	 * 角色ID
	 */
	private Long roleId;

	/**
	 * 部门ID
	 */
	private Long deptId;

}
