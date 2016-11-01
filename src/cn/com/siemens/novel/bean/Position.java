package cn.com.siemens.novel.bean;

import org.bson.types.ObjectId;

public class Position {
    private ObjectId _id;
    private String poid;// 位置的oid

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public ObjectId get_id() {
        return _id;
    }

    public String getPoid() {
        return poid;
    }

    public void setPoid(String poId) {
        this.poid = poId;

    }

    public Position(String poid) {
        this.poid = poid;

    }

    public Position() {
    }

    @Override
    public String toString() {

        return BeanUtil.BeantoString(this);
    }

}
