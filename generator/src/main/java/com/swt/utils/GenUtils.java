/**
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.swt.utils;

import com.swt.entity.ColumnEntity;
import com.swt.entity.TableEntity;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 *
 * @author RoXuLo
 * @email @shuweitech.com
 * @date 2016年12月19日 下午11:40:24
 */
public class GenUtils {

    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
        templates.add("template/Entity.java.vm");
        templates.add("template/Dao.java.vm");
        templates.add("template/Dao.xml.vm");
        templates.add("template/Service.java.vm");
        templates.add("template/ServiceImpl.java.vm");
        templates.add("template/Controller.java.vm");
//        templates.add("template/list.html.vm");
//        templates.add("template/list.js.vm");
//        templates.add("template/menu.sql.vm");
        return templates;
    }

    /**
     * 生成代码
     */
    public static String generatorCode(Map<String, String> table,
                                       List<Map<String, String>> columns, Map<String, String> exampleMap, ZipOutputStream zip) {
        //配置信息
        Configuration config = getConfig();
        String sql = null;
        String mainDirectory = config.getString("mainDirectory") + "//";
        boolean hasBigDecimal = false;
        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName"));
        tableEntity.setComments(commentToJava(table.get("tableComment")));
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), table.get("tablePrefix"));
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));
        //列信息
        List<Object> hidden = config.getList("hidden");
        List<ColumnEntity> columsList = new ArrayList<>();
        //列名
        List<String> attrNamesList = new ArrayList<>();
        //资源关键词
        String[] resourceKey = config.getStringArray("resourceKey");
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            String columnName = column.get("columnName");
            columnEntity.setColumnName(toLowerCaseFirstOne(columnName));
            columnEntity.setDataType(column.get("dataType"));
            String columnComment = column.get("columnComment");
            columnEntity.setComments(commentToJava(columnComment));
            columnEntity.setExtra(column.get("extra"));
            columnEntity.setIsNull(column.get("is_null"));
            if(hidden.contains(columnName)){
                columnEntity.setHidden(true);
            }else{
                columnEntity.setHidden(false);
            }
            //列名是否以is_开头
            columnEntity.setStartIs(columnName.startsWith("is_"));
            //列名转换成Java属性名
            String attrName = commentToJava(columnEntity.getColumnName());
            //大写属性名
            columnEntity.setAttrName(attrName);
            attrNamesList.add(columnName);
            //小写属性名
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "unknowType");
            columnEntity.setAttrType(attrType);
            columnEntity.setAttrtype(attrType.toLowerCase());
            if (!hasBigDecimal && attrType.equals("BigDecimal")) {
                hasBigDecimal = true;
            }
            //添加示例
            if (exampleMap != null && exampleMap.get(columnName) != null) {
                columnEntity.setExample(commentToJava(String.valueOf(exampleMap.get(columnName))));
            } else {
                columnEntity.setExample("");
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
                columnEntity.setHidden(true);
                tableEntity.setPk(columnEntity);
            }
            //资源路径判断
            for (String s : resourceKey) {
                if(columnName.indexOf(s) > -1){
                    columnEntity.setResource(true);
                    break;
                }
            }
            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        String api = tableEntity.getComments();
        if (!api.endsWith("管理")) {
            api += "管理";
        }
        //封装模板数据
        Map<String, Object> map = new HashMap<>(16);
        String pathName = tableToPath(tableEntity.getTableName(), table.get("tablePrefix"));
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("api", api);
        //主键字段
        map.put("pk", tableEntity.getPk());
        //查询字段
        map.put("searchColumns", tableEntity.getSearchColumns());
        map.put("className", tableEntity.getClassName());
        map.put("classname", tableEntity.getClassname());
        map.put("pathName", pathName);
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        //包路径配置
        map.put("package", config.getString("package"));
        //enterModuleName(tableEntity.getTableName(), config.getString("tablePrefix"), moduleName);
        String moduleName = table.getOrDefault("moduleName", "moduleName");
        map.put("moduleName", moduleName);
        map.put("controller", config.getString("controller","controller"));
        map.put("service", config.getString("service","service"));
        map.put("dao", config.getString("dao","dao"));
        map.put("entity", config.getString("entity","entity"));

        //是否生成swagger
        map.put("swagger", config.getBoolean("swagger",false));
        //是否权限校验
        map.put("permissions", config.getBoolean("permissions",false));
        //是否添加日志管理
        map.put("sysLog", config.getBoolean("sysLog",false));
        //绑定模块路径
        map.put("modules", config.getString("modules","modules"));

        //是否添加查询字段
        map.put("search", config.getBoolean("search",false));

        //作者信息配置
        map.put("author", config.getString("author"));
        map.put("email", config.getString("email"));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));

        //用户信息
        map.put("CrtUserId", attrNamesList.contains(config.getString("crt_user_id"))?commentToJava(config.getString("crt_user_id")):"");
        map.put("OptUserId", attrNamesList.contains(config.getString("opt_user_id"))?commentToJava(config.getString("opt_user_id")):"");
        map.put("CrtTime", attrNamesList.contains(config.getString("crt_time"))?commentToJava(config.getString("crt_time")):"");
        map.put("OptTime", attrNamesList.contains(config.getString("opt_time"))?commentToJava(config.getString("opt_time")):"");
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);
            try {
                String fileName = getFileName(template, moduleName, tableEntity.getClassName(), pathName, config);
                if (zip == null) {
                    if (template.endsWith("menu.sql.vm")) {
                        //写数据库
                        sql = sw.toString();
                        IOUtils.closeQuietly(sw);
                    }else{
                        //写文件
                        File file = new File(mainDirectory + fileName);
                        FileUtils.touch(file);
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        IOUtils.write(sw.toString(), fileOutputStream, "UTF-8");
                        IOUtils.closeQuietly(sw);
                        IOUtils.closeQuietly(fileOutputStream);
                    }
                } else {
                    //添加到zip
                    ZipEntry zipEntry = new ZipEntry(fileName);
                    zip.putNextEntry(zipEntry);
                    IOUtils.write(sw.toString(), zip, "UTF-8");
                    zip.closeEntry();
                    IOUtils.closeQuietly(sw);
                }
            } catch (IOException e) {
                throw new RRException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }
        return sql;
    }

    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        String is_ = "is_";
        if(columnName.startsWith(is_)){
            columnName = columnName.substring(2);
        }
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 注释转换成Java类型
     */
    public static String commentToJava(String comment) {
        return comment.replaceAll("\\\\","\\\\\\\\").replaceAll("\\\"","\\\\\"");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToPath(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }
        String path = tableName.toLowerCase().replaceAll("_", "-");
        return path;
    }
    /**
     * 当表前缀生效时候，moduleName为表前缀
     */
    public static void enterModuleName(String tableName, String tablePrefix, String moduleName) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            if (tableName.startsWith(tablePrefix)) {
                moduleName = tablePrefix.replace("_", "");
            }
        }
    }
    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new RRException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     * @param template 模板路径
     * @param moduleName
     * @param className
     * @param pathName
     * @param config
     * @return
     */
    public static String getFileName(String template, String moduleName, String className, String pathName, Configuration config) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        String packageName = config.getString("package","package");
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + config.getString("modules") + File.separator + moduleName + File.separator;
        }

        if (template.contains("Entity.java.vm")) {
            return packagePath + config.getString("entity","entity") + File.separator + className + ".java";
        }

        if (template.contains("Dao.java.vm")) {
            return packagePath + config.getString("dao","dao") + File.separator + "I" + className + "Dao.java";
        }

        if (template.contains("Service.java.vm")) {
            return packagePath + config.getString("service","service") + File.separator + "I" + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath + config.getString("service","service") + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm")) {
            return packagePath + config.getString("controller","controller") + File.separator + className + "Controller.java";
        }

        if (template.contains("Dao.xml.vm")) {
          return "main" + File.separator + "resources" + File.separator  + config.getString("mapper","mapper")  + File.separator + moduleName + File.separator + className + "Dao.xml";
        }

        if (template.contains("list.html.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator
                    + "modules" + File.separator + moduleName + File.separator + pathName + ".html";
        }

        if (template.contains("list.js.vm")) {
            return "main" + File.separator + "resources" + File.separator + "statics" + File.separator + "js" + File.separator
                    + "modules" + File.separator + moduleName + File.separator + pathName + ".js";
        }

        if (template.contains("menu.sql.vm")) {
            return className.toLowerCase() + "_menu.sql";
        }

        return null;
    }

    //首字母转小写
    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0))){
            return s;
        }else{
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    //首字母转大写
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0))){
            return s;
        }
        else{
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }
}
