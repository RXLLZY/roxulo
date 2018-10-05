package com.swt.common.datainput;

import java.io.Serializable;
import java.util.List;

public class DataHeadList implements Serializable {
	private static final long serialVersionUID = -7631122453595411126L;

	private String srcFileName;
	private String table_name;
	private String es_name;
	private String type;
	private String table_comment;
	private List<String> old_name;
	private List<String> new_name;
	private List<String> new_comment;
	private List<String> rules;
	private int creator;

	private String hdfsPath;

	private int[] selected;

	public int[] getSelected() {
		return selected;
	}

	public void setSelected(int[] Selected) {
		this.selected = Selected;
	}

	public String getEs_name() {
		return es_name;
	}

	public void setEs_name(String es_name) {
		this.es_name = es_name;
	}

	public List<String> getOld_name() {
		return old_name;
	}
	public void setOld_name(List<String> old_name) {
		this.old_name = old_name;
	}
	public List<String> getNew_name() {
		return new_name;
	}
	public void setNew_name(List<String> new_name) {
		this.new_name = new_name;
	}
	public List<String> getNew_comment() {
		return new_comment;
	}
	public void setNew_comment(List<String> new_comment) {
		this.new_comment = new_comment;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getTable_comment() {
		return table_comment;
	}
	public void setTable_comment(String table_comment) {
		this.table_comment = table_comment;
	}

	public int getCreator() {
		return creator;
	}

	public void setCreator(int creator) {
		this.creator = creator;
	}

	public String getHdfsPath() {
		return hdfsPath;
	}

	public void setHdfsPath(String hdfsPath) {
		this.hdfsPath = hdfsPath;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getRules() {
		return rules;
	}

	public void setRules(List<String> rules) {
		this.rules = rules;
	}

	public String getSrcFileName() {
		return srcFileName;
	}

	public void setSrcFileName(String srcFileName) {
		this.srcFileName = srcFileName;
	}
}
