package com.tk.youfan.domain.purchase;

/**
 * 作者：tpkeeper on 2016/10/10 21:40
 * 微信：lzy1056883354
 * QQ号：1056883354
 * 作用：xxxx
 */
public class Goods {
    private String id;
    private String prodClsNum;
    private String lmProdClsId;
    private int count;

    public Goods() {
    }

    @Override
    public String toString() {
        return "Goods{" +
                "count=" + count +
                ", id='" + id + '\'' +
                ", prodClsNum='" + prodClsNum + '\'' +
                ", lmProdClsId='" + lmProdClsId + '\'' +
                '}';
    }

    public Goods(String id, String lmProdClsId, String prodClsNum, int count) {
        this.id = id;
        this.lmProdClsId = lmProdClsId;
        this.prodClsNum = prodClsNum;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLmProdClsId() {
        return lmProdClsId;
    }

    public void setLmProdClsId(String lmProdClsId) {
        this.lmProdClsId = lmProdClsId;
    }

    public String getProdClsNum() {
        return prodClsNum;
    }

    public void setProdClsNum(String prodClsNum) {
        this.prodClsNum = prodClsNum;
    }
}
