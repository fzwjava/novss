package cn.com.siemens.novel.bixuge;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.siemens.novel.bean.Novel;
import cn.com.siemens.novel.spider.analysis.tool.FiledConfig;
import cn.com.siemens.novel.spider.analysis.tool.GetBeanByHtml;
import cn.com.siemens.novel.spider.core.AnalysisHandler;
import cn.com.siemens.novel.spider.core.Configs;
import cn.com.siemens.novel.spider.core.IntentMap;
import cn.com.siemens.novel.spider.core.SpiderEnum;
import cn.com.siemens.novel.spider.core.UrlHandler;
import cn.com.siemens.novel.spider.util.UrlBean;

public class InfoEnum {
	
	public static void main(String[] args) {
		System.out.println(getkey("http://m.biquge.com/22_22660/"));
		 /* SpiderHandler spiderHandler = new SpiderHandlerInterface();
		  spiderHandler.intent.put(Configs.URL, "http://m.biquge.com/22_22660/");
	        spiderHandler.handler(getEnum( ));
	        System.out.println(spiderHandler.intent
	                .getString(ConfigProperties.RESULT));*/
	}
	public static SpiderEnum getEnum() {
		return new SpiderEnum(new UrlHandler() {

			public void handler(IntentMap prameter) {
			}
		}, new AnalysisHandler() {
			public void handler(IntentMap intent) {
				String html = intent.getString(Configs.HTML);
				String url = intent.get(Configs.URL);
			
				IntentMap con = new IntentMap();
				con.put("listpath",
						new FiledConfig()
								.setPath("//div[@class='cover']"));
				GetBeanByHtml<UrlBean> getBean = new GetBeanByHtml<UrlBean>(
						UrlBean.class, con, html);
				Novel nv = new Novel();
				nv.setImageUrl( getBean.getValueByPath(new FiledConfig()
								.setPath("//div[@class='block']//img$src")));
				nv.setAuthor(getBean.getValueByPath(new FiledConfig()
								.setPath("//div[@class='block_txt2']//p[2]$text")));
				nv.setName(getBean.getValueByPath(new FiledConfig()
						.setPath("//div[@class='block_txt2']//h2$text")));
				nv.setStatus(getBean.getValueByPath(new FiledConfig()
						.setPath("//div[@class='block_txt2']//p[4]$text")));
				nv.setNvUpdateTime(getBean.getValueByPath(new FiledConfig()
						.setPath("//div[@class='block_txt2']//p[5]$text")));
				nv.setInfo(getBean.getValueByPath(new FiledConfig()
						.setPath("//div[@class='intro_info']$text")));
				nv.setUrl(url);
				intent.put(Configs.RESULT, nv);
			}
		});
	}
	
	public static String getkey(String url){
		Pattern pa = Pattern.compile("\\d+_(\\d+)");
		Matcher ma = pa.matcher(url);
		if(ma.find()){
			return ma.group(1);
		}
		return null;
	}
}
