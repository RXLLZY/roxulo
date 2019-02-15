package com.swt.modules.sys.service;


import com.baomidou.mybatisplus.service.IService;
import com.swt.common.utils.PageUtils;
import com.swt.modules.sys.entity.SysLogEntity;

import java.util.Map;


/**
 * 系统日志
 * 
 * @author RoXuLo
 * @email @shuweitech.com
 * @date 2017-03-08 10:40:56
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
