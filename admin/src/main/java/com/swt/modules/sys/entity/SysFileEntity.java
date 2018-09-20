package com.swt.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;

/**
 * 静态资源
 * 
 * @author RoXuLo
 * @email shuweitech.com
 * @date 2018-09-19 18:11:12
 */
@TableName("sys_file")
@ApiModel(value="静态资源")
public class SysFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	@ApiModelProperty(value="主键", example="Integer", hidden=true)
	private Integer id;
	/**
	 * 原始名称
	 */
	@NotBlank(message="原始名称不能为空")
	@ApiModelProperty(value="原始名称", example="String", hidden=false,required=true)
	private String originalName;
	/**
	 * 文件路径
	 */
	@ApiModelProperty(value="文件路径", example="String", hidden=false,required=false)
	private String path;
	/**
	 * 文件类型
	 */
	@ApiModelProperty(value="文件类型", example="String", hidden=false,required=false)
	private String contentType;
	/**
	 * 文件大小
	 */
	@ApiModelProperty(value="文件大小", example="String", hidden=false,required=false)
	private String size;
	/**
	 * 描述
	 */
	@ApiModelProperty(value="描述", example="String", hidden=false,required=false)
	private String description;
	/**
	 * 创建者
	 */
	@ApiModelProperty(value="创建者", example="Long", hidden=true,required=false)
	private Long crtUserId;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value="创建时间", example="Date", hidden=true,required=false)
	private Date crtTime;

	/**
	 * 设置：主键
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：原始名称
	 */
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	/**
	 * 获取：原始名称
	 */
	public String getOriginalName() {
		return originalName;
	}
	/**
	 * 设置：文件路径
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * 获取：文件路径
	 */
	public String getPath() {
		return path;
	}
	/**
	 * 设置：文件类型
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	/**
	 * 获取：文件类型
	 */
	public String getContentType() {
		return contentType;
	}
	/**
	 * 设置：文件大小
	 */
	public void setSize(String size) {
		this.size = size;
	}
	/**
	 * 获取：文件大小
	 */
	public String getSize() {
		return size;
	}
	/**
	 * 设置：描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：描述
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置：创建者
	 */
	public void setCrtUserId(Long crtUserId) {
		this.crtUserId = crtUserId;
	}
	/**
	 * 获取：创建者
	 */
	public Long getCrtUserId() {
		return crtUserId;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCrtTime() {
		return crtTime;
	}
}
