package com.swt.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swt.common.utils.PageInfo;
import com.swt.common.utils.PageUtils;
import com.swt.modules.sys.entity.SysFileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 静态资源
 *
 * @author RoXuLo
 * @email shuweitech.com
 * @date 2018-09-19 18:11:12
 */
public interface ISysFileService extends IService<SysFileEntity> {

    PageUtils queryPage(PageInfo pageInfo, String originalName);

    SysFileEntity upload(MultipartFile file);

    SysFileEntity uploadAndAdd(MultipartFile file);
}

