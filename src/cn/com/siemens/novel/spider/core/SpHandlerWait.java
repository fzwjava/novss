package cn.com.siemens.novel.spider.core;


import static cn.com.siemens.novel.spider.core.Configs.RESULT;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SpHandlerWait extends SpiderHandler {
    private final Log log = LogFactory.getLog(SpiderHandler.class);

    @Override
    public <T> T customMethod(SpiderEnum sn) {
        T result = null;
        intent.put(Configs.interfaceFlag,
                Configs.interfaceFlag);
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.equals(e);
            }
        }
        result = intent.get(RESULT);
        return result;
    }

}
