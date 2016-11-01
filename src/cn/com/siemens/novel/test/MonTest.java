package cn.com.siemens.novel.test;

import cn.com.siemens.novel.bean.Novel;
import cn.com.siemens.novel.dao.CatalogueDAO;
import cn.com.siemens.novel.spider.core.SpiderHandler;
import cn.com.siemens.novel.spider.core.SpiderHandlerInterface;
import cn.com.siemens.novel.spider.enums.CatalogueEnum;
import cn.com.siemens.novel.util.ConfigProperties;

public class MonTest {
    public static void main(String[] args) {
        SpiderHandler spiderHandler2 = new SpiderHandlerInterface();
        spiderHandler2.intent.put(ConfigProperties.URL,
                "http://www.1kanshu.com/files/article/html/61/61834/");
        spiderHandler2.intent.put("pa",
                "http://www.1kanshu.com/files/article/html/61/61834/");

        Novel catalogue = spiderHandler2.handler(CatalogueEnum.getEnum(null));
        catalogue.setName("斗破苍穹");
        catalogue.setUrl("http://www.1kanshu.com/");
        catalogue.setAuthor("天下第二");
        System.out.println(catalogue);
        CatalogueDAO.insert(catalogue);
    }

}
