package com.tk.youfan.db;

import android.content.Context;

import com.tk.youfan.domain.home.Module;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者：tpkeeper on 2016/10/10 23:51
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class Model {
    private DBManager dbManager;
    private Context mContext;
    private static Model model = new Model();
    private Model(){
    }
    public static Model getInstance(){
        return model;
    }
    public void init(Context mContext,String info){
        this.mContext = mContext;
        dbManager = new DBManager(mContext,info);
    }
    public DBManager getDbManager(){
        return dbManager;
    }
    private ExecutorService executorService = Executors.newCachedThreadPool();
    public ExecutorService getGlobalThreadPool(){
        return executorService;
    }
}
