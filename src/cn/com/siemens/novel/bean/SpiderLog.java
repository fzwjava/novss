package cn.com.siemens.novel.bean;

import org.bson.types.ObjectId;

public class SpiderLog {
    private ObjectId _id;
    private long createTime;
    private String msg;// 爬虫日志消息
    private int type;// 0普通
    private int spiderOid;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSpiderOid() {
        return spiderOid;
    }

    public void setSpiderOid(int spiderOid) {
        this.spiderOid = spiderOid;
    }

}
