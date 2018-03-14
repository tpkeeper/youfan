package com.tk.youfan.domain.home;

import java.util.List;

/**
 * 作者：tpkeeper on 2016/9/28 10:47
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class HomeData {

    /**
     * id : 23
     * name : 首页
     * title : 首页
     * is_delete : 0
     * create_time : 2016-04-29 00:00:00
     * page : home
     * module : []
     * updateNum : {"productNum":0,"specialNum":0}
     */

    private String id;
    private String name;
    private String title;
    private String is_delete;
    private String create_time;
    private String page;
    private List<Module> module;
    private UpdateNum updateNum;

    public UpdateNum getUpdateNum() {
        return updateNum;
    }

    public void setUpdateNum(UpdateNum updateNum) {
        this.updateNum = updateNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<Module> getModule() {
        return module;
    }

    public void setModule(List<Module> module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return "HomeData{" +
                "create_time='" + create_time + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", is_delete='" + is_delete + '\'' +
                ", page='" + page + '\'' +
                ", module=" + module +
                ", updateNum=" + updateNum +
                '}';
    }
}
