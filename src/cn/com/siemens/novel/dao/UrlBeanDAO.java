package cn.com.siemens.novel.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

import cn.com.siemens.novel.bean.Position;
import cn.com.siemens.novel.bean.UrlBean;

public class UrlBeanDAO {

    // 这里是否应该声明静态变量，不能一直调用这个方法
    private static DBCollection getUrlColleciton() throws UnknownHostException,
            MongoException {
        return MongoUtil.getMongo().getDB("novelsystem").getCollection("urls");
    }

    public static void insert(List<UrlBean> list) {
        for (UrlBean url : list) {
            insert(url);
        }
    }

    public static void insert(UrlBean url) {
        DBObject dbObject = (DBObject) JSON.parse(new Gson().toJson(url));
        DBObject query = new BasicDBObject();
        query.put("url", url.getUrl());
        try {
            if (getUrlColleciton().findOne(query) == null) {
                getUrlColleciton().insert(dbObject);
            }
        } catch (MongoException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static List<UrlBean> getUrlByPosition(Position position, int num) {
        DBObject query = new BasicDBObject();
        if (position.get_id() != null) {
            query.put("_id",
                    new BasicDBObject("$gt", new ObjectId(position.getPoid())));
        }
        DBObject sort = new BasicDBObject("_id", 1);
        DBCursor cursor = null;
        try {
            if (position.get_id() != null) {
                cursor = getUrlColleciton().find(query).sort(sort).limit(num);
            } else {
                cursor = getUrlColleciton().find().sort(sort).limit(num);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }
        DBObject dbo = null;
        List<UrlBean> list = null;
        UrlBean url = null;
        Gson gson = new Gson();
        if (cursor != null) {
            list = new ArrayList<UrlBean>();
            while (cursor.hasNext()) {
                dbo = cursor.next();
                url = gson.fromJson(dbo.toString(), UrlBean.class);
                url.set_id(new ObjectId(dbo.get("_id").toString()));
                list.add(url);
            }
        }
        return list;
    }
}
