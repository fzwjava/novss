package cn.com.siemens.novel.spider.core;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * 此类为多线程处理方法,当onOff为true时，需要调用者处理结果集，反之此类将自己处理结果集
 * 
 * @author Jack Fang
 * 
 * @param <T>
 */
public class SpHandlerFast<T> extends SpHandlerResult {
	private final Log log = LogFactory.getLog(SpHandlerFast.class);

	// 线程开关
	private boolean onOff;

	public SpHandlerFast() {
	}

	public SpHandlerFast(boolean onOff) {
		this.onOff = onOff;
	}

	public LinkedBlockingQueue<T> results = new LinkedBlockingQueue<T>();

	public void beforeMethod(SpiderEnum sn) {
		sn.resultHandler = new ResultHandler() {

			public Object handler(IntentMap pa) {
				T re = pa.get(Configs.RESULT);
				try {
					if (re != null){
						results.put(re);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return null;
			}
		};
	}

	public <E> E handler(SpiderEnum sn) {
		if (onOff) {
			return super.handler(sn);
		} else {
			handleRe();
			while (true) {
				if (checked()) {
					//log.info(results.size()+"当前结果集");
					return super.handler(sn);
				} else {
					handleAllRe();
				}
			}

		}
	}

	/**
	 * 处理结果集
	 */
	public boolean handleRe() {
		return false;
	};

	public void handleAllRe() {
		while (handleRe()) {
		}
	}

	/**
	 * 执行爬虫前需调用此方法，否则可能死锁或者造成处理队列过大
	 * 
	 * @return
	 */
	public boolean checked() {
		if (results.size() > 100) {
			return false;
		}
		return true;
	}

}
