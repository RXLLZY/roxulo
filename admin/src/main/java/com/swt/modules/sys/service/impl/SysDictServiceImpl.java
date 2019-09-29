package com.swt.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swt.common.utils.PageData;
import com.swt.common.utils.PageInfo;
import com.swt.common.utils.PageUtils;
import com.swt.modules.sys.dao.ISysDictDao;
import com.swt.modules.sys.entity.SysDictEntity;
import com.swt.modules.sys.service.ISysDictService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;


@Service("sysDictService")
public class SysDictServiceImpl extends ServiceImpl<ISysDictDao, SysDictEntity> implements ISysDictService {

    @Override
    public PageUtils queryPage(PageInfo pageInfo, String name) {

        IPage<SysDictEntity> page = this.baseMapper.selectPage(
                new PageData<>(pageInfo),
                new QueryWrapper<SysDictEntity>()
                    .like(StringUtils.isNotBlank(name),"name", name)
        );

        return new PageUtils(page);
    }

}
