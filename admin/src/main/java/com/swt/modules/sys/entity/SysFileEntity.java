package com.swt.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 静态资源
 * 
 * @author RoXuLo
 * @email shuweitech.com
 * @date 2018-09-19 18:11:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
