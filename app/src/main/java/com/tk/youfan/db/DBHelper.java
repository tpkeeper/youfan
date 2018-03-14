package com.tk.youfan.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tk.youfan.dao.GoodsTable;

/**
 * 作者：tpkeeper on 2016/10/10 21:35
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class DBHelper extends SQLiteOpenHelper{
    public DBHelper(Context context, String name) {
        super(context, name, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GoodsTable.CREATE_TAB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
