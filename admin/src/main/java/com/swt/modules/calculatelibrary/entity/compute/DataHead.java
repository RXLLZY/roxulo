package com.swt.modules.calculatelibrary.entity.compute;

import java.io.Serializable;

public class DataHead implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3454396053886902755L;
	
	private String old_name;
	private String new_name;
	private String new_comment;
	private String entity_name;
	
	public String getOld_name() {
		return old_name;
	}
	public void setOld_name(String old_name) {
		this.old_name = old_name;
	}
	public String getNew_name() {
		return new_name;
	}
	public void setNew_name(String new_name) {
		this.new_name = new_name;
	}
	public String getNew_comment() {
		return new_comment;
	}
	public void setNew_comment(String new_comment) {
		this.new_comment = new_comment;
	}
	public String getEntity_name() {
		return entity_name;
	}
	public void setEntity_name(String entity_name) {
		this.entity_name = entity_name;
	}
	
	
}
