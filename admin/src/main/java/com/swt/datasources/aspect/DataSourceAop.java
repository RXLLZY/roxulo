package com.swt.datasources.aspect;

import com.swt.datasources.DataSourceNames;
import com.swt.datasources.DynamicDataSource;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * desc: 数据源切面配置 指定文件
 * Created by jack-cooper on 2017/1/18.
 */
//@Aspect
//@Order(-1)
//@Component
public class DataSourceAop {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.swt.modules.*.service..*.*(..))")
    public void setCenterUserDataSourceType() {
        DynamicDataSource.setDataSource(DataSourceNames.FIRST);
        log.info("dataSource == >:" + DataSourceNames.FIRST);
    }

//    @Before("execution(* com.swt.modules.*.service..*.*(..))")
//    public void setUserCenterDataSourceType(){
//        DynamicDataSource.setDataSource(DataSourceNames.SECOND);
//        log.info("dataSource == >：userCenter");
//    }

    @After("execution(* com.swt.modules.*.service.*..*.*(..)) ")
    public void afterReturning(){
        DynamicDataSource.clearDataSource();
        log.info("=====> clear dataSource aop ");
    }
}