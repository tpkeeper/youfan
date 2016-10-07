package com.tk.youfan.domain.search.brandrequence;

import java.util.List;

/**
 * 作者：tpkeeper on 2016/10/7 13:59
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class GroupData {
    /**
     * sortString : C
     * brandInfo : []
     */

    private String sortString;
    private List<BrandInfo> brandInfo;

    public String getSortString() {
        return sortString;
    }

    public void setSortString(String sortString) {
        this.sortString = sortString;
    }

    public List<BrandInfo> getBrandInfo() {
        return brandInfo;
    }

    public void setBrandInfo(List<BrandInfo> brandInfo) {
        this.brandInfo = brandInfo;
    }
}
