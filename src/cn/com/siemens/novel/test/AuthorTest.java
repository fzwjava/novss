package cn.com.siemens.novel.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import cn.com.siemens.novel.spider.core.HttpClientUtil;
import cn.com.siemens.novel.spider.core.IntentMap;
import cn.com.siemens.novel.util.ConfigProperties;

public class AuthorTest {
    @Test
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
        Pattern pa = Pattern.compile("作[\\s(&nbsp;)]*者[\\s(&nbsp;)]*[:：][\\s(&nbsp;)]*([^<>]+)");
        // System.out.println(intent.getString(ConfigProperties.HTML));
        Matcher matcher3 = pa.matcher(intent.getString(ConfigProperties.HTML));
        if (matcher3.find()) {
            System.out.println(matcher3.group(1));
        }
    }

}
