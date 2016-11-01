package cn.com.siemens.novel.bean;

import org.bson.types.ObjectId;

public class UrlBean {
    private ObjectId _id;
    private String url;
    private int fage;// 0未爬取，1已爬取
    private int num;// 更新次数
    private int depth;// url深度
    private int significance;// url重要性等级,0~10,为10则标示为废弃状态
    private long updateTime;// 更新时间
    private String text;
    private String father;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getFather() {
        return father;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public int getFage() {
        return fage;
    }

    public void setFage(int fage) {
        this.fage = fage;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public ObjectId get_id() {
        return _id;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getSignificance() {
        return significance;
    }

    public void setSignificance(int significance) {
        this.significance = significance;
    }

    @Override
    public String toString() {

        return BeanUtil.BeantoString(this);
    }

}
