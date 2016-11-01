package cn.com.siemens.novel.spider.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Configs {
	private static Log log = LogFactory.getLog(Configs.class);
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
	public static String SPIDERENUM = "SpiderEnum";
	public static String SPTIME = "SPTIME";//爬取一个网页爬取用时
	public static String REALURL = "realurl";
	public static String HOST = "HOST";
	public final static String TIMEOUT = "TIMEOUT";
	public final static String SOTIMEOUT = "SOTIMEOUT";
	public static int analyThreadNum;
	public static int spiderThreadNum;
	public static int urlMsgCount;
	public static int htmlMsgCount;
	public static int totalThreadCount;
	public static String Cookie = "cookie";
	public static String Cookies = "cookies";
	private static String propertiesName = "spider-config.properties";
	public static String SPERROR = "sperror";
	static {
		load();
		initNum();
	}

	private static void load() {
		File f = new File(new File("").getAbsoluteFile() + "/"
				+ propertiesName);
		if (f.exists()) {
			try {
				prop.load(new InputStreamReader(new FileInputStream(f), "UTF-8"));
			} catch (Exception e) {
				log.error("文件找不到或者。。。", e);
			}
		} else {
			try {
				prop.load(new InputStreamReader(Configs.class.getClassLoader()
						.getResourceAsStream(propertiesName), "UTF-8"));
			} catch (Exception e) {
				log.error("文件找不到或者。。。", e);
			}
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

		} catch (Exception e) {
			log.error(
					"配置文件有错，中断-------------------------------------------------",
					e);
			System.exit(0);
		}
	}

	public static void setId(int ids) throws IOException {

		File file = new File(Configs.class.getClassLoader()
				.getResource(propertiesName).getPath());
		OutputStream fos = new FileOutputStream(file);
		prop.setProperty("id", ids + "");
		prop.store(fos, "Update '" + "po" + "' value");
		fos.close();

	}


}
