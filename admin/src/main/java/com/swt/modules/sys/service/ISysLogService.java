package com.swt.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.swt.common.utils.PageInfo;
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
public interface ISysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(PageInfo pageInfo, String key);

}
