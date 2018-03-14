package com.tk.youfan.domain.search.branddetail;

/**
 * 作者：tpkeeper on 2016/10/5 16:19
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class BrandStoryTab {
    /**
     * id : 0
     * key : A
     * img : http://7xjir4.com2.z0.glb.qiniucdn.com/Fi01zSNjTXxtXzSPqDiOPf_sg2NU
     * home_url :
     * story_url :
     * is_choose : false
     */

    private int id;
    private String key;
    private String img;
    private String home_url;
    private String story_url;
    private boolean is_choose;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getHome_url() {
        return home_url;
    }

    public void setHome_url(String home_url) {
        this.home_url = home_url;
    }

    public String getStory_url() {
        return story_url;
    }

    public void setStory_url(String story_url) {
        this.story_url = story_url;
    }

    public boolean isIs_choose() {
        return is_choose;
    }

    public void setIs_choose(boolean is_choose) {
        this.is_choose = is_choose;
    }
}
