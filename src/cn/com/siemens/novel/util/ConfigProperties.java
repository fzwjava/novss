package cn.com.siemens.novel.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConfigProperties {
    private static Log log = LogFactory.getLog(ConfigProperties.class);
    private static Properties prop = new Properties();
    public static String MONGOD_IP;
    public static int MONGOD_PORT;
    public static String METHOD = "method";
    public static String PARAMETER = "prameter";
    public static String URL = "url";
    public static String RESULT = "result";
    public static String HTML = "html";
    public static String interfaceFlag = "interface";
    public static String spiderhandler = "spiderhandler";
    public static int analyThreadNum;
    public static int spiderThreadNum;
    public static int urlMsgCount;
    public static int htmlMsgCount;
    public static int totalThreadCount;
    public static int waitTimeout;

    public static int type;

    public static int insertNum = 5;
    public static String serverUrl;
    private static String propertiesName = "spider-config.properties";
    public static int id;
    public static int taskThreadNum;
    public static int GoodsListThreadNum;
    public static int GoodsResultThreadNum;
    public static int MonitorGoodsThreadNum;
    public static int goodslistBlockNum;
    public static int goodslistResultBlockNum;
    public static int spiderTaskNum;
    public static int spiderMonitorNum;
    public static long MonitorGoodsWaitTime;
    public static long MonitorGoodsComplaintTime;
    public static String spiderUrl;
    public static int getUrlNum;
    public static int dpth;

    static {
        load();
        initNum();
    }

    private static void load() {
        try {
            prop.load(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(propertiesName));
        } catch (Exception e) {
            log.error("文件找不到或者。。。", e);
        }

    }

    private static void initNum() {
        try {
            analyThreadNum = Integer.parseInt(prop.getProperty(
                    "analyThreadNum", "5"));
            spiderThreadNum = Integer.parseInt(prop.getProperty(
                    "spiderThreadNum", "5"));
            urlMsgCount = Integer.parseInt(prop.getProperty("urlMsgCount",
                    "100"));
            htmlMsgCount = Integer.parseInt(prop.getProperty("htmlMsgCount",
                    "100"));
            totalThreadCount = Integer.parseInt(prop.getProperty(
                    "totalThreadCount", "50"));
            waitTimeout = Integer.parseInt(prop.getProperty("waitTimeout",
                    "10000"));

            serverUrl = prop.getProperty("serverUrl");
            type = Integer.parseInt(prop.getProperty("type"));
            id = Integer.parseInt(prop.getProperty("id", "-1"));
            taskThreadNum = Integer.parseInt(prop.getProperty("taskThreadNum",
                    "2"));
            GoodsListThreadNum = Integer.parseInt(prop.getProperty(
                    "GoodsListThreadNum", "2"));
            GoodsResultThreadNum = Integer.parseInt(prop.getProperty(
                    "GoodsResultThreadNum", "2"));
            MonitorGoodsThreadNum = Integer.parseInt(prop.getProperty(
                    "MonitorGoodsThreadNum", "2"));
            goodslistBlockNum = Integer.parseInt(prop.getProperty(
                    "goodslistBlockNum", "100"));
            goodslistResultBlockNum = Integer.parseInt(prop.getProperty(
                    "goodslistResultBlockNum", "100"));
            spiderUrl = prop.getProperty("spiderUrl");
            spiderTaskNum = Integer.parseInt(prop.getProperty("spiderTaskNum"));
            spiderMonitorNum = Integer.parseInt(prop
                    .getProperty("spiderMonitorNum"));
            MonitorGoodsWaitTime = Long.parseLong(prop
                    .getProperty("MonitorGoodsWaitTime"));
            MonitorGoodsComplaintTime = Long.parseLong(prop
                    .getProperty("MonitorGoodsComplaintTime"));
            MONGOD_IP = prop.getProperty("MONGOD_IP");
            MONGOD_PORT = Integer.parseInt(prop.getProperty("MONGOD_PORT"));
            getUrlNum = Integer.parseInt(prop.getProperty("getUrlNum"));
            dpth = Integer.parseInt(prop.getProperty("dpth"));
        } catch (Exception e) {
            log.error(
                    "配置文件有错，中断-------------------------------------------------",
                    e);
            System.exit(0);
        }
    }

    public static void setId(int ids) throws IOException {
        id = ids;

        File file = new File(ConfigProperties.class.getClassLoader()
                .getResource(propertiesName).getPath());
        // System.out.println(file.getAbsolutePath());
        OutputStream fos = new FileOutputStream(file);
        prop.setProperty("id", ids + "");
        prop.store(fos, "Update '" + "po" + "' value");
        fos.close();

    }

}
