package cn.com.siemens.novel.dao;

import java.net.UnknownHostException;

import cn.com.siemens.novel.util.ConfigProperties;

import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoUtil {
    private static Mongo mongo;

    public static Mongo getMongo() throws UnknownHostException, MongoException {
        if (mongo == null) {
            mongo = new Mongo(ConfigProperties.MONGOD_IP,
                    ConfigProperties.MONGOD_PORT);
        }
        return mongo;
    }

    public static void main(String[] args) throws UnknownHostException,
            MongoException {
        System.out.println(getMongo());
    }
}
