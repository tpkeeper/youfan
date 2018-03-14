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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Goods goods = (Goods) o;

        if (count != goods.count) return false;
        if (!id.equals(goods.id)) return false;
        if (prodClsNum != null ? !prodClsNum.equals(goods.prodClsNum) : goods.prodClsNum != null)
            return false;
        return lmProdClsId != null ? lmProdClsId.equals(goods.lmProdClsId) : goods.lmProdClsId == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (prodClsNum != null ? prodClsNum.hashCode() : 0);
        result = 31 * result + (lmProdClsId != null ? lmProdClsId.hashCode() : 0);
        result = 31 * result + count;
        return result;
    }

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
