package cn.com.siemens.novel.bixuge;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import cn.com.siemens.novel.spider.analysis.tool.FiledConfig;
import cn.com.siemens.novel.spider.analysis.tool.GetBeanByHtml;
import cn.com.siemens.novel.spider.core.AnalysisHandler;
import cn.com.siemens.novel.spider.core.Configs;
import cn.com.siemens.novel.spider.core.IntentMap;
import cn.com.siemens.novel.spider.core.SpiderEnum;
import cn.com.siemens.novel.spider.core.SpiderHandler;
import cn.com.siemens.novel.spider.core.SpiderHandlerInterface;
import cn.com.siemens.novel.spider.core.UrlHandler;
import cn.com.siemens.novel.spider.util.UrlBean;
import cn.com.siemens.novel.util.ConfigProperties;

public class ListEnum {
	
	public static void main(String[] args) {
		  SpiderHandler spiderHandler = new SpiderHandlerInterface();
	        spiderHandler.handler(getEnum( 1,1));
	        System.out.println(spiderHandler.intent
	                .getString(ConfigProperties.RESULT));
	}
	
	public static SpiderEnum getEnum(final int ca,final int page ) {
		return new SpiderEnum(new UrlHandler() {

			public void handler(IntentMap prameter) {
				String url = "http://m.biquge.com/fenlei"+ca+"_"+page+".html";
				prameter.put(Configs.URL, url);
			}
		}, new AnalysisHandler() {
			public void handler(IntentMap intent) {
				String html = intent.getString(Configs.HTML);
				String url = intent.get(Configs.URL);
				URI u = null;
				try {
					 u = new URI(url);
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				final String s = u.getHost();
				IntentMap con = new IntentMap();
				con.put("listpath",
						new FiledConfig()
								.setPath("//div[@class='bookbox']//i[@class='iTit']"));
				con.put("key", new FiledConfig().setPath("//a$text"));
				con.put("url", new FiledConfig().setPath("//a$href").setFiledMath( (a)->{return "http://"+s+a;}));
				GetBeanByHtml<UrlBean> getBean = new GetBeanByHtml<UrlBean>(
						UrlBean.class, con, html);
				List<UrlBean> list = getBean.getListBean();
				intent.put(Configs.RESULT, list);
			}
		});
	}

}
