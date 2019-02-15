package com.swt.common.utils;

import com.swt.modules.sys.entity.SysFileEntity;

/**
 * 系统参数相关Key
 *
 * @author Mark @shuweitech.com
 * @since 1.2.0 2017-03-26
 */
public class ConfigConstant {

    /**
     * 项目路径
     */
    public static final String CONTENT_PATH = SysFileEntity.class.getResource("/").getPath();

    /**
     * 资源路径
     */
    public static final String RESOURCE_PATH = "/statics/";
}
