package com.swt.modules.calculatelibrary.service.imp;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.swt.common.config.UploadConfig;
import com.swt.common.exception.RRException;
import com.swt.common.utils.ConfigConstant;
import com.swt.modules.calculatelibrary.service.IFileService;
import com.swt.common.datainput.PoiExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by HuGuowei on 18-6-14.
 *
 * <p>Say something when you want to bibi.<br>
 */
@Service
public class FileServiceImpl implements IFileService {
  private final Logger logger = Logger.getLogger(FileServiceImpl.class.getName());
  private final Path localFileDir;
  private final Path validDataPath;
  private final Path errDataPath;
  private final UploadConfig uploadConfig;

  @Autowired
  public FileServiceImpl(UploadConfig uploadConfig) throws IOException {
    this.uploadConfig = uploadConfig;
    this.localFileDir = Paths.get(uploadConfig.getLocalDir());
    this.validDataPath = Paths.get(uploadConfig.getValidDataDir());
    this.errDataPath = Paths.get(uploadConfig.getErrDataDir());
    if (!localFileDir.toFile().exists() && !localFileDir.toFile().mkdirs()) {
      throw new IOException("创建文件长传文件夹失败 -> " + localFileDir.toUri().getPath());
    }
    if (!validDataPath.toFile().exists() && !validDataPath.toFile().mkdirs()) {
      throw new IOException("创建文件长传文件夹失败 -> " + validDataPath.toUri().getPath());
    }
    if (!errDataPath.toFile().exists() && !errDataPath.toFile().mkdirs()) {
      throw new IOException("创建文件长传文件夹失败 -> " + errDataPath.toUri().getPath());
    }
  }

  @Override
  public String resolveMultipartFile(MultipartFile file, Integer uid) {
    String finalName;
    try {
      String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
      String renamed = "tmp_" + (uid == null ? "" : uid + "_") + renameMethod(filename);
      if (file.isEmpty()) {
        throw new Exception("上传的文件为空，上传失败。");
      }
      if (filename.contains("..")) {
        throw new Exception("文件名中包含相对路径，上传失败");
      }
      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, localFileDir.resolve(renamed), StandardCopyOption.REPLACE_EXISTING);
      }
      finalName = renamed;
    } catch (Exception e) {
      logger.log(Level.SEVERE, "获取上传文件失败", e);
      finalName = null;
    }
    return finalName;
  }

  @Override
  public Path load(String filename) {
    return localFileDir.resolve(filename);
  }

  @Override
  public Resource loadAsResource(String filename) {
    try {
      Path filePath = load(filename);
      Resource resource = new UrlResource(filePath.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        logger.warning("未能加载指定文件 -> " + filename);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public ResponseEntity<Resource> convert2Response(Resource resource) {
    String filename = resource.getFilename();
    String downloadName = resource.getFilename();
    try {
      assert filename != null;
      downloadName = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return ResponseEntity.ok()
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\""
                + resource.getFilename()
                + "\"; filename*=utf-8''"
                + downloadName)
        .body(resource);
  }

  @Override
  public String excel2Csv(MultipartFile file, Integer uid)
      throws IOException, InvalidFormatException {
    String oriFileName =
        StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())).toLowerCase();
    String path = uploadConfig.getLocalDir();
    String tarFileName = "tmp_" + (uid == null ? "" : uid + "_") + renameMethod(oriFileName);
    tarFileName = tarFileName.replace(".xlsx", ".csv").replace(".xls", ".csv");
    try {
      new PoiExcelHelper().parseToCSVFile(file.getInputStream(), path, tarFileName);
    } catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
      logger.log(Level.SEVERE, "无效的格式", e);
      throw  new RRException("无效的格式");
    }
    return tarFileName;
  }

  private String renameMethod(String srcFileName) {
    return LocalDateTime.now().format(ConfigConstant.time) + "_" + srcFileName;
  }
}
