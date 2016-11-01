package cn.com.siemens.novel.bixuge;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import cn.com.siemens.novel.bean.Catalogue;
import cn.com.siemens.novel.spider.analysis.tool.FiledConfig;
import cn.com.siemens.novel.spider.analysis.tool.GetBeanByHtml;
import cn.com.siemens.novel.spider.core.AnalysisHandler;
import cn.com.siemens.novel.spider.core.Configs;
import cn.com.siemens.novel.spider.core.IntentMap;
import cn.com.siemens.novel.spider.core.SpiderEnum;
import cn.com.siemens.novel.spider.core.SpiderHandler;
import cn.com.siemens.novel.spider.core.SpiderHandlerInterface;
import cn.com.siemens.novel.spider.core.UrlHandler;
import cn.com.siemens.novel.util.ConfigProperties;

public class CatalogueEnum {
	public static void main(String[] args) {
		  SpiderHandler spiderHandler = new SpiderHandlerInterface();
		  spiderHandler.intent.put(Configs.URL, "http://m.biquge.com/booklist/22660.html");
	        spiderHandler.handler(getEnum("22660" ));
	        System.out.println(spiderHandler.intent
	                .getString(ConfigProperties.RESULT));
	}
	
	public static SpiderEnum getEnum(final String key ) {
		return new SpiderEnum(new UrlHandler() {

			public void handler(IntentMap prameter) {
				prameter.put(Configs.URL, "http://m.biquge.com/booklist/"+key+".html");
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
								.setPath("//ul[@class='chapter']/li"));
				con.put("name", new FiledConfig().setPath("/a$text"));
				con.put("url", new FiledConfig().setPath("/a$href").setFiledMath( (a)->{return "http://"+s+a;}));
				GetBeanByHtml<Catalogue> getBean = new GetBeanByHtml<Catalogue>(
						Catalogue.class, con, html);
				List<Catalogue> list = getBean.getListBean();
				intent.put(Configs.RESULT, list);
			}
		});
	}

}
