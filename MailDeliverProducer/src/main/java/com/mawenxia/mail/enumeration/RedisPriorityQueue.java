package com.mawenxia.mail.enumeration;

/**
 * Created by sam on 2017/5/31.
 * <p>
 * coding like an artist
 */
public enum  RedisPriorityQueue {
    //7.8.9隐私、安全、交易
    FAST_QUEUE("fast"),
    //4.5.6活动、通知
    NORMAL_QUEUE("normal"),
    //1.2.3汇总、调查
    DEFER_QUEUE("defer");

    private String code;


    private RedisPriorityQueue(String code) {
        this.code = code;
    }


    public String getCode() {
        return code;
    }

}
