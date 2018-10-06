package com.swt.common.datainput;

import java.io.Serializable;

public class GuessMetaModel implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2842337091137353793L;
	
	private String filename;
	private String entity_model_name;
	private String delimiter;
	private TableModel data;
	
	public String getEntity_model_name() {
		return entity_model_name;
	}
	public void setEntity_model_name(String entity_model_name) {
		this.entity_model_name = entity_model_name;
	}
	public String getDelimiter() {
		return delimiter;
	}
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	public TableModel getData() {
		return data;
	}
	public void setData(TableModel data) {
		this.data = data;
	}
/*	public String getData_text() {
		return data_text;
	}
	public void setData_text(String data_text) {
		this.data_text = data_text;
	}*/
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
