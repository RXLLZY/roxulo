package com.swt.modules.calculatelibrary.entity;

/**
 * Created by Administrator on 2018/6/5 0005.
 */
public class DataPart {
    private int data_resourse_id;

    private String name;

    private String table_name;

    private String table_name_preview;

    private int type_id;

    private String type_icon;

    private String latest_time;

    private String dataNum;

    public String getLatest_time() {
        return latest_time;
    }

    public void setLatest_time(String latest_time) {
        this.latest_time = latest_time;
    }

    public int getData_resourse_id() {
        return data_resourse_id;
    }

    public void setData_resourse_id(int data_resourse_id) {
        this.data_resourse_id = data_resourse_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getTable_name_preview() {
        return table_name_preview;
    }

    public void setTable_name_preview(String table_name_preview) {
        this.table_name_preview = table_name_preview;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getType_icon() {
        return type_icon;
    }

    public void setType_icon(String type_icon) {
        this.type_icon = type_icon;
    }

    public String getDataNum() {
        return dataNum;
    }

    public void setDataNum(String dataNum) {
        this.dataNum = dataNum;
    }
}
