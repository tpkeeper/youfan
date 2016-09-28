package com.tk.youfan.domain.home;

/**
 * 作者：tpkeeper on 2016/9/28 10:17
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：用于展示在module的每个商品信息
 */
public class GoodsInfo {

    /**
     * title : 抓绒印花拉链连帽卫衣
     * jump_id : 208773
     * replace_param : 816571
     * img : http://img6.ibanggo.com/sources/images/goods/TP/816571/816571_00.jpg
     * sort : 2
     * module_id : 4
     * jump : {"id":"208773","name":"新人价","type":"1","is_h5":"0","url":"","tid":"816571","ext":"","jump_type":"1"}
     * price_tag : 新人价
     * product_price : 168.0
     */

    private String title;
    private String jump_id;
    private String replace_param;
    private String img;
    private String sort;
    private String module_id;
    private String price_tag;
    private double product_price;
    private Jump jump;

    public Jump getJump() {
        return jump;
    }

    public void setJump(Jump jump) {
        this.jump = jump;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJump_id() {
        return jump_id;
    }

    public void setJump_id(String jump_id) {
        this.jump_id = jump_id;
    }

    public String getReplace_param() {
        return replace_param;
    }

    public void setReplace_param(String replace_param) {
        this.replace_param = replace_param;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getModule_id() {
        return module_id;
    }

    public void setModule_id(String module_id) {
        this.module_id = module_id;
    }

    public String getPrice_tag() {
        return price_tag;
    }

    public void setPrice_tag(String price_tag) {
        this.price_tag = price_tag;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }
}
