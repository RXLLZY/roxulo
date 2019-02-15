package com.swt.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.swt.common.utils.PageUtils;
import com.swt.modules.sys.entity.SysDictEntity;

import java.util.Map;

/**
 * 数据字典
 *
 * @author Mark @shuweitech.com
 * @since 3.1.0 2018-01-27
 */
public interface SysDictService extends IService<SysDictEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

