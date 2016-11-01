package cn.com.siemens.novel.dao;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;

import cn.com.siemens.novel.bean.Position;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

public class PositionDAO {

    private static DBCollection getPostionColleciton()
            throws UnknownHostException, MongoException {
        return MongoUtil.getMongo().getDB("novelsystem")
                .getCollection("position");
    }

    public static Position getPosition() {
        Position position = null;
        try {
            DBObject dbObject = getPostionColleciton().findOne();
            if (dbObject != null) {
                position = new Gson().fromJson(dbObject.toString(),
                        Position.class);
                position.set_id(new ObjectId(dbObject.get("_id").toString()));
            }
        } catch (MongoException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {

            e.printStackTrace();
        }
        return position;

    }

    public static void UpdatePosition(Position position) {
        DBObject query = new BasicDBObject();
        if (position.get_id() != null) {
            query = new BasicDBObject("_id", position.get_id());

        }

        try {
            getPostionColleciton().update(query,
                    (DBObject) JSON.parse(new Gson().toJson(position)), true,
                    false);
        } catch (MongoException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}
