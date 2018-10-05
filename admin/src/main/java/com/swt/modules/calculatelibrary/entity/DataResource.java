package com.swt.modules.calculatelibrary.entity;

/**
 * Created by Administrator on 2018/6/5 0005.
 */
public class DataResource {
    private String passwd;

    private String username;

    private int data_resourse_id;

    private String name;

    private String type;

    private String division;

    private String table_name;

    private String table_name_preview;

    private String db_url;

    private int type_id;

    private int creator;

    private Boolean shared;

    private Boolean active;

    private String created_time;

    private String type_id1;

    private String type_name;

    private String type_icon;

    private String remark;

    private Boolean is_active;

    private String latest_time;

    private Long data_num;

    public String getLatest_time() {
        return latest_time;
    }

    public void setLatest_time(String latest_time) {
        this.latest_time = latest_time;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
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

    public String getDb_url() {
        return db_url;
    }

    public void setDb_url(String db_url) {
        this.db_url = db_url;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public Boolean getShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getType_id1() {
        return type_id1;
    }

    public void setType_id1(String type_id1) {
        this.type_id1 = type_id1;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getType_icon() {
        return type_icon;
    }

    public void setType_icon(String type_icon) {
        this.type_icon = type_icon;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public Long getData_num() {
        return data_num;
    }

    public void setData_num(Long data_num) {
        this.data_num = data_num;
    }

    @Override
    public String toString() {
        return "DataResource{" +
                "passwd='" + passwd + '\'' +
                ", username='" + username + '\'' +
                ", data_resourse_id=" + data_resourse_id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", division='" + division + '\'' +
                ", table_name='" + table_name + '\'' +
                ", table_name_preview='" + table_name_preview + '\'' +
                ", db_url='" + db_url + '\'' +
                ", type_id=" + type_id +
                ", creator=" + creator +
                ", shared=" + shared +
                ", active=" + active +
                ", created_time='" + created_time + '\'' +
                ", type_id1='" + type_id1 + '\'' +
                ", type_name='" + type_name + '\'' +
                ", type_icon='" + type_icon + '\'' +
                ", remark='" + remark + '\'' +
                ", is_active=" + is_active +
                ", data_num=" + data_num +
                '}';
    }


}
