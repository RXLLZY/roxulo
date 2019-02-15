package com.swt.modules.sys.controller;


import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.swt.common.utils.R;
import com.swt.common.validator.ValidatorUtils;
import com.swt.modules.sys.form.SysLoginForm;
import com.swt.modules.sys.shiro.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录相关
 * 
 * @author RoXuLo
 * @email @shuweitech.com
 * @date 2016年11月10日 下午1:15:31
 */
@Controller
@Api(tags = "用户登陆",description="用户登陆")
public class SysLoginController {
	@Autowired
	private Producer producer;

	@GetMapping("captcha.jpg")
	@ApiOperation(value = "生成验证码", notes = "随机生成验证码",produces="image/jpeg")
	public void captcha(HttpServletResponse response)throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
	}
	
	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	@ApiOperation("登录")
	public R login(@RequestBody SysLoginForm form) {
//		String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
//		if(!captcha.equalsIgnoreCase(form.getCaptcha())){
//			return R.error("验证码不正确");
//		}
		//表单校验
		ValidatorUtils.validateEntity(form);
		try{
			Subject subject = ShiroUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(form.getUsername(), form.getPassword());
			subject.login(token);
		}catch (UnknownAccountException e) {
			return R.error(e.getMessage());
		}catch (IncorrectCredentialsException e) {
			return R.error("账号或密码不正确");
		}catch (LockedAccountException e) {
			return R.error("账号已被锁定,请联系管理员");
		}catch (AuthenticationException e) {
			return R.error("账户验证失败");
		}
	    
		return R.ok();
	}

	@ResponseBody
	@RequestMapping("unauthorized")
	public R unauthorized(){
		return R.error(401, "您已登出或者登陆超时");
	}
	/**
	 * 退出
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	@ApiOperation(value = "退出登陆", notes = "退出登陆")
	public String logout() {
		ShiroUtils.logout();
		return "redirect:login.html";
	}
	
}
