package com.swt.common.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2018/6/27 0027.
 */
public class CronUtils {

    private static CronUtils instance;

    private CronUtils(){

    }

    public static synchronized CronUtils getInstance(){
        if(instance==null)
            instance = new CronUtils();
        return instance;
    }

    public String CronTranse(String jsonstr){
        JSONObject jsonObject = JSONObject.parseObject(jsonstr);
        int jobGroup = jsonObject.getInteger("jobGroup");
        String  monthDay = jsonObject.getString("monthDay");
        String weekDay = jsonObject.getString("weekDay");
        String hourMinutes = jsonObject.getString("hourMinutes");
        String time = jsonObject.getString("time");
        String cron = "";
        //单次执行
        if(jobGroup==0){
            cron = "%d %d/%d %d/%d %d/%d %d/%d ? %d";
            String data[] = time.split(" ");
            String date[] = data[0].split("/");
            String times[] = data[1].split(":");
            cron = String.format(cron,Integer.parseInt(times[2]),Integer.parseInt(times[1]),0,Integer.parseInt(times[0]),0,Integer.parseInt(date[2]),0,Integer.parseInt(date[1]),0,Integer.parseInt(date[0]));
        }
        //重复执行,每月按日执行
        else if(jobGroup==1){
            cron = "%s %s %s %s * ?";
            cron = String.format(cron,0,hourMinutes.split(":")[1],hourMinutes.split(":")[0],monthDay);
        }
        //重复执行,每周按日执行
        else if(jobGroup==2){
            cron = "%s %s %s ? * %s";
            cron = String.format(cron,0,hourMinutes.split(":")[1],hourMinutes.split(":")[0],weekDay);
        }
        //重复执行,每日定时执行
        else if(jobGroup==3){
            cron = "%s %s %s * * ?";
            cron = String.format(cron,0,hourMinutes.split(":")[1],hourMinutes.split(":")[0]);
        }
        System.out.println(cron);
        return cron;
    }

    public static void main(String args[]){
        String cron = "%d %d/%d %d/%d %d/%d %d/%d ? %d";
        System.out.print(String.format(cron,0,0,1,16,0,27,0,6,0,2018));
    }

}
