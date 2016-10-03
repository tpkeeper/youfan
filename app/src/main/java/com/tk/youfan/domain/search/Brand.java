package com.tk.youfan.domain.search;

/**
 * 作者：tpkeeper on 2016/10/1 22:58
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：分类的品牌页面
 */
public class Brand {
    /**
     * id : 10481
     * brand_code : puma
     * name : puma
     * img : http://metersbonwe.qiniucdn.com/zshj_pp10078_2.jpg
     * web_url : http://m.funwear.com/wx/?p=779&amp;a=
     * logo_img : http://7xjir4.com2.z0.glb.qiniucdn.com/FskqfoYVRMcIvCvoIZv8ec0hJkeS
     * product_brand_img :
     */

    private String id;
    private String brand_code;
    private String name;
    private String img;
    private String web_url;
    private String logo_img;
    private String product_brand_img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand_code() {
        return brand_code;
    }

    public void setBrand_code(String brand_code) {
        this.brand_code = brand_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getLogo_img() {
        return logo_img;
    }

    public void setLogo_img(String logo_img) {
        this.logo_img = logo_img;
    }

    public String getProduct_brand_img() {
        return product_brand_img;
    }

    public void setProduct_brand_img(String product_brand_img) {
        this.product_brand_img = product_brand_img;
    }
}
