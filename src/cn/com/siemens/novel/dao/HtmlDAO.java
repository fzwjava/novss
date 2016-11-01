package cn.com.siemens.novel.dao;

import java.net.UnknownHostException;

import cn.com.siemens.novel.bean.Html;

import com.google.gson.Gson;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

public class HtmlDAO {

    private static DBCollection getHtmlColleciton()
            throws UnknownHostException, MongoException {
        return MongoUtil.getMongo().getDB("novelsystem").getCollection("html");
    }

    public void insert(Html html) {
        try {
            getHtmlColleciton().insert(
                    (DBObject) JSON.parse(new Gson().toJson(html)));
        } catch (MongoException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
