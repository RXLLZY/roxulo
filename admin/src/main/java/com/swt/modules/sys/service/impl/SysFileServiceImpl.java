package com.swt.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.swt.common.utils.PageUtils;
import com.swt.common.utils.Query;

import com.swt.modules.sys.dao.SysFileDao;
import com.swt.modules.sys.entity.SysFileEntity;
import com.swt.modules.sys.service.SysFileService;


@Service("sysFileService")
public class SysFileServiceImpl extends ServiceImpl<SysFileDao, SysFileEntity> implements SysFileService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SysFileEntity> page = this.selectPage(
                new Query<SysFileEntity>(params).getPage(),
                new EntityWrapper<SysFileEntity>()
        );

        return new PageUtils(page);
    }

}
