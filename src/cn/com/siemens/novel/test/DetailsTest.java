package cn.com.siemens.novel.test;

import cn.com.siemens.novel.spider.core.SpiderHandler;
import cn.com.siemens.novel.spider.core.SpiderHandlerInterface;
import cn.com.siemens.novel.spider.enums.DetailEnum;
import cn.com.siemens.novel.util.ConfigProperties;

public class DetailsTest {

    @org.junit.Test
    public void test() {
        SpiderHandler spiderHandler = new SpiderHandlerInterface();
        spiderHandler.intent.put(ConfigProperties.URL,
                "http://www.biquge.com/0_399/1433859.html");
        spiderHandler.handler(DetailEnum.getEnum( "http://www.biquge.com/0_399/1433859.html"));
        System.out.println(spiderHandler.intent
                .getString(ConfigProperties.RESULT));
        System.out.println();
    }

}
