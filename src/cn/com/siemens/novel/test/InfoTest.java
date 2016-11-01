package cn.com.siemens.novel.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import cn.com.siemens.novel.spider.core.HttpClientUtil;
import cn.com.siemens.novel.spider.core.IntentMap;
import cn.com.siemens.novel.util.ConfigProperties;

public class InfoTest {
    @org.junit.Test
    public void test() {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = HttpClientUtil.getDefaultMethod();
        try {
            request.setURI(new URI("http://www.piaotian.net/bookinfo/1/1343.html"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String html = null;
        try {
            HttpResponse response = client.execute(request);
            html = HttpClientUtil.getHtml(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        IntentMap intent = new IntentMap();
        intent.put(ConfigProperties.HTML, html);
       // Pattern pa = Pattern.compile("介[\\s(&nbsp;)]*绍[\\s(&nbsp;)]*[:：][\\s(&nbsp;)]*([^<>]+)");
        Pattern pa1 = Pattern.compile("内容简介[\\s(&nbsp;)]*[:：][\\s(&nbsp;)]*<br><br>[\\s(&nbsp;)]*");
        Pattern pa = Pattern
                .compile("[“]*([(\u4E00-\u9FA5)|(\\uF900-\\uFA2D)]+[^“,，。?？”…！!]{0,4}[(\u4E00-\u9FA5)|(\\uF900-\\uFA2D)]+)[“,，。?？”…！!]{0,2}[“,，。?？”…！!]{1,2}");
        Matcher matcher3 = pa.matcher(intent.getString(ConfigProperties.HTML));
       StringBuffer sb = new StringBuffer();
        while (matcher3.find()) {
        	sb.append(matcher3.group());
        }
        System.out.println(sb.toString());

    }

}
