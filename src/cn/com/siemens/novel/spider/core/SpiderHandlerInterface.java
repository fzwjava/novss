package cn.com.siemens.novel.spider.core;

import static cn.com.siemens.novel.util.ConfigProperties.RESULT;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.siemens.novel.util.ConfigProperties;

public class SpiderHandlerInterface extends SpiderHandler {
    private final Log log = LogFactory.getLog(SpiderHandler.class);

    @Override
    public <T> T customMethod(SpiderEnum sn) {
        T result = null;
        intent.put(ConfigProperties.interfaceFlag,
                ConfigProperties.interfaceFlag);
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
