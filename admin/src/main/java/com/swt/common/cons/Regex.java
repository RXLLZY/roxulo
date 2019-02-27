package com.swt.common.cons;

public class Regex {

    /**
     * 正则表达式：验证用户名
     */
    public static final String USERNAME = "^[a-zA-Z]\\w{5,17}$";
    /**
     * 正则表达式：验证密码 (必须字母与数字组合)
     */
    public static final String PASSWORD = "^(?!([a-zA-Z]+|\\d+)$)[a-zA-Z\\d]{6,14}$";
    /**
     * 正则表达式：验证手机号
     */
    public static final String PHONE = "^1[3,4,5,7,8]\\d{9}$";
    /**
     * 正则表达式：验证邮箱
     */
    public static final String EMAIL = "^([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    /**
     * 正则表达式：验证汉字
     */
    public static final String CHINESE = "^[\u4e00-\u9fa5],*$";
    /**
     * 正则表达式：验证身份证
     */
    public static final String ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
    /**
     * 正则表达式：验证URL
     */
    public static final String URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    /**
     * 正则表达式：验证IP地址
     */
    public static final String IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    /**
     * 正则表达式：验证数字
     */
    public static final String NUMBER = "[0-9]*";
    /**
     * 正则表达式：验证图片格式
     */
    public static final String IMAGE = "(?i)(jpg|jpeg|png|gif)$";
    /**
     * 正则表达式：
     */
    public static final String DUPLICATE_ENTRY = "'(.*?)'";
}
