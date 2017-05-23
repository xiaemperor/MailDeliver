package com.mawenxia.mail.config.database;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Created by sam on 2017/5/22.
 */
@Aspect
@Component
public class ReadOnlyConnectionInterceptor implements Ordered{

    public static final Logger LOGGER = LoggerFactory.getLogger(ReadOnlyConnectionInterceptor.class);
    @Around("@annotation(readOnlyConnection)")
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint,ReadOnlyConnection readOnlyConnection) throws Throwable{
        try{
            LOGGER.info("---------set database connection 2 readonly-----");
            DataBaseContextHolder.setDataBaseType(DataBaseContextHolder.DataBaseType.SLAVE);
            Object result = proceedingJoinPoint.proceed();
            return result;
        }finally {
            DataBaseContextHolder.clearDataBaseType();
            LOGGER.info("-----------clear database connection------");
        }

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
