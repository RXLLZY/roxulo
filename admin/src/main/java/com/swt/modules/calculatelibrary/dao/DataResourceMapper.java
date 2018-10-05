package com.swt.modules.calculatelibrary.dao;

import com.swt.modules.calculatelibrary.entity.DataField;
import com.swt.modules.calculatelibrary.entity.DataResouceDetail;
import com.swt.modules.calculatelibrary.entity.DataResource;
import com.swt.modules.calculatelibrary.entity.TableName;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Administrator on 2018/6/5 0005.<br>
 * 复杂SQL已移至xml文件
 */
@Repository
public interface DataResourceMapper {

  List<DataResource> getDataResourceInfo(@Param("uid") Integer uid);

  List<DataResouceDetail> getDataResourceDetail(@Param("table") String table, @Param("comment") String comment);

  @Select(
      "select data_resourse.table_name_preview from data_resourse where data_resourse_id=#{data_resourse_id}")
  String getIndex(int data_resource_id);

  @Select("select updateTime from update_sync where hdfsUrl = #{hdfsUrl}")
  Timestamp getDSUpdateTime(@Param("hdfsUrl") String hdfsUrl);

  @Delete("delete from data_resourse where data_resourse_id = #{id}")
  void deleteDataResource(@Param("id") int dsId);

  @Insert(
      "insert into data_resourse(name,type,division,table_name,table_name_preview,type_id,creator,created_time) "
          + "values(#{name},#{type},#{division},#{table_name},#{table_name_preview},#{type_id},#{creator},NOW())")
  @Options(
      useGeneratedKeys = true,
      keyProperty = "data_resourse_id",
      keyColumn = "data_resourse_id")
  void addDataResource(DataResource ds);

  @Insert(
      "insert into table_field_comparision(`table`,table_comment,field,field_comment,field_type,is_ID_Number) "
          + "values(#{table},#{tableComment},#{field},#{fieldComment},#{fieldType},#{isIdNumber})")
  void addField(DataField field);

  @Results({
    @Result(column = "table_name", property = "tableName"),
    @Result(column = "table_name_preview", property = "tableNamePreview")
  })
  @Select(
      "select table_name,table_name_preview from data_resourse where active = 1 and type_id = 9")
  List<TableName> privateDs();

  @Select("select field from table_field_comparision where `table` = #{table}")
  List<String> cols(@Param("table") String table);

  @Select(
      "select count(1) from data_resourse where `name` = #{name} and `creator` = #{uid} and (type_id = 9 or type_id = 10) and active = 1 limit 1")
  boolean checkDSNameDuplicate(@Param("name") String name, @Param("uid") int uid);

  @Select(
      "select count(1) from data_resourse where data_resourse_id <> #{dsId} and  `name` = #{name} "
          + "and `creator` = #{uid} and (type_id = 9 or type_id = 10) and active = 1 limit 1  ")
  boolean checkDSNameDuplicateWithoutSelf(
          @Param("name") String name, @Param("dsId") int dsId, @Param("uid") int uid);

  DataResource getDataSource(@Param("dsId") int data_resource_id);

  @Update("update data_resourse set `active` = 0 where data_resourse_id = #{id}")
  boolean markDataResourceAsDelete(@Param("id") int dsId);

  @Update("update data_resourse set `name` = #{name} where data_resourse_id = #{data_resourse_id}")
  boolean updateDSMeta(DataResource ds);
}
