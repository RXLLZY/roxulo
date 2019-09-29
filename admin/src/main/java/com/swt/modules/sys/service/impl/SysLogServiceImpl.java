package com.swt.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swt.common.utils.PageData;
import com.swt.common.utils.PageInfo;
import com.swt.common.utils.PageUtils;
import com.swt.modules.sys.dao.ISysLogDao;
import com.swt.modules.sys.entity.SysLogEntity;
import com.swt.modules.sys.service.ISysLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<ISysLogDao, SysLogEntity> implements ISysLogService {

    @Override
    public PageUtils queryPage(PageInfo pageInfo, String key) {

        IPage<SysLogEntity> page = this.baseMapper.selectPage(
                new PageData<>(pageInfo),
            new QueryWrapper<SysLogEntity>().like(StringUtils.isNotBlank(key),"username", key)
        );

        return new PageUtils(page);
    }
}
