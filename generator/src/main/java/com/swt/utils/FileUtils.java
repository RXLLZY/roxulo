package com.swt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author RoXuLo
 */
public class FileUtils {
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
    /**
     * 获取指定路径下所有包含指定值的文件名
     *
     * @param path     路径
     * @param fileName 文件名
     * @return
     */
    public static List<String> getFile(String path, String fileName) {
        List<String> fileNames = new ArrayList();
        // get file list where the path has   
        File file = new File(path);
        // get the folder list   
        File[] array = file.listFiles();

        for (int i = 0; i < array.length; i++) {
            if (array[i].isFile()) {
                if (array[i].getName().toLowerCase().startsWith(fileName)) {
                    fileNames.add(array[i].getPath());
                }
            } else if (array[i].isDirectory()) {
                fileNames.addAll(getFile(array[i].getPath(), fileName));
            }
        }
        return fileNames;
    }

    /**
     * 删除指定目录下的代码文件
     *
     * @param path     文件路径
     * @param fileName 表名
     * @return
     */
    public static List<String> delTargetFile(String path, String fileName) {
        List<String> successFileName = new ArrayList<>();
        fileName = fileName.toLowerCase();
        List<String> end = Arrays.asList(".js", "dao.xml", ".html", "controller.java", "dao.java", "entity.java", "service.java", "serviceimpl.java");
        List<String> allPath = getFile(path, fileName);
        //拼接文件名
        for (int j = 0; j < end.size(); j++) {
            String s = end.get(j);
            end.set(j, fileName + s);
        }
        //删除正确的文件名
        for (int i = 0; i < allPath.size(); i++) {
            String s = allPath.get(i);
            for (int j = 0; j < end.size(); j++) {
                String s1 = end.get(j);
                if (s.toLowerCase().endsWith(s1)) {
                    //删除文件并记录
                    File file = new File(s);
                    if (file.delete()) {
                        successFileName.add(file.getName());
                    }
                }
            }
        }
        delEmptyDir(new File(path));
        return successFileName;
    }

    /**
     * 删除空文件夹
     *
     * @param targer
     */
    public static  void delEmptyDir(File targer) {
        if (targer.exists()) {
            if (targer.isDirectory()) {
                File[] files = targer.listFiles();
                for (File file : files) {
                    delEmptyDir(file);
                }
                if (targer.listFiles().length == 0) {
                    logger.info("删除空目录"+targer.getAbsolutePath());
                    targer.delete();
                }
            }
        }
    }
}   