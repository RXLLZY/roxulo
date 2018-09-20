package com.swt.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.swt.common.utils.PageUtils;
import com.swt.modules.sys.entity.SysFileEntity;

import java.util.Map;

/**
 * 静态资源
 *
 * @author RoXuLo
 * @email shuweitech.com
 * @date 2018-09-19 18:11:12
 */
public interface SysFileService extends IService<SysFileEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

