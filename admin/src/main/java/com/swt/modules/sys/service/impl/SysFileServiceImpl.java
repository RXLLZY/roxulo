package com.swt.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swt.common.exception.RRException;
import com.swt.common.utils.ConfigConstant;
import com.swt.common.utils.PageData;
import com.swt.common.utils.PageInfo;
import com.swt.common.utils.PageUtils;
import com.swt.modules.sys.dao.ISysFileDao;
import com.swt.modules.sys.entity.SysFileEntity;
import com.swt.modules.sys.service.ISysFileService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service("sysFileService")
public class SysFileServiceImpl extends ServiceImpl<ISysFileDao, SysFileEntity> implements ISysFileService {


    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    @Override
    public PageUtils queryPage(PageInfo pageInfo, String originalName) {
        IPage<SysFileEntity> page = this.baseMapper.selectPage(
                new PageData<>(pageInfo),
                new QueryWrapper<SysFileEntity>()
                        .like(StringUtils.isNotBlank(originalName),"original_name", originalName)
        );

        return new PageUtils(page);
    }

    @Override
    public SysFileEntity upload(MultipartFile file) {
        String name = file.getOriginalFilename();
        String filePath;
        String frontPath;
        try {
            frontPath = ConfigConstant.RESOURCE_PATH + file.getContentType().split("/")[0] + "/" +simpleDateFormat.format(new Date()) + name.substring(name.lastIndexOf("."));
            filePath = ConfigConstant.CONTENT_PATH + frontPath;
        }catch (Exception e){
            throw  new RRException("文件名不合法");
        }
        File saveFile = new File(filePath);
        OutputStream os = null;
        try {
            FileUtils.touch(saveFile);
            os = new FileOutputStream(saveFile);
            IOUtils.copy(file.getInputStream(),os);
        } catch (FileNotFoundException e) {
            throw new RRException("路径不合法");
        }catch (IOException e) {
            throw  new RRException("IO异常");
        }finally {
            IOUtils.closeQuietly(os);
        }
        SysFileEntity sysFileEntity = new SysFileEntity();
        sysFileEntity.setOriginalName(file.getOriginalFilename());
        sysFileEntity.setContentType(file.getContentType());
        sysFileEntity.setPath(frontPath);
        String size = FileUtils.byteCountToDisplaySize(file.getSize());
        sysFileEntity.setSize(size);
        return sysFileEntity;
    }

    @Override
    public SysFileEntity uploadAndAdd(MultipartFile file) {
        SysFileEntity sysFileEntity = upload(file);
        this.save(sysFileEntity);

        return sysFileEntity;
    }

}
