package cn.com.siemens.novel.spider.core;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SpManager {
	private static Log log = LogFactory.getLog(SpManager.class);

	// spiderUrl队列
	public static BlockingQueue<IntentMap> urlMsg;

	// html队列
	public static BlockingQueue<IntentMap> HtmlQueue;

	// qq队列
	public static BlockingQueue<IntentMap> qqUrl;

	// 公用线程�?
	public final static ExecutorService executor = Executors
			.newCachedThreadPool();

	static {
		log.info("&&&&&SpiderManager init ");
		HtmlQueue = new ArrayBlockingQueue<IntentMap>(Configs.htmlMsgCount);
		urlMsg = new ArrayBlockingQueue<IntentMap>(Configs.urlMsgCount);

		init();
		log.info("&&&&&SpiderManager init end 启动分析线程："+Configs.analyThreadNum+"爬取线程："+Configs.spiderThreadNum);
	}

	static void init() {
		for (int i = 0; i < Configs.analyThreadNum; i++) {
			executor.execute(new AnalysisThread());
		}
		for (int i = 0; i < Configs.spiderThreadNum; i++) {
			executor.execute(new HtmlSpiderThread());
		}
	}
}
