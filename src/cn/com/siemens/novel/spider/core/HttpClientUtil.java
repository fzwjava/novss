package cn.com.siemens.novel.spider.core;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;

@SuppressWarnings("deprecation")
public class HttpClientUtil {
	public static Log log = LogFactory.getLog(HttpClientUtil.class);

	public static HttpGet getDefaultMethod() {

		HttpGet get = new HttpGet();
		get.addHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		get.addHeader("Connection", "keep-alive");
		get.addHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		get.addHeader("Accept-Charset", "utf-8;q=0.7,*;q=0.7");
		get.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
		get.addHeader("Accept-Encoding", "gzip");
		// get.addHeader("X-Requested-With", "XMLHttpRequest");
		return get;
	}

	public static HttpPost getPostMethod(List<NameValuePair> pairs) {

		HttpPost post = new HttpPost();
		if (pairs != null) {
			try {
				post.setEntity(new UrlEncodedFormEntity(pairs, "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		post.addHeader("Accept", "application/json, text/javascript, */*");
		post.addHeader("Connection", "keep-alive");
		post.addHeader("Cache-Control", "no-cache");
		post.addHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		post.addHeader("Accept-Charset", "utf-8;q=0.7,*;q=0.7");
		post.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:17.0) Gecko/20100101 Firefox/17.0");
		post.addHeader("Accept-Encoding", "gzip");
		post.addHeader("Content-Type",
				"application/x-www-form-urlencoded; charset=UTF-8");
		post.addHeader("X-Requested-With", "XMLHttpRequest");
		post.addHeader("Pragma", "no-cache");

		return post;
	}

	@SuppressWarnings({ "resource" })
	public static String send(URI uri, HttpEntity entity) throws Exception {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = getPostMethod(null);
		if (entity != null) {
			post.setEntity(entity);
		}
		String re = null;
		post.setURI(uri);
		HttpResponse response = client.execute(post);
		re = getHtml(response);
		return re;
	}

	public static String getHtml(HttpResponse response) throws Exception {

		HttpEntity entity = response.getEntity();
		BufferedReader br = null;
		String en = null;
		Header co = response.getFirstHeader("Content-Type");
		Header[] ss = response.getHeaders("Location");
		if (ss != null && ss.length > 0) {
		}
		InputStream in = entity.getContent();
		CharesetUtil chutil = new CharesetUtil();
		if (co != null) {
			en = chutil.getCharset(co.getValue());
		}
		if (en == null) {
			Header[] header = response.getHeaders("Content-Encoding");
			String cod = null;
			if (header.length > 0) {
				if (header[0].getValue().equals("gzip")) {
					cod = "gzip";
				}
			}
			en = chutil.convertInputStream(in, cod);
			in = chutil.getIn();
			in = new SequenceInputStream(new ByteArrayInputStream(
					con(chutil.getBytes())), in);

			if (en == null) {
				en = "utf-8";
			}
		} else {
			Header[] header = response.getHeaders("Content-Encoding");
			if (header.length > 0) {
				if (header[0].getValue().equals("gzip")) {
					in = new GZIPInputStream(in);
				}
			}
		}
		StringBuffer sb = new StringBuffer();
		try {
			br = new BufferedReader(new InputStreamReader(in, en));
			String tem = null;
			while ((tem = br.readLine()) != null) {
				sb.append(tem);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (entity != null) {
				entity.consumeContent();
			}
		}
		return sb.toString();
	}

	public static byte[] con(List<Byte> list) {
		byte[] bts = new byte[list.size()];
		for (int i = 0; i < list.size(); i++) {
			bts[i] = list.get(i);
		}
		return bts;
	}

	public static String get(String uri, HttpHost host) {
		String html = null;
		SpiderHandler spiderHandler = new SpHandlerWait();
		spiderHandler.intent.put(Configs.URL, uri);
		if (host != null)
			spiderHandler.intent.put(Configs.HOST, host);
		HttpGet get = getDefaultMethod();
		spiderHandler.intent.put(Configs.METHOD, get);
		html = spiderHandler.handler(SpiderEnum.getHtml);
		return html;
	}

	public static BasicCookieStore getCookie(String cs) {
		BasicCookieStore cookis = new BasicCookieStore();
		String[] cks = cs.split(";");
		String[] kv = null;
		for (String ck : cks) {
			kv = ck.split("=");
			cookis.addCookie(new BasicClientCookie(kv[0], kv.length > 1 ? kv[1]
					: ""));
		}
		return cookis;
	}

	public static String get(URI uri, HttpHost host) {

		return null;
	}
}
