package com.swt.modules.calculatelibrary.service;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;

/**
 * Created by HuGuowei on 18-6-14.
 *
 * <p>Say something when you want to bibi.<br>
 */
public interface IFileService {
  /**
   * 处理表单中上传的文件
   *
   * @param file 表单文件
   * @param uid 用户id
   * @return 本地存储的文件名
   */
  String resolveMultipartFile(MultipartFile file, Integer uid);

  /**
   * 加载设定的本地文件夹中的文件,转化为路径<br>
   * 注意文件可能不存在
   *
   * @param filename 文件名
   * @return 文件指示
   */
  Path load(String filename);

  /**
   * 加载设定的本地文件夹中的文件,转化为资源
   *
   * @param filename 文件名
   * @return 文件资源
   */
  Resource loadAsResource(String filename);

  /**
   * 将文件资源转化为web接口返回类
   *
   * @param resource 文件资源
   * @return 用于web接口返回对象
   * @throws UnsupportedEncodingException - 对文件名进行u8编码，编码失败时异常
   */
  ResponseEntity<Resource> convert2Response(Resource resource) throws UnsupportedEncodingException;

  /**
   * excel转为csv
   *
   * @param file 文件
   * @param uid 用户id
   * @return 文件名称
   */
  String excel2Csv(MultipartFile file, Integer uid) throws IOException, InvalidFormatException;
}
