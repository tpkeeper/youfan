package com.tk.youfan.dao;

/**
 * 作者：tpkeeper on 2016/10/10 21:19
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：商品数据
 */
public class GoodsTable {
    public static String TAB_NAME = "tab_goods";
    public static String COL_ID = "id";
    public static String COL_PROD_CLS_NUM = "prod_cls_num";//款式
    public static String COL_LM_PROD_CLS_ID = "lm_prod_cls_id";//样式
    public static String COL_COUNT = "count";//数量
    public static String CREATE_TAB = "create table "
            + TAB_NAME
            + " ( "
            + COL_ID + " text primary key , "
            + COL_PROD_CLS_NUM + " text , "
            + COL_LM_PROD_CLS_ID + " text , "
            + COL_COUNT + " integer "
            + " )";
}
