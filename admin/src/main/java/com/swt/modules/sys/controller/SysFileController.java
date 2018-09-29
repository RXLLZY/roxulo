package com.swt.modules.sys.controller;

import com.swt.common.annotation.SysLog;
import com.swt.common.controller.AbstractController;
import com.swt.common.utils.ConfigConstant;
import com.swt.common.utils.PageUtils;
import com.swt.common.utils.R;
import com.swt.common.validator.ValidatorUtils;
import com.swt.modules.sys.entity.SysFileEntity;
import com.swt.modules.sys.service.SysFileService;
import io.swagger.annotations.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
 * 静态资源管理
 *
 * @author RoXuLo
 * @email shuweitech.com
 * @date 2018-09-19 18:11:12
 */
@RestController
@RequestMapping("sys/file")
@Api(tags = "静态资源管理",description="静态资源 Controller")
public class SysFileController extends AbstractController{
    @Autowired
    private SysFileService sysFileService;

    /**
     * 上传文件
     */
    @ResponseBody
    @PostMapping("/upload")
    @ApiOperation(value = "uploadFile", notes = "上传文件",consumes="multipart/form-data",produces="application/json")
    public R upload(@RequestParam("file") MultipartFile file){

        SysFileEntity sysFileEntity = sysFileService.upload(file);

        return R.ok().put("sysFile", sysFileEntity);
    }

    /**
     * 上传文件
     */
    @ResponseBody
    @PostMapping("/uploadAndAdd")
    @ApiOperation(value = "uploadFile", notes = "上传文件",consumes="multipart/form-data",produces="application/json")
    public R uploadAndAdd(@RequestParam("file") MultipartFile file){

        SysFileEntity sysFileEntity = sysFileService.uploadAndAdd(file);

        return R.ok().put("sysFile", sysFileEntity);
    }
    /**
     * 列表
     */
    @GetMapping
    @ApiOperation(value = "列表", notes = "分页查询静态资源",produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = false, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = false, paramType = "query", dataType = "int", defaultValue = "10"),
            @ApiImplicitParam(name = "sidx", value = "页码", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "order", value = "页码", required = false, paramType = "query", dataType = "string", defaultValue = "asc"),
    })
    public R list(@RequestParam @ApiParam(hidden = true) Map<String, Object> params) {
        PageUtils page = sysFileService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 记录
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "记录", notes = "根据ID查询单条静态资源详情",produces="application/json")
    @ApiImplicitParam(name = "id", value = "静态资源ID", required = true,paramType = "path", dataType = "Integer", defaultValue="Integer")
    public R info(@PathVariable("id") Integer id){
        SysFileEntity sysFile = sysFileService.selectById(id);

        return R.ok().put("sysFile", sysFile);
    }

    /**
     * 记录
     */
    @SysLog("新增静态资源")
    @PostMapping
    @ApiOperation(value = "新增", notes = "新增静态资源",produces="application/json")
    public R save(@RequestBody SysFileEntity sysFile){
        //添加用户名
        sysFile.setCrtUserId(getUserId());
        //添加创建时间
        sysFile.setCrtTime(new Date());
        //校验
        ValidatorUtils.validateEntity(sysFile);
        sysFileService.insert(sysFile);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改静态资源")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改", notes = "修改静态资源",produces="application/json")
    @ApiImplicitParam(name = "id", value = "静态资源ID", required = true,paramType = "path", dataType = "Integer", defaultValue="Integer")
    public R update(@PathVariable("id") Integer id,@RequestBody SysFileEntity sysFile){
        sysFile.setId(id);
        //校验
        ValidatorUtils.validateEntity(sysFile);
        //全部更新
        sysFileService.updateAllColumnById(sysFile);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除静态资源")
    @DeleteMapping
    @ApiOperation(value = "删除", notes = "删除静态资源",produces="application/json")
    @ApiImplicitParam(name = "ids", value = "静态资源ID", required = true,paramType = "body", dataType = "Integer[]", defaultValue="[Integer]")
    public R delete(@RequestBody Integer[] ids){
        for (int i = 0; i < ids.length; i++) {
            SysFileEntity sysFileEntity = sysFileService.selectById(ids[i]);
            FileUtils.deleteQuietly(new File(ConfigConstant.CONTENT_PATH + sysFileEntity.getPath()));
        }
        sysFileService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
