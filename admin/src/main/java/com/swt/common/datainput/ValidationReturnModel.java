package com.swt.common.datainput;

import java.io.Serializable;

public class ValidationReturnModel implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 314714664104884390L;
	
	private long input_num;
	private long err_num;
	private String err_file_name;
	private Integer fileID;
	private TableModel data;
	
	public long getInput_num() {
		return input_num;
	}
	public void setInput_num(long input_num) {
		this.input_num = input_num;
	}
	public long getErr_num() {
		return err_num;
	}
	public void setErr_num(long err_num) {
		this.err_num = err_num;
	}
	public TableModel getData() {
		return data;
	}
	public void setData(TableModel data) {
		this.data = data;
	}
	public String getErr_file_name() {
		return err_file_name;
	}

	public Integer getFileID() {
		return fileID;
	}

	public void setFileID(Integer fileID) {

		this.fileID = fileID;
	}

	public void setErr_file_name(String err_file_name) {
		this.err_file_name = err_file_name;
	}
}
