package com.swt.entity;

/**
 * 列的属性
 * 
 * @author RoXuLo
 * @email @shuweitech.com
 * @date 2016年12月20日 上午12:01:45
 */
public class ColumnEntity {
	//列名
    private String columnName;
    //列名类型
    private String dataType;
    //列名备注
    private String comments;
    
    //属性名称(第一个字母大写)，如：user_name => UserName
    private String attrName;
    //属性名称(第一个字母小写)，如：user_name => userName
    private String attrname;
    //属性类型
    private String attrType;
	//属性类型小写
	private String attrtype;
    //auto_increment
    private String extra;
    //是否为空
    private Boolean isNull;
	//示例
	private String example;
	//是否隐藏
	private Boolean hidden;
	//是否可被搜索
	private Boolean search;
	//是否是资源
	private Boolean resource;
	//是否以is_开头
	private Boolean startIs;
	//字段长度
	private Long length;
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getAttrname() {
		return attrname;
	}
	public void setAttrname(String attrname) {
		this.attrname = attrname;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public String getAttrType() {
		return attrType;
	}
	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public Boolean getIsNull() {
		return isNull;
	}
	public void setIsNull(String isNull) {
		if("NO".equals(isNull)){
			this.isNull = true;
		}else{
			this.isNull = false;
		}
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public Boolean getSearch() {
		if(search == null){
			search = false;
		}
		return search;
	}

	public void setSearch(Boolean search) {
		this.search = search;
	}

	public Boolean getResource() {
		return resource;
	}

	public void setResource(Boolean resource) {
		this.resource = resource;
	}

	public String getAttrtype() {
		return attrtype;
	}

	public void setAttrtype(String attrtype) {
		this.attrtype = attrtype;
	}

	public Boolean getStartIs() {
		return startIs;
	}

	public void setStartIs(Boolean startIs) {
		this.startIs = startIs;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}
}
