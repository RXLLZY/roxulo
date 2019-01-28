package com.swt.controller;

import com.swt.entity.TablePropertiesEntity;
import com.swt.service.SysGeneratorService;
import com.swt.utils.PageUtils;
import com.swt.utils.Query;
import com.swt.utils.R;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 *
 * @author RoXuLo
 * @email @shuweitech.com
 * @date 2016年12月19日 下午9:12:58
 */
@Controller
@RequestMapping("/sys/generator")
public class SysGeneratorController {
    @Autowired
    private SysGeneratorService sysGeneratorService;

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<TablePropertiesEntity> list = sysGeneratorService.queryList(query);
        int total = sysGeneratorService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 生成代码
     */
    @RequestMapping("/export")
    public void export(@RequestBody List<TablePropertiesEntity> tables, HttpServletResponse response) throws IOException {
        byte[] data = sysGeneratorService.generatorZipCode(tables);

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"code.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        response.addHeader("Param","no-cache");
        response.addHeader("Cache-Control","no-cache");
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.write(data, outputStream);
        outputStream.flush();
        outputStream.close();
    }


    /**
     * 生成代码
     */
    @ResponseBody
    @RequestMapping("/code")
    public R code(@RequestBody List<TablePropertiesEntity> tables, HttpServletResponse response) throws IOException {
        sysGeneratorService.generatorCode(tables);
        return R.ok("生成代码成功");
    }

    /**
     * 删除代码
     */
    @ResponseBody
    @RequestMapping("/deleteCode")
    public R deleteCode(@RequestBody List<TablePropertiesEntity> tables, HttpServletResponse response) throws IOException {
        List<String> strings = sysGeneratorService.deleteCode(tables);
        return R.ok("成功删除如下文件：<br/>" + StringUtils.join(strings, "<br/>"));
    }
}
