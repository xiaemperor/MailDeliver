package com.mawenxia.mail.config.database;

/**
 * Created by sam on 2017/5/22.
 */
public class DataBaseContextHolder {
    public enum DataBaseType{
        MASTER,SLAVE
    }
    private static final ThreadLocal<DataBaseType> contextHolder = new ThreadLocal<>();

    public static void setDataBaseType(DataBaseType dataBaseType){
        if (dataBaseType == null){
            throw new NullPointerException();
        }else {
            contextHolder.set(dataBaseType);
        }
    }
    public static DataBaseType getDataBaseType(){
        return contextHolder.get() == null?DataBaseType.MASTER:contextHolder.get();
    }
    public static void clearDataBaseType(){
        contextHolder.remove();
    }
}
