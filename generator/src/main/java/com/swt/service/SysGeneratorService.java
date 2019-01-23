package com.swt.service;

import com.swt.dao.SysGeneratorDao;
import com.swt.utils.FileUtils;
import com.swt.utils.GenUtils;
import com.swt.utils.RRException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import static com.swt.utils.GenUtils.getConfig;

/**
 * 代码生成器
 *
 * @author RoXuLo
 * @email @shuweitech.com
 * @date 2016年12月19日 下午3:33:38
 */
@Service
public class SysGeneratorService {
    @Autowired
    private SysGeneratorDao sysGeneratorDao;

    public List<Map<String, Object>> queryList(Map<String, Object> map) {
        return sysGeneratorDao.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return sysGeneratorDao.queryTotal(map);
    }

    public int queryMenuCount(String url) {
        return sysGeneratorDao.queryMenuCount(url);
    }

    public void deleteMenu(String url) {
        sysGeneratorDao.deleteMenu(url);
    }

    public Map<String, String> queryTable(String tableName) {
        return sysGeneratorDao.queryTable(tableName);
    }

    public List<Map<String, String>> queryColumns(String tableName) {
        return sysGeneratorDao.queryColumns(tableName);
    }

    public Map<String, String> queryExample(String tableName) {
        return sysGeneratorDao.queryExample(tableName);
    }

    public byte[] generatorZipCode(String[] tableNamesWithTableComments) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for (String tableNamesWithTableComment : tableNamesWithTableComments) {
            String[] split = tableNamesWithTableComment.split("\\|");
            String tableName = split[0];
            String tableComment = split[1];
            //查询表信息
            Map<String, String> table = queryTable(tableName);
            table.put("tableComment", tableComment);
            //查询列信息
            List<Map<String, String>> columns = queryColumns(tableName);
            //获取示例
            Map<String, String> map = queryExample(tableName);
            //生成代码
            GenUtils.generatorCode(table, columns, map, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    public void generatorCode(String[] tableNamesWithTableComments) {
        //配置信息
        Configuration config = getConfig();
        for (String tableNamesWithTableComment : tableNamesWithTableComments) {
            String[] split = tableNamesWithTableComment.split("\\|");
            String tableName = split[0];
            String tableComment = split[1];
            String url = "/" + tableName.replace(config.getString("tablePrefix"),"").toLowerCase().replaceAll("_", "") + ".html";
            if (queryMenuCount(url) > 0) {
                throw new RRException("菜单已存在");
            }
            //查询表信息
            Map<String, String> table = queryTable(tableName);
            table.put("tableComment", tableComment);
            //查询列信息
            List<Map<String, String>> columns = queryColumns(tableName);
            //获取示例
            Map<String, String> map = queryExample(tableName);
            //生成代码
            String sql = GenUtils.generatorCode(table, columns, map, null);
            if (sql != null) {
                sysGeneratorDao.excuteSQL(sql);
            }
        }
    }

    public List<String> deleteCode(String[] tableNames) {
        List<String> fileNames = new ArrayList<>();
        //配置信息
        Configuration config = getConfig();
        for (String tableName : tableNames) {
            String fileName = tableName.replace(config.getString("tablePrefix"), "");
            String  viewName= fileName.replaceAll("_", "-");
            String  viewPath = "/" + viewName.toLowerCase() + ".html";
            //删除菜单栏
            deleteMenu(viewPath);
            String javaName = fileName.replaceAll("_", "").toLowerCase();
            //配置java文件和html文件
            String path = getConfig().getString("mainDirectory");
            fileNames.addAll(FileUtils.delTargetFile(path, viewName, javaName));
        }
        if(fileNames.size() == 0 ){
            throw new RRException( "项目不存在相关代码文件,无需删除", 500);
        }
        return fileNames;
    }
}
