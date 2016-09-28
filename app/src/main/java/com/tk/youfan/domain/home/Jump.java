package com.tk.youfan.domain.home;

/**
 * 作者：tpkeeper on 2016/9/28 09:42
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：点击图片要跳转的页面信息
 */
public class Jump {


    /**
     * id : 208773
     * name : 新人价
     * type : 1
     * is_h5 : 0
     * url :
     * tid : 816571
     * ext :
     * jump_type : 1
     */

    private String id;
    private String name;
    private String type;
    private String is_h5;
    private String url;
    private String tid;
    private String ext;
    private String jump_type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIs_h5() {
        return is_h5;
    }

    public void setIs_h5(String is_h5) {
        this.is_h5 = is_h5;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getJump_type() {
        return jump_type;
    }

    public void setJump_type(String jump_type) {
        this.jump_type = jump_type;
    }
}
