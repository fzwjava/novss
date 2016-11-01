package cn.com.siemens.novel.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.zip.GZIPInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpUtil {

    static Log log = LogFactory.getLog(HttpUtil.class);

    public static String send(URI uri, HttpEntity entity)
            throws ClientProtocolException, IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = getDefaultMethod();
        if (entity != null) {
            post.setEntity(entity);
        }

        String re = null;

        post.setURI(uri);
        HttpResponse response = client.execute(post);
        re = getHtml(response, "utf-8");

        return re;
    }

    public static HttpPost getDefaultMethod() {

        HttpPost get = new HttpPost();

        get.addHeader("Accept", "application/json, text/javascript; q=0.01");
        get.addHeader("Connection", "keep-alive");
        get.addHeader("Cache-Control", "max-age=0");
        get.addHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
        get.addHeader("Accept-Charset", "utf-8;q=0.7,*;q=0.7");
        get.addHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:17.0) Gecko/20100101 Firefox/17.0");
        get.addHeader("Accept-Encoding", "gzip");
        get.addHeader("X-Requested-With", "XMLHttpRequest");

        return get;
    }

    @SuppressWarnings("deprecation")
    public static String getHtml(HttpResponse response, String encode) {
        HttpEntity entity = response.getEntity();
        BufferedReader br = null;
        Header[] header = response.getHeaders("Content-Encoding");
        StringBuffer sb = new StringBuffer();
        try {
            if (header.length > 0) {
                if (header[0].getValue().equals("gzip")) {
                    br = new BufferedReader(new InputStreamReader(
                            new GZIPInputStream(entity.getContent()), encode));
                }

            } else {
                br = new BufferedReader(new InputStreamReader(
                        entity.getContent(), encode));
            }
            String tem = null;

            while ((tem = br.readLine()) != null) {
                sb.append(tem);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (entity != null) {
                try {
                    entity.consumeContent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }


}
