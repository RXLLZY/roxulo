package com.swt.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 * 
 * @author RoXuLo
 * @email @shuweitech.com
 * @date 2016年12月19日 下午3:32:04
 */
@Mapper
public interface SysGeneratorDao {
	
	List<Map<String, Object>> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	Map<String, String> queryTable(String tableName);
	
	List<Map<String, String>> queryColumns(String tableName);

	void excuteSQL(String sql);

	/**
	 * 查询新建表的一条记录
	 * @param tableName
	 * @return
	 */
    Map<String, String> queryExample(String tableName);

	/**
	 * 查询是否存在菜单
	 * @param url 菜单的html地址
	 * @return
	 */
	int queryMenuCount(String url);

	/**
	 * 删除数据库的菜单
	 * @param url 菜单的html地址
	 * @return
	 */
	void deleteMenu(String url);
}
