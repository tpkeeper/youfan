package com.tk.youfan.domain;

/**
 * 作者：tpkeeper on 2016/9/21 08:48
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class EventMessage {

    public static final String MESSAGE_UIUPDATE = "message_update";
    public static final String MESSAGE_SLEEP = "message_sleep";
    public static final String MESSAGE_DATA_GETED="message_data_geted";
    public static final String MESSAGE_DATA_EMPTY = "message_data_empty";
    public static final String MESSAGE_OFFLINE = "message_offline";
    public static final String MESSAGE_DATA_GETED_HomeData = "message_data_geted_homedata";
    public static final String MESSAGE_DATA_GETED_HomeData_LIKE_MODULE ="message_data_geted_homedata_like_module";

    public EventMessage(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
