package cn.com.siemens.novel.test;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import cn.com.siemens.novel.bean.UrlBean;
import cn.com.siemens.novel.spider.analysis.CommonAnalysis;
import cn.com.siemens.novel.spider.core.HttpClientUtil;
import cn.com.siemens.novel.spider.core.IntentMap;
import cn.com.siemens.novel.util.ConfigProperties;

public class CommonAnalysisTest {
    @Test
    public void test() {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = HttpClientUtil.getDefaultMethod();
        try {
            request.setURI(new URI("http://m.biquge.com/fenlei1_1.html"));
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
        UrlBean bean = new UrlBean();
        bean.setUrl("sdf");
        intent.put("father", bean);
        new CommonAnalysis().handler(intent);

    }

}
