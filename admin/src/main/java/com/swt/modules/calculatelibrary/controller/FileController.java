package com.swt.modules.calculatelibrary.controller;

import com.alibaba.fastjson.JSONObject;
import com.swt.common.controller.AbstractController;
import com.swt.modules.calculatelibrary.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by HuGuowei on 18-6-14.
 *
 * <p>Say something when you want to bibi.<br>
 */
@RestController
@RequestMapping("/file")
public class FileController extends AbstractController {

  private final Logger logger = Logger.getLogger(FileController.class.getName());
  private final IFileService fileService;

  @Autowired
  public FileController(IFileService fileService) {
    this.fileService = fileService;
  }

  @PostMapping
  @ResponseBody
  public JSONObject upload(
      @RequestParam("file") MultipartFile file) {
    JSONObject retJson = new JSONObject();
    String fileName =
        StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())).toLowerCase();
    String renamedFile;
    if (fileName.endsWith(".xlsx") || fileName.endsWith(".xls")) {
      try {
        renamedFile = fileService.excel2Csv(file, getUserIntId());
      } catch (Exception e) {
        logger.log(Level.SEVERE, "excel转化为csv出错", e);
        renamedFile = null;
      }
    } else {
      renamedFile = fileService.resolveMultipartFile(file, getUserIntId());
    }
    if (renamedFile == null) {
      retJson.put("message", "文件上传失败");
      retJson.put("status", "500");
      return retJson;
    }
    retJson.put("message", "文件上传成功。");
    retJson.put("status", "200");
    retJson.put("data", renamedFile);
    return retJson;
  }

  @GetMapping
  @ResponseBody
  public ResponseEntity<Resource> download(@RequestParam("filename") String filename)
      throws UnsupportedEncodingException {
    Resource file = fileService.loadAsResource(filename);
    if (file == null) {
      return ResponseEntity.notFound().build();
    }
    return fileService.convert2Response(file);
  }
}
