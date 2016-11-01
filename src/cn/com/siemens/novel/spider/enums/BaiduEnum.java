package cn.com.siemens.novel.spider.enums;

import java.util.List;

import cn.com.siemens.novel.spider.analysis.tool.FiledConfig;
import cn.com.siemens.novel.spider.analysis.tool.GetBeanByHtml;
import cn.com.siemens.novel.spider.core.AnalysisHandler;
import cn.com.siemens.novel.spider.core.Configs;
import cn.com.siemens.novel.spider.core.IntentMap;
import cn.com.siemens.novel.spider.core.SpiderEnum;
import cn.com.siemens.novel.spider.core.UrlHandler;
import cn.com.siemens.novel.spider.util.UrlBean;

public class BaiduEnum {
	public static SpiderEnum baiduEnum(final String key) {
		return new SpiderEnum(new UrlHandler() {

			public void handler(IntentMap prameter) {
				String url = "http://www.baidu.com/s?wd="
						+ key
						+ "&tn=baidu&ie=utf-8&f=8&bs="
						+ key
						+ "&rsv_bp=1&rsv_spt=3&rsv_sug3=2&rsv_sug4=58&rsv_sug1=2&rsv_sug2=0&inputT=295&rsv_sug=2";
				prameter.put(Configs.URL, url);
			}
		}, new AnalysisHandler() {
			public void handler(IntentMap intent) {
				String html = intent.getString(Configs.HTML);
				IntentMap con = new IntentMap();
				con.put("listpath",
						new FiledConfig()
								.setPath("//div[@id='content_left']//div[@class='result c-container ']/h3[@class='t']"));
				con.put("key", new FiledConfig().setPath("//a$text"));
				con.put("url", new FiledConfig().setPath("//a$href"));
				GetBeanByHtml<UrlBean> getBean = new GetBeanByHtml<UrlBean>(
						UrlBean.class, con, html);
				List<UrlBean> list = getBean.getListBean();
				intent.put(Configs.RESULT, list);
			}
		});
	}
}
