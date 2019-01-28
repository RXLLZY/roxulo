package com.swt.entity;

import java.util.Date;

/**
 * 表数据
 * 
 * @author RoXuLo
 * @email @shuweitech.com
 * @date 2016年12月20日 上午12:02:55
 */
public class TablePropertiesEntity {
	/**
	 *表的名称
	 */
	private String tableName;
	/**
	 * 引擎
	 */
	private String engine;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 *表的备注
	 */
	private String tableComment;
	/**
	 * 模块名称
	 */
	private String moduleName;
	/**
	 * 表前缀
	 */
	private String tablePrefix;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getTablePrefix() {
		return tablePrefix;
	}

	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}
}
