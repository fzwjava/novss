package cn.com.siemens.novel.spider.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;

import cn.com.siemens.novel.bean.ChapterDetails;
import cn.com.siemens.novel.bean.Novel;
import cn.com.siemens.novel.main.SpringUtils;
import cn.com.siemens.novel.spider.core.AnalysisHandler;
import cn.com.siemens.novel.spider.core.Configs;
import cn.com.siemens.novel.spider.core.IntentMap;
import cn.com.siemens.novel.spider.core.ResultHandler;
import cn.com.siemens.novel.spider.core.SpiderEnum;
import cn.com.siemens.novel.spider.core.UrlHandler;
import cn.com.siemens.novel.util.ConfigProperties;
import cn.com.siemens.novel.util.MD5;

public class DetailEnum {
	
	private static final Logger logger = Logger.getLogger(DetailEnum.class);
	public static SpiderEnum getEnum(final String key) {
		return new SpiderEnum(new UrlHandler() {

			public void handler(IntentMap prameter) {
				
				prameter.put(Configs.URL, key);
			}
		}, new AnalysisHandler() {
			public void handler(IntentMap intent) {
				Pattern pattern = Pattern
		                .compile("[“]*([(\u4E00-\u9FA5)|(\\uF900-\\uFA2D)]+[^“,，。?？”…！!]{0,4}[(\u4E00-\u9FA5)|(\\uF900-\\uFA2D)]+)[“,，。?？”…！!]{0,2}[“,，。?？”…！!]{1,2}");
		        Matcher matcher = pattern.matcher(intent
		                .getString(ConfigProperties.HTML));
		        // System.out.println(intent.getString(ConfigProperties.HTML));
		        StringBuffer sb = new StringBuffer();
		        while (matcher.find()) {
		            sb.append(matcher.group());

		        }
		        intent.put(ConfigProperties.RESULT, sb.toString());
			}
		},new ResultHandler() {
			
			@Override
			public Object handler(IntentMap pa) {
				String re = pa.getString(Configs.RESULT);
				if (StringUtils.isNotEmpty(re)) {
					/*if(BixugeDeSpider.common==null){
						if(BixugeDeSpider.temp==null){
							BixugeDeSpider.temp = re;
						}else{
							BixugeDeSpider.common = CleanNovle.commonStr(BixugeDeSpider.temp, re);
						}
					}
					if(BixugeDeSpider.common!=null){
						String temp =  CleanNovle.eliminateStEd(re, BixugeDeSpider.common);
						if(re.equals(temp)){
							BixugeDeSpider.num++;
						}else{
							BixugeDeSpider.num--;
						}
						if(BixugeDeSpider.num>5){
							BixugeDeSpider.common = null;
							BixugeDeSpider.num = 0;
						}
					}*/
					MongoTemplate mongoTemplate = (MongoTemplate) SpringUtils.springFactory.getBean("mongoTemplate");
					Novel n = pa.get("nv");
					String ca = pa.getString("catalogue");
					ChapterDetails cd = new ChapterDetails();
					cd.setId(MD5.GetMD5Code(n.getName() + n.getAuthor() + ca));
					cd.setContent(re);
					cd.setChapter(ca);
					cd.setNovelName(n.getName());
					mongoTemplate.save(cd);
					
					logger.info("spider:" + n.getId() + "," + ca);
				}
				return null;
			}
		});
	}
}
