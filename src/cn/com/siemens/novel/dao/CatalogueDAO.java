package cn.com.siemens.novel.dao;

import java.net.UnknownHostException;

import cn.com.siemens.novel.bean.Novel;

import com.google.gson.Gson;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

public class CatalogueDAO {

    private static DBCollection getCatalogueColleciton()
            throws UnknownHostException, MongoException {
        return MongoUtil.getMongo().getDB("novelsystem").getCollection("novel");
    }

    public static void insert(Novel catalogue) {
        DBObject dbObject = (DBObject) JSON.parse(new Gson().toJson(catalogue));

        try {
            getCatalogueColleciton().insert(dbObject);
            // WriteResult writeResult =
            // getCatalogueColleciton().insert(dbObject);
            // System.out.println(writeResult);
        } catch (MongoException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
