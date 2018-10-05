package com.swt.modules.calculatelibrary.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.swt.modules.calculatelibrary.entity.DataResouceDetail;
import com.swt.modules.calculatelibrary.entity.DataResource;
import com.swt.common.datainput.DataHeadList;

import java.util.List;

/**
 * Created by Administrator on 2018/6/5 0005.
 */
public interface DataResourceService {
  /**
   * 数据源名称重复
   */
  int ERR_DS_NAME_DUPLICATE = -50001;

    /**
     * 获取数据源信息
     * @return 数据源信息
     */
    JSONArray getDataResourceInfo(Integer uid);

    /**
     * 获取指定数据源的字段详情
     * @param data_resource_id 指定的数据源id
     * @return 字段详情
     */
    List<DataResouceDetail> getDataResourceDetail(int data_resource_id);

    /**
     * 获取指定数据源在es中的索引名称
     * @param data_resource_id 指定数据源的id
     * @return 索引名称
     */
    String getIndex(int data_resource_id);

  /**
   * 检查新数据源元信息合法性
   *
   * @param dhl 元信息
   * @param uid 用户id
   * @return 状态码
   *     <p>200：合法
   *     <p>406：数据源名称重复
   */
  int testNewDSMeta(DataHeadList dhl, int uid);

  /**
   * 删除数据源
   *
   * @return 状态码
   *     <p>200：成功
   *     <p>其他：失败
   */
  int deleteDataResource(int dsId);

  /**
   * 修改数据源描述信息
   *
   * <p>目前之修改数据源名称
   *
   * @param id 数据源id
   * @param ds 数据源描述信息
   * @return 状态码
   *     <p>200：修改成功
   *     <p>其他：修改失败
   */
  int modifyDSMeta(int id, DataResource ds);

  /**
   * 预览数据
   * @param str
   * @param index
   * @param list
   * @return
   */
  JSONObject previewData(String str, String index, List<DataResouceDetail> list);
}
