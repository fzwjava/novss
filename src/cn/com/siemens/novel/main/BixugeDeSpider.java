package cn.com.siemens.novel.main;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import cn.com.siemens.novel.bean.Catalogue;
import cn.com.siemens.novel.bean.ChapterDetails;
import cn.com.siemens.novel.bean.Novel;
import cn.com.siemens.novel.spider.analysis.tool.TwoTuple;
import cn.com.siemens.novel.spider.core.SpHandlerResult;
import cn.com.siemens.novel.spider.core.SpiderHandler;
import cn.com.siemens.novel.spider.enums.DetailEnum;
import cn.com.siemens.novel.util.MD5;

public class BixugeDeSpider {

	private static final Logger logger = Logger.getLogger(BixugeDeSpider.class);
	public static TwoTuple<String, String> common = null;
	public static String temp = null;
	public static int num = 0;
	public static void main(String[] args) {
		MongoTemplate mongoTemplate = (MongoTemplate) SpringUtils.springFactory.getBean("mongoTemplate");
		Query qu = new Query();
		int limit = 10;
		qu.limit(limit);
		int skip = 0;
		qu.addCriteria(Criteria.where("isSpider").is(false));
		qu.with(new Sort(new Sort.Order(Sort.Direction.ASC, "createTime")));
		List<Novel> list = mongoTemplate.find(qu, Novel.class);
		while (list != null && list.size() > 0) {

			for (Novel nv : list) {
				List<Catalogue> cas = nv.getCatalogues();
				Collections.sort(cas, (a, b) -> {
					if (a.getName() != null && b.getName() != null)
						return a.getName().compareTo(b.getName());
					return 1;
				});
				for (Catalogue c : nv.getCatalogues()) {
					String id = MD5.GetMD5Code(nv.getName() + nv.getAuthor() + c.getName());
					if (mongoTemplate.findById(id, ChapterDetails.class) == null) {
						SpiderHandler deHandler = new SpHandlerResult();
						deHandler.intent.put("nv", nv);
						deHandler.intent.put("catalogue", c.getName());
						deHandler.handler(DetailEnum.getEnum(c.getUrl()));
					}
				}
				nv.setIsSpider(true);
				mongoTemplate.save(nv);
			}
			skip++;
			qu.skip(skip * limit);
			list = mongoTemplate.find(qu, Novel.class);
		}
	}

}
