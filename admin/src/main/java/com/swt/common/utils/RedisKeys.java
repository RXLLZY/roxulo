package com.swt.common.utils;

/**
 * Redis所有Keys
 *
 * @author Mark @shuweitech.com
 * @since 3.0.0 2017-07-18
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }

    public static String getShiroSessionKey(String key){
        return "sessionid:" + key;
    }
}
