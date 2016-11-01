package cn.com.siemens.novel.test;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import cn.com.siemens.novel.bean.KeyValue;
import cn.com.siemens.novel.bean.Novel;
import cn.com.siemens.novel.spider.core.SpiderHandler;
import cn.com.siemens.novel.spider.core.SpiderHandlerInterface;
import cn.com.siemens.novel.spider.enums.CatalogueEnum;
import cn.com.siemens.novel.spider.enums.DetailEnum;
import cn.com.siemens.novel.util.ConfigProperties;
import cn.com.siemens.novel.util.FileUtil;

public class DownloadTest {
    public static void main(String[] args) {
        final BlockingQueue<KeyValue<String, String, String>> ss = new LinkedBlockingQueue<KeyValue<String, String, String>>();
        SpiderHandler spiderHandler2 = new SpiderHandlerInterface();
        spiderHandler2.intent.put(ConfigProperties.URL,
                "http://www.1kanshu.com/files/article/html/61/61834/");
        spiderHandler2.intent.put("pa",
                "http://www.1kanshu.com/files/article/html/61/61834/");

        Novel catalogue = spiderHandler2.handler(CatalogueEnum.getEnum(null));
        for (int i = 0; i < catalogue.getDetails().size(); i++) {
            try {
                ss.put(catalogue.getDetails().get(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        new Thread() {
            @Override
            public void run() {
                StringBuffer sb = new StringBuffer();
                while (ss.size() > 0) {
                    KeyValue<String, String, String> keyValue = null;
                    String url = null;
                    try {
                        keyValue = ss.take();
                        url = keyValue.value;
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                    SpiderHandler spiderHandler2 = new SpiderHandlerInterface();
                    spiderHandler2.intent.put(ConfigProperties.URL, url);

                    String s = spiderHandler2.handler(DetailEnum.getEnum(null));
                    sb.append(keyValue.key + s);
                }
                try {
                    FileUtil.saveFile(sb.toString(), "e://novel//dou.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
