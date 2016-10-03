package com.tk.youfan.domain.search;

import java.util.List;

/**
 * 作者：tpkeeper on 2016/10/1 18:21
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class Parent {
    /**
     * id : 20
     * parent_id : 1
     * cate_name : 男上装
     * level : 2
     * img : http://7xjir4.com2.z0.glb.qiniucdn.com/Fv8N_O1lECIXkN88Mb50auDOvsaA
     * subs : []
     */

    private String id;
    private String parent_id;
    private String cate_name;
    private String level;
    private String img;
    private List<Sub> subs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<Sub> getSubs() {
        return subs;
    }

    public void setSubs(List<Sub> subs) {
        this.subs = subs;
    }
}
