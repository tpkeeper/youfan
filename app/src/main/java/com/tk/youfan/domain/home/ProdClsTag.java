package com.tk.youfan.domain.home;

/**
 * 作者：tpkeeper on 2016/9/29 07:13
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：商品图片上覆盖的标签
 */
public class ProdClsTag {
    /**
     * tagName : 折扣
     * tagUrl : http://metersbonwe.qiniucdn.com/icon_sale2.png
     * tagType : 0
     * remark :
     */

    private String tagName;
    private String tagUrl;
    private int tagType;
    private String remark;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagUrl() {
        return tagUrl;
    }

    public void setTagUrl(String tagUrl) {
        this.tagUrl = tagUrl;
    }

    public int getTagType() {
        return tagType;
    }

    public void setTagType(int tagType) {
        this.tagType = tagType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
