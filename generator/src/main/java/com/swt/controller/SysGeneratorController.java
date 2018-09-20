package com.swt.controller;

import com.swt.service.SysGeneratorService;
import com.swt.utils.PageUtils;
import com.swt.utils.Query;
import com.swt.utils.R;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<Map<String, Object>> list = sysGeneratorService.queryList(query);
		int total = sysGeneratorService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 生成代码
	 */
	@RequestMapping("/export")
	public void export(String tables, HttpServletResponse response) throws IOException{
		byte[] data = sysGeneratorService.generatorZipCode(tables.split(","));
		
		response.reset();  
        response.setHeader("Content-Disposition", "attachment; filename=\"swt.zip\"");
        response.addHeader("Content-Length", "" + data.length);  
        response.setContentType("application/octet-stream; charset=UTF-8");  
  
        IOUtils.write(data, response.getOutputStream());  
	}


	/**
	 * 生成代码
	 */
	@ResponseBody
	@RequestMapping("/code")
	public R code(String tables, HttpServletResponse response) throws IOException{
		sysGeneratorService.generatorCode(tables.split(","));
		return R.ok().put("msg","生成代码成功");
	}

	/**
	 * 删除代码
	 */
	@ResponseBody
	@RequestMapping("/deleteCode")
	public R deleteCode(String tables, HttpServletResponse response) throws IOException{
		List<String> strings = sysGeneratorService.deleteCode(tables.split(","));
		return R.ok().put("msg","成功删除如下文件：<br/>"+ StringUtils.join(strings,"<br/>"));
	}
}
