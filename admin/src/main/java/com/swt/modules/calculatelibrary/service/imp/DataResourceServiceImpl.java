package com.swt.modules.calculatelibrary.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.swt.modules.calculatelibrary.dao.DataResourceMapper;
import com.swt.modules.calculatelibrary.entity.DataPart;
import com.swt.modules.calculatelibrary.entity.DataResouceDetail;
import com.swt.modules.calculatelibrary.entity.DataResource;
import com.swt.modules.calculatelibrary.service.DataResourceService;
import com.swt.common.datainput.DataHeadList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2018/6/5 0005.
 */
@Service
public class DataResourceServiceImpl implements DataResourceService {

  private final static Logger logger = Logger.getLogger(DataResourceServiceImpl.class.getName());

    @Autowired
    private DataResourceMapper dataResourceMapper;

    @Override
    public JSONArray getDataResourceInfo(Integer uid){
        JSONArray jsonArray = new JSONArray();
        List<DataResource> list = dataResourceMapper.getDataResourceInfo(uid);
        List<DataResource> list1 = new ArrayList<>();
        List<DataResource> list2 = new ArrayList<>();
        List<DataResource> list3 = new ArrayList<>();
        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray1 = new JSONArray();
        JSONObject jsonObject2 = new JSONObject();
        JSONArray jsonArray2 = new JSONArray();
        JSONObject jsonObject3 = new JSONObject();
        JSONArray jsonArray3 = new JSONArray();
        for(DataResource d : list){
            switch (d.getDivision()) {
                case "公有数据":
                    list1.add(d);
                    break;
                case "自有数据":
                    list2.add(d);
                    break;
                case "临时数据":
                    list3.add(d);
                    break;
            }
        }

        for(DataResource d1 : list1){
            JSONObject json1 = new JSONObject();
            JSONArray array1 = new JSONArray();
            if(!containsValue(jsonArray1,d1.getType())) {
                json1.put("key", d1.getType());
                for (DataResource d01 : list1) {
                    if (d01.getType().equals(d1.getType())) {
                        DataPart dataPart = new DataPart();
                        dataPart.setData_resourse_id(d01.getData_resourse_id());
                        dataPart.setName(d01.getName());
                        dataPart.setTable_name(d01.getTable_name());
                        dataPart.setTable_name_preview(d01.getTable_name_preview());
                        dataPart.setType_icon(d01.getType_icon());
                        dataPart.setType_id(d01.getType_id());
                        dataPart.setDataNum(parseDataNum(d01.getData_num()));
                        if(d01.getLatest_time()==null){
                            dataPart.setLatest_time("");
                        }
                        else {
                            dataPart.setLatest_time(d01.getLatest_time());
                        }
                        array1.add(dataPart);
                    }
                }
                json1.put("value", array1);
                jsonArray1.add(json1);
            }
        }


        for(DataResource d2 : list2){
            JSONObject json2 = new JSONObject();
            JSONArray array2 = new JSONArray();
            if(!containsValue(jsonArray2,d2.getType())) {
                json2.put("key", d2.getType());
                for (DataResource d02 : list2) {
                    if (d02.getType().equals(d2.getType())) {
                        DataPart dataPart = new DataPart();
                        dataPart.setData_resourse_id(d02.getData_resourse_id());
                        dataPart.setName(d02.getName());
                        dataPart.setTable_name(d02.getTable_name());
                        dataPart.setTable_name_preview(d02.getTable_name_preview());
                        dataPart.setType_icon(d02.getType_icon());
                        dataPart.setType_id(d02.getType_id());
                        dataPart.setDataNum(parseDataNum(d02.getData_num()));
                        if(d02.getLatest_time()==null){
                            dataPart.setLatest_time("");
                        }
                        else {
                            dataPart.setLatest_time(d02.getLatest_time());
                        }
                        array2.add(dataPart);
                    }
                }
                json2.put("value", array2);
                jsonArray2.add(json2);
            }
        }


        for(DataResource d3 : list3){
            JSONObject json3 = new JSONObject();
            JSONArray array3 = new JSONArray();
            if(!containsValue(jsonArray3,d3.getType())) {
                json3.put("key", d3.getType());
                for (DataResource d03 : list3) {
                    if (d03.getType().equals(d3.getType())) {
                        DataPart dataPart = new DataPart();
                        dataPart.setData_resourse_id(d03.getData_resourse_id());
                        dataPart.setName(d03.getName());
                        dataPart.setTable_name(d03.getTable_name());
                        dataPart.setTable_name_preview(d03.getTable_name_preview());
                        dataPart.setType_icon(d03.getType_icon());
                        dataPart.setType_id(d03.getType_id());
                        dataPart.setDataNum(parseDataNum(d03.getData_num()));
                        if(d03.getLatest_time()==null){
                            dataPart.setLatest_time("");
                        }
                        else {
                            dataPart.setLatest_time(d03.getLatest_time());
                        }
                        array3.add(dataPart);
                    }
                }
                json3.put("value", array3);
                jsonArray3.add(json3);
            }
        }


        jsonObject1.put("division",0);
        jsonObject1.put("name","公有");
        jsonObject1.put("value",jsonArray1);
        jsonObject2.put("division",1);
        jsonObject2.put("name","自有");
        jsonObject2.put("value",jsonArray2);
        jsonObject3.put("division",2);
        jsonObject3.put("name","临时");
        jsonObject3.put("value",jsonArray3);
        jsonArray.add(jsonObject1);
        jsonArray.add(jsonObject2);
        jsonArray.add(jsonObject3);
        return  jsonArray;
    }

    public static boolean containsValue(JSONArray jsonArray, String type){
        for(Object o : jsonArray){
           String value = JSONObject.parseObject(o.toString()).getString("key");
           if(value.equals(type)){
               return true;
           }
        }
        return false;
    }

    @Override
    public List<DataResouceDetail> getDataResourceDetail(int data_resource_id){
        DataResource ds = dataResourceMapper.getDataSource(data_resource_id);
        String hdfsPath = ds.getTable_name();
        String dsComment = ds.getName();
        return dataResourceMapper.getDataResourceDetail(hdfsPath,dsComment);
    }

    @Override
    public String getIndex(int data_resource_id){
        return dataResourceMapper.getIndex(data_resource_id);
    }

  @Override
  public int testNewDSMeta(DataHeadList dhl, int uid) {
    if (!dataResourceMapper.checkDSNameDuplicate(dhl.getTable_comment(), uid)) {
      return 200;
    } else {
      return 406;
    }
  }

  @Override
  public int deleteDataResource(int dsId) {
    if (dataResourceMapper.markDataResourceAsDelete(dsId)) {
      return 200;
    }
    return 500;
  }

  @Override
  public int modifyDSMeta(int id, DataResource ds) {
    if (ds == null || id <= 0) {
      logger.severe(
          String.format("修改数据源元数据，参数有误： id = %d, data source -> %s", id, JSON.toJSONString(ds)));
      return 406;
    }
    if (dataResourceMapper.checkDSNameDuplicateWithoutSelf(
        ds.getName(), id, ds.getCreator())) {
      return DataResourceService.ERR_DS_NAME_DUPLICATE;
    }
    ds.setData_resourse_id(id);
    if (dataResourceMapper.updateDSMeta(ds)) {
      return 200;
    }
    return 500;
  }

    @Override
    public JSONObject previewData(String str, String index, List<DataResouceDetail> list) {
        return null;
    }

    private String parseDataNum(Long dataNum) {
    if (dataNum == null || dataNum == 0) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("数据量：");
    int yi = (int) (dataNum / 100000000);
    if (yi > 0) {
      sb.append(yi).append("亿");
      int qianwan = (int) ((dataNum % 100000000) / 10000000);
      if (qianwan > 0) {
        sb.append(qianwan).append("千万");
      }
      return sb.toString();
    }
    int wan = (int) (dataNum / 10000);
    if (wan > 0) {
      sb.append(wan).append("万");
      return sb.toString();
    }
    sb.append(dataNum);
    return sb.toString();
  }
}
