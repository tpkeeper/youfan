package com.tk.youfan.domain.home;

import java.util.List;

/**
 * 作者：tpkeeper on 2016/9/28 10:24
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：首页展示的module
 */
public class Module {

    /**
     * id : 9
     * c_title : 流行资讯
     * e_title : Magazine
     * module_key : imgModule
     * sort : 26
     * is_more : 1
     * more_jump_id : 88745
     * jump : {}
     * data : []
     */

    private int id;
    private String c_title;
    private String e_title;
    private String module_key;
    private String sort;
    private String is_more;
    private String more_jump_id;

    private Jump jump;
    private List<GoodsInfo> goodsInfoList;

    public List<GoodsInfo> getGoodsInfoList() {
        return goodsInfoList;
    }

    public void setGoodsInfoList(List<GoodsInfo> goodsInfoList) {
        this.goodsInfoList = goodsInfoList;
    }

    public Jump getJump() {
        return jump;
    }

    public void setJump(Jump jump) {
        this.jump = jump;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getC_title() {
        return c_title;
    }

    public void setC_title(String c_title) {
        this.c_title = c_title;
    }

    public String getE_title() {
        return e_title;
    }

    public void setE_title(String e_title) {
        this.e_title = e_title;
    }

    public String getModule_key() {
        return module_key;
    }

    public void setModule_key(String module_key) {
        this.module_key = module_key;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getIs_more() {
        return is_more;
    }

    public void setIs_more(String is_more) {
        this.is_more = is_more;
    }

    public String getMore_jump_id() {
        return more_jump_id;
    }

    public void setMore_jump_id(String more_jump_id) {
        this.more_jump_id = more_jump_id;
    }
}
