package com.tk.youfan.db;

import android.content.Context;

import com.tk.youfan.dao.GoodsDao;

/**
 * 作者：tpkeeper on 2016/10/10 23:44
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class DBManager {
    private final DBHelper dbHelper;
    private final GoodsDao goodsDao;

    public DBManager(Context context, String name) {
        this.dbHelper = new DBHelper(context,name);
        this.goodsDao = new GoodsDao(dbHelper);
    }
    public GoodsDao getGoodsDao(){
        return goodsDao;
    }
    public void close(){
        dbHelper.close();
    }

}
