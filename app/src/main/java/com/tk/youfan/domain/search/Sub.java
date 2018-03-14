package com.tk.youfan.domain.search;

/**
 * 作者：tpkeeper on 2016/10/1 18:20
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：分类子类
 */
public class Sub {
    /**
     * id : 209
     * parent_id : 20
     * cate_name : T恤
     * level : 3
     * img : http://7xjir4.com2.z0.glb.qiniucdn.com/Fh2e9M1tWCkScnBXzUfJwybIFlRM
     */

    private String id;
    private String parent_id;
    private String cate_name;
    private String level;
    private String img;

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
}
