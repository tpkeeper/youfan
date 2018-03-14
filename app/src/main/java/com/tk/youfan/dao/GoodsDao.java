package com.tk.youfan.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.tk.youfan.db.DBHelper;
import com.tk.youfan.domain.purchase.Goods;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：tpkeeper on 2016/10/10 21:33
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class GoodsDao {
    private final DBHelper dbHelper;

    public GoodsDao(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public List<Goods> getGoodsList() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from " + GoodsTable.TAB_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        List<Goods> goodsList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Goods goods = new Goods();
            goods.setId(cursor.getString(cursor.getColumnIndex(GoodsTable.COL_ID)));
            goods.setProdClsNum(cursor.getString(cursor.getColumnIndex(GoodsTable.COL_PROD_CLS_NUM)));
            goods.setLmProdClsId(cursor.getString(cursor.getColumnIndex(GoodsTable.COL_LM_PROD_CLS_ID)));
            goods.setCount(cursor.getInt(cursor.getColumnIndex(GoodsTable.COL_COUNT)));
            goodsList.add(goods);
        }
        cursor.close();
        return goodsList;
    }

    public void saveGoods(Goods goods) {
        if (goods == null) {
            return;
        }
        //查看数据库中有没有重复的
        Goods goodsById = getGoodsById(goods.getId());
        if(goodsById!=null) {
            updateGoods(goods);
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "insert into " + GoodsTable.TAB_NAME + " values (?,?,?,?)";
        db.execSQL(sql, new String[]{goods.getId(), goods.getProdClsNum(), goods.getLmProdClsId(), goods.getCount() + ""});
    }

    public void saveGoodsList(List<Goods> goodsList) {
        if (goodsList == null || goodsList.size() <= 0) {
            return;
        }
        for (int i = 0; i < goodsList.size(); i++) {
            saveGoods(goodsList.get(i));
        }
    }

    public void deleteById(String id) {
        if (TextUtils.isEmpty(id)) {
            return;
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "delete from " + GoodsTable.TAB_NAME + " where " + GoodsTable.COL_ID + " = ?";
        db.execSQL(sql, new String[]{id});
    }

    public Goods getGoodsById(String id) {
        if (TextUtils.isEmpty(id)) {
            return null;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from " + GoodsTable.TAB_NAME +" where " +GoodsTable.COL_ID +" = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{id});
        Goods goods = null;
        if(cursor.moveToNext()) {
            goods = new Goods();
            goods.setId(cursor.getString(cursor.getColumnIndex(GoodsTable.COL_ID)));
            goods.setProdClsNum(cursor.getString(cursor.getColumnIndex(GoodsTable.COL_PROD_CLS_NUM)));
            goods.setLmProdClsId(cursor.getString(cursor.getColumnIndex(GoodsTable.COL_LM_PROD_CLS_ID)));
            goods.setCount(cursor.getInt(cursor.getColumnIndex(GoodsTable.COL_COUNT)));
        }
        return goods;
    }

    public void updateGoods(Goods goods){
        if(goods==null) {
            return;
        }
        Goods goodsById = getGoodsById(goods.getId());
        if(goodsById==null) {
            saveGoods(goods);
        }else {
            deleteById(goods.getId());
            saveGoods(goods);
        }
    }

}
