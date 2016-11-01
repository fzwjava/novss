package cn.com.siemens.novel.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import cn.com.siemens.novel.spider.core.Configs;
import cn.com.siemens.novel.spider.core.SpHandlerWait;
import cn.com.siemens.novel.spider.core.SpiderEnum;

public class HtmlTest {
	@Test
	public void testSpider(){
		SpHandlerWait sp = new SpHandlerWait();
		sp.intent.put(Configs.URL, "http://m.biquge.com/fenlei1_1.html");
		sp.handler(SpiderEnum.getHtml);
	
			String s = sp.intent.getString(Configs.HTML);
			System.out.println(s);
			Pattern pattern = Pattern
	                .compile("[“]*([(\u4E00-\u9FA5)|(\\uF900-\\uFA2D)]+[^“,，。?？”…！!]{0,4}[(\u4E00-\u9FA5)|(\\uF900-\\uFA2D)]+)[“,，。?？”…！!]{0,2}[“,，。?？”…！!]{1,2}");
	        Matcher matcher = pattern.matcher(s);
	        StringBuffer sb = new StringBuffer();
	        while (matcher.find()) {
	            sb.append(matcher.group());

	        }
	       // System.out.println(sb.toString());
	}

}
