package cn.com.siemens.novel.main;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import cn.com.siemens.novel.bean.Catalogue;
import cn.com.siemens.novel.bean.Flagstamp;
import cn.com.siemens.novel.bean.Novel;
import cn.com.siemens.novel.bixuge.CatalogueEnum;
import cn.com.siemens.novel.bixuge.InfoEnum;
import cn.com.siemens.novel.bixuge.ListEnum;
import cn.com.siemens.novel.spider.core.Configs;
import cn.com.siemens.novel.spider.core.SpiderHandler;
import cn.com.siemens.novel.spider.core.SpiderHandlerInterface;
import cn.com.siemens.novel.spider.util.UrlBean;
import cn.com.siemens.novel.util.ChineseToEnglish;
import cn.com.siemens.novel.util.ConfigProperties;

public class BixugeSpider {
	private static final Logger logger = Logger.getLogger(BixugeSpider.class);
	public static void main(String[] args) {
		MongoTemplate mongoTemplate = (MongoTemplate) SpringUtils.springFactory.getBean("mongoTemplate");
		int catalogueFlag = 6;
		Query qu = new Query();
		qu.addCriteria(Criteria.where("type").is("biquge")).addCriteria(Criteria.where("catalogueFlag").is(catalogueFlag));
		Flagstamp flag = mongoTemplate.findOne(qu, Flagstamp.class);
		if (flag == null) {
			flag = new Flagstamp();
			flag.setCatalogueFlag(catalogueFlag);
			flag.setPageFlag(1);
			flag.setType("biquge");
			mongoTemplate.save(flag);
		}
		Novel nv = null;
		while (true) {
			SpiderHandler spiderHandler = new SpiderHandlerInterface();
			spiderHandler.handler(ListEnum.getEnum(flag.getCatalogueFlag(), flag.getPageFlag()));
			List<UrlBean> list = spiderHandler.intent.get(ConfigProperties.RESULT);
			if (list != null) {
				for (UrlBean u : list) {
					try {
						SpiderHandler infoHandler = new SpiderHandlerInterface();
						infoHandler.intent.put(Configs.URL, u.getUrl());
						infoHandler.handler(InfoEnum.getEnum());
						nv = infoHandler.intent.get(ConfigProperties.RESULT);
						if(nv==null||nv.getName()==null){
							logger.error("spider error:"+u.getUrl()+"--"+nv);
							continue;
						}
						String key = InfoEnum.getkey(nv.getUrl());
						SpiderHandler catalogueHandler = new SpiderHandlerInterface();
						catalogueHandler.handler(CatalogueEnum.getEnum(key));
						List<Catalogue> catalogues = catalogueHandler.intent.get(ConfigProperties.RESULT);
						nv.setCatalogues(catalogues);
						nv.setCreateTime(new Date());
						nv.setUpdateTime(new Date());
						try {
							nv.setId(ChineseToEnglish.getPingYin(nv.getName()));
						} catch (Exception e) {
							logger.error(""+nv,e);
						}
						nv.setPageFlag(flag.getPageFlag() + "");
						nv.setNovleType("网游小说");
						nv.setNovelTypeFlag(flag.getCatalogueFlag()+"");
						nv.setIsSpider(false);
						mongoTemplate.save(nv);
					} catch (Exception e) {
						logger.error(""+nv,e);
					}
					
				}
			}
			mongoTemplate.save(flag);
			flag.setPageFlag(flag.getPageFlag() + 1);
			if (flag.getPageFlag() == 55||list==null||list.size()==0) {
				logger.info("spider break:"+flag.getCatalogueFlag()+"--"+flag.getPageFlag());
				break;
			}
			logger.info("spider:"+flag.getCatalogueFlag()+"--"+flag.getPageFlag());
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
