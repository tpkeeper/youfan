package com.tk.youfan.domain.inspiration;

import java.util.List;

/**
 * 作者：tpkeeper on 2016/10/3 13:21
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class ItemChild {
    /**
     * id : 15713
     * title : NEWS｜Rihanna第二个设计系列亮相巴黎
     * product_list : []
     * img : http://7xjir4.com2.z0.glb.qiniucdn.com/FmVk0FqqDHciI1LiZpHA0XPtj985
     * keywords_list : []
     * url : http://m.funwear.com/wx-sp/special-insp.html?p=15713&navi=bottom
     */

    private String id;
    private String title;
    private String img;
    private String url;
    private List<String> product_list;
    private List<String> keywords_list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(List<String> product_list) {
        this.product_list = product_list;
    }

    public List<String> getKeywords_list() {
        return keywords_list;
    }

    public void setKeywords_list(List<String> keywords_list) {
        this.keywords_list = keywords_list;
    }
}
