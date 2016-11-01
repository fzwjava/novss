package cn.com.siemens.novel.test;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import cn.com.siemens.novel.bean.Novel;
import cn.com.siemens.novel.main.SpringUtils;

public class Temp {
	public static void main(String[] args) {
		MongoTemplate mongoTemplate = (MongoTemplate) SpringUtils.springFactory.getBean("mongoTemplate");
		Query qu = new Query();
		int limit = 500;
		qu.limit(limit);
		int skip = 0;
		//qu.addCriteria(Criteria.where("isSpider").is(false));
		qu.with(new Sort(new Sort.Order(Sort.Direction.ASC, "createTime")));
		List<Novel> list = mongoTemplate.find(qu, Novel.class);
		while (list != null && list.size() > 0) {

			for (Novel nv : list) {
				nv.setIsSpider(false);
				mongoTemplate.save(nv);
			}
			skip++;
			qu.skip(skip * limit);
			System.out.println(skip * limit);
			list = mongoTemplate.find(qu, Novel.class);
		}
	}

}
