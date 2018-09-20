package com.swt.modules.sys.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 登录表单
 *
 * @author Mark shuweitech.com
 * @since 2.0.0 2018-01-25
 */
@ApiModel(value = "登录表单")
public class SysLoginForm {
    @ApiModelProperty(value = "用户名",example = "admin",required = true)
    @NotBlank(message="用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码",example = "admin",required = true)
    @NotBlank(message="密码不能为空")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String captcha;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
