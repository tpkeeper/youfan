package com.tk.youfan.domain.home;

import java.util.List;

/**
 * 作者：tpkeeper on 2016/9/29 07:15
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：猜你喜欢商品数据
 */
public class LikeModuleProduct {

    /**
     * product_sys_code : 837275
     * market_price : 839.0
     * product_url : http://img5.ibanggo.com/sources/images/goods/TP/837275/837275_00.jpg
     * brand_id : 79
     * product_name : PUMA中性Blaze x 鹿晗-刘雯代言慢跑鞋
     * status : 2
     * on_sale_date : 2016-07-08 11:15:03
     * spec_price_list : ["529.000000"]
     * brandUrl : http://img7.ibanggo.com/sources/cms/brand_logo/puma.png
     * brandName : puma
     * activity_rule :
     * activity_icon :
     * price : 529.0
     * prodClsTag : [{"tagName":"折扣","tagUrl":"http://metersbonwe.qiniucdn.com/icon_sale2.png","tagType":0,"remark":""}]
     * stock_num : 0
     */

    private String product_sys_code;
    private double market_price;
    private String product_url;
    private String brand_id;
    private String product_name;
    private int status;
    private String on_sale_date;
    private String brandUrl;
    private String brandName;
    private String activity_rule;
    private String activity_icon;
    private double price;
    private int stock_num;
    private List<String> spec_price_list;
    private List<ProdClsTag> prodClsTag;

    public List<ProdClsTag> getProdClsTag() {
        return prodClsTag;
    }

    public void setProdClsTag(List<ProdClsTag> prodClsTag) {
        this.prodClsTag = prodClsTag;
    }

    public String getProduct_sys_code() {
        return product_sys_code;
    }

    public void setProduct_sys_code(String product_sys_code) {
        this.product_sys_code = product_sys_code;
    }

    public double getMarket_price() {
        return market_price;
    }

    public void setMarket_price(double market_price) {
        this.market_price = market_price;
    }

    public String getProduct_url() {
        return product_url;
    }

    public void setProduct_url(String product_url) {
        this.product_url = product_url;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOn_sale_date() {
        return on_sale_date;
    }

    public void setOn_sale_date(String on_sale_date) {
        this.on_sale_date = on_sale_date;
    }

    public String getBrandUrl() {
        return brandUrl;
    }

    public void setBrandUrl(String brandUrl) {
        this.brandUrl = brandUrl;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getActivity_rule() {
        return activity_rule;
    }

    public void setActivity_rule(String activity_rule) {
        this.activity_rule = activity_rule;
    }

    public String getActivity_icon() {
        return activity_icon;
    }

    public void setActivity_icon(String activity_icon) {
        this.activity_icon = activity_icon;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock_num() {
        return stock_num;
    }

    public void setStock_num(int stock_num) {
        this.stock_num = stock_num;
    }

    public List<String> getSpec_price_list() {
        return spec_price_list;
    }

    public void setSpec_price_list(List<String> spec_price_list) {
        this.spec_price_list = spec_price_list;
    }
}
