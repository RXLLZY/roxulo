package com.swt.modules.calculatelibrary.controller;

import com.alibaba.fastjson.JSONObject;
import com.swt.common.controller.AbstractController;
import com.swt.modules.calculatelibrary.entity.DataResouceDetail;
import com.swt.modules.calculatelibrary.entity.DataResource;
import com.swt.modules.calculatelibrary.service.DataResourceService;
import com.swt.modules.calculatelibrary.service.IFileService;
import com.swt.common.datainput.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/** Created by Administrator on 2018/6/5 0005. */
@RestController
@RequestMapping("/dataresource")
public class DataResourceController extends AbstractController {
  private final Logger logger = Logger.getLogger(DataResourceController.class.getName());
  private final DataResourceService service;
  private final IFileService fileService;
  private final IdentifyMeta identifyDataMeta;
  @Autowired
  private ValidateData validateData;
  @Autowired
  public DataResourceController(
      DataResourceService service,
      IFileService fileService,
      IdentifyMeta identifyDataMeta) {
    this.service = service;
    this.fileService = fileService;
    this.identifyDataMeta = identifyDataMeta;
  }

  @RequestMapping(value = "/info", method = RequestMethod.GET)
  public JSONObject getDataResource() {
    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("data", service.getDataResourceInfo(getUserIntId()));
      jsonObject.put("message", "获取数据源信息成功");
      jsonObject.put("status", "200");
    } catch (Exception e) {
      jsonObject.put("message", "获取数据源信息失败");
      jsonObject.put("status", "500");
      e.printStackTrace();
    }

    return jsonObject;
  }

  @RequestMapping(value = "/filedinfo", method = RequestMethod.POST)
  public JSONObject getDataResourceFiledInfo(@RequestBody String str) {
    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put(
          "data",
          service.getDataResourceDetail(
              JSONObject.parseObject(str).getInteger("data_resource_id")));
      jsonObject.put("message", "获取数据源字段详细信息成功");
      jsonObject.put("status", "200");
    } catch (Exception e) {
      jsonObject.put("message", "获取数据源字段详细信息失败");
      jsonObject.put("status", "500");
      e.printStackTrace();
    }

    return jsonObject;
  }

  @RequestMapping(value = "/preview", method = RequestMethod.POST)
  public JSONObject getDataResourcePreview(@RequestBody String str) {
    JSONObject json = JSONObject.parseObject(str);
    String index = service.getIndex(json.getInteger("data_resource_id"));
    List<DataResouceDetail> list =
        service.getDataResourceDetail(json.getInteger("data_resource_id"));
    JSONObject esres = service.previewData(str, index, list);
    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("data", esres);
      jsonObject.put("message", "预览成功");
      jsonObject.put("status", "200");
    } catch (Exception e) {
      jsonObject.put("message", "预览失败");
      jsonObject.put("status", "500");
      e.printStackTrace();
    }

    return jsonObject;
  }

  @GetMapping(value = "/{file_name}/header")
  public Map<String, Object> fileHeader(@PathVariable("file_name") String fileName) {
    Map<String, Object> retMap = new HashMap<>();
    TableModel resultTable = identifyDataMeta.identifyDataMeta(fileName);
    if (resultTable == null || resultTable.getData_head() == null) {
      retMap.put("message", "读文件失败，请检查文件");
      retMap.put("status", "404");
      return retMap;
    }
    if (resultTable.getData_head().getOld_name().contains("has some empty field name")
        || resultTable.getData_head().getOld_name().contains("has same field name")) {
      retMap.put("message", "列名为空或有重复");
      retMap.put("status", "501");
      return retMap;
    } else {
      retMap.put("data", resultTable);
      retMap.put("message", "操作成功");
      retMap.put("status", "200");
      return retMap;
    }
  }

  @PostMapping(value = "/data")
  public Map uploadData(
          @RequestBody GuessMetaModel gmm) {
    Map<String, Object> retMap = new HashMap<>();
    // 重名检测
    int checkCode = service.testNewDSMeta(gmm.getData().getData_head(),getUserIntId());
    if (checkCode != 200) {
      retMap.put("status", checkCode + "");
      retMap.put("message", "数据元信息错误");
      return retMap;
    }
    gmm.getData().getData_head().setCreator(getUserIntId());
    ValidationReturnModel vrm = validateData.ValidateAndSaveData(gmm);
    if (vrm.getInput_num() > 0) {
      retMap.put("status", "200");
      retMap.put("message", "操作成功");
      retMap.put("data", vrm);
    } else {
      retMap.put("status", "500");
      retMap.put("message", "上传过程出错，上传失败。");
    }
    return retMap;
  }

  @DeleteMapping("/{id}")
  public Map deleteDataResource(@PathVariable("id") Integer dsId) {
    Map<String, Object> retMap = new HashMap<>();
    int checkCode = service.deleteDataResource(dsId);
    if (checkCode != 200) {
      retMap.put("status", checkCode + "");
      retMap.put("message", "服务错误，删除失败");
    } else {
      retMap.put("status", "200");
      retMap.put("message", "删除成功");
    }
    return retMap;
  }

  @PutMapping("/{id}")
  public Map modifyDataResourceMeta(
      @PathVariable("id") Integer id,
      @RequestBody DataResource ds) {
    Map<String, Object> retMap = new HashMap<>();
    ds.setCreator(getUserIntId());
    int checkCode = service.modifyDSMeta(id, ds);
    if (checkCode != 200) {
      if (checkCode == DataResourceService.ERR_DS_NAME_DUPLICATE) {
        retMap.put("status", -checkCode + "");
        retMap.put("message", "数据源重名");
      } else {
        retMap.put("status", checkCode + "");
        retMap.put("message", "服务错误，修改失败");
      }
    } else {
      retMap.put("status", "200");
      retMap.put("message", "修改成功");
    }
    return retMap;
  }
}
