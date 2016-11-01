package cn.com.siemens.novel.spider.core;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class AnalysisThread implements Runnable {

    private final Log log = LogFactory.getLog(AnalysisThread.class);

    public void run() {

        while (!Thread.interrupted()) {
            IntentMap intent = null;
            try {
                intent = SpManager.HtmlQueue.take();
               // log.info("++++++AnalysisThread work take html,intent :"+ intent.hashCode()+"---queue size"+SpiderManager.HtmlQueue.size());
                SpiderEnum se = intent.get(Configs.SPIDERENUM);
                se.anHandler.handler(intent);
                // 太多的判断会不会影响到整体的效率，这里的执行频率很高

                SpiderHandler spiderhandler = intent
                        .get(Configs.spiderhandler);
                if (se.resultHandler != null) {
                    se.resultHandler.handler(intent);
                }
                if (spiderhandler != null) {
                    synchronized (spiderhandler) {
                        spiderhandler.notifyAll();
                    }
                }
                
              //  log.info("++++++AnalysisThread handle an html,intent :" + intent.hashCode()+"---queue size"+SpiderManager.HtmlQueue.size());
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e);

                if (intent != null) {
                    // 判断是否为接口调用者
                    if (intent.getString(Configs.interfaceFlag) != null) {
                        SpiderHandler spiderhandler = intent
                                .get(Configs.spiderhandler);
                        synchronized (spiderhandler) {
                            spiderhandler.notifyAll();
                        }
                    }
                    intent.put("error", "analysis");
                    log.error("++++++AnalysisThread handle error,notifyall,intent :"
                            + intent.hashCode());
                }
            }
            Thread.yield();
        }
    }

}
