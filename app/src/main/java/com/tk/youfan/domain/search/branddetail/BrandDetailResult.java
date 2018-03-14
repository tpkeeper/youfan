package com.tk.youfan.domain.search.branddetail;

import java.util.List;

/**
 * 作者：tpkeeper on 2016/10/5 21:41
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：品牌详情result
 */
public class BrandDetailResult {

    private ClsInfo clsInfo;
    /**
     * clsInfo : {}
     * prodClsTag : []
     */

    private List<ProdClsTag> prodClsTag;

    public ClsInfo getClsInfo() {
        return clsInfo;
    }

    public void setClsInfo(ClsInfo clsInfo) {
        this.clsInfo = clsInfo;
    }

    public List<ProdClsTag> getProdClsTag() {
        return prodClsTag;
    }

    public void setProdClsTag(List<ProdClsTag> prodClsTag) {
        this.prodClsTag = prodClsTag;
    }

    public static class ClsInfoBean {
    }
}
