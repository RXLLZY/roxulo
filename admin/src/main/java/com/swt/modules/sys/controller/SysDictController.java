package com.swt.modules.sys.controller;

import com.swt.common.controller.AbstractController;
import com.swt.common.responses.Responses;
import com.swt.common.utils.PageUtils;
import com.swt.common.validator.ValidatorUtils;
import com.swt.modules.sys.entity.SysDictEntity;
import com.swt.modules.sys.service.SysDictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 数据字典
 *
 * @author Mark @shuweitech.com
 * @since 3.1.0 2018-01-27
 */
@RestController
@RequestMapping("sys/dict")
public class SysDictController extends AbstractController {
    @Autowired
    private SysDictService sysDictService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dict:list")
    public Responses<PageUtils> list(@RequestParam Map<String, Object> params){
        PageUtils page = sysDictService.queryPage(params);

        return success(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:dict:info")
    public Responses<SysDictEntity> info(@PathVariable("id") Long id){
        SysDictEntity dict = sysDictService.getById(id);

        return success(dict);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dict:save")
    public Responses<SysDictEntity> save(@RequestBody SysDictEntity dict){
        //校验类型
        ValidatorUtils.validateEntity(dict);

        sysDictService.save(dict);

        return success(dict);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dict:update")
    public Responses<SysDictEntity> update(@RequestBody SysDictEntity dict){
        //校验类型
        ValidatorUtils.validateEntity(dict);

        sysDictService.updateById(dict);

        return success(dict);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dict:delete")
    public Responses<Void> delete(@RequestBody Long[] ids){
        sysDictService.deleteBatchIds(Arrays.asList(ids));

        return success(HttpStatus.NO_CONTENT);
    }

}
