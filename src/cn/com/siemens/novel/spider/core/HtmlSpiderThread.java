package cn.com.siemens.novel.spider.core;

import static cn.com.siemens.novel.spider.core.Configs.HTML;
import static cn.com.siemens.novel.spider.core.Configs.METHOD;
import static cn.com.siemens.novel.spider.core.SpManager.HtmlQueue;
import static cn.com.siemens.novel.spider.core.SpManager.urlMsg;

import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

@SuppressWarnings("deprecation")
public class HtmlSpiderThread implements Runnable {

	private static CookieStore cookieStore = new BasicCookieStore();
	private static Map<String, BasicClientCookie> cookieMap = new HashMap<String, BasicClientCookie>();

	private final Log log = LogFactory.getLog(HtmlSpiderThread.class);
	DefaultHttpClient client;
	IntentMap intent;
	String url;
	HttpUriRequest request;
	HttpResponse response;
	HttpHost host = null;
	Integer timeout;
	Integer sotimeout;
	HttpContext httpContext = new BasicHttpContext();
	String realUrl;

	@SuppressWarnings({ "unused" })
	public HtmlSpiderThread() {
		client = new DefaultHttpClient();
		HttpParams params = client.getParams();
		// params.setParameter(ConnRoutePNames.LOCAL_ADDRESS, "");
		// params.setParameter(ClientPNames.HANDLE_REDIRECTS, false);
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		ConnManagerParams.setMaxTotalConnections(params, 200);
		ConnPerRouteBean pr = new ConnPerRouteBean(20);
		ConnManagerParams.setMaxConnectionsPerRoute(params, pr);
		ClientConnectionManager cm = new ThreadSafeClientConnManager(params,
				schemeRegistry);
		HttpClientParams.setCookiePolicy(client.getParams(),
				CookiePolicy.BROWSER_COMPATIBILITY);

	}

	// 需要设置重试次数
	public void run() {
		URL ul = null;
		URI uri = null;
		while (!Thread.interrupted()) {
			try {
				intent = urlMsg.take();
				url = intent.get(Configs.URL);
				request = intent.get(METHOD);
				if (request == null) {
					request = HttpClientUtil.getDefaultMethod();
				}
				if (request.getURI() == null) {
					url = URLDecoder.decode(url, "UTF-8");
					ul = new URL(url);
					uri = new URI(ul.getProtocol(), ul.getUserInfo(),
							ul.getHost(), ul.getPort(), ul.getPath(),
							ul.getQuery(), null);
					if (HttpPost.class.isInstance(request)) {
						((HttpPost) request).setURI(uri);
					} else if (HttpGet.class.isInstance(request)) {
						((HttpGet) request).setURI(uri);
					}
				}
				timeout = intent.get(Configs.TIMEOUT);
				if (timeout != null) {
					client.getParams().setParameter(
							CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
				}
				sotimeout = intent.get(Configs.SOTIMEOUT);
				if (sotimeout != null) {
					client.getParams().setParameter(
							CoreConnectionPNames.SO_TIMEOUT, sotimeout);
				}
				host = intent.get(Configs.HOST);
				if (host != null) {
					client.getParams().setParameter(
							ConnRoutePNames.DEFAULT_PROXY, host);
				}

				CookieStore store = intent.get(Configs.Cookie);
				if (store != null) {
					for (Cookie ck : store.getCookies()) {
						addCookie((BasicClientCookie) ck);
					}
				}
				client.setCookieStore(cookieStore);
				long timestamp = System.currentTimeMillis();
				response = client.execute(request, httpContext);
				timestamp = System.currentTimeMillis() - timestamp;
				intent.put(Configs.SPTIME, timestamp);
				HttpHost targetHost = (HttpHost) httpContext
						.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
				// 获取实际的请求对象的URI,即重定向之后的"/blog/admin/login.jsp"
				HttpUriRequest realRequest = null;
				Object ob = httpContext
						.getAttribute(ExecutionContext.HTTP_REQUEST);
				// 获取重定向后的url
				realUrl = url;
				if (HttpUriRequest.class.isInstance(ob)) {
					realRequest = (HttpUriRequest) ob;
					realUrl = targetHost.toString();
					URI u = realRequest.getURI();
					if (u != null) {
						if (u.getHost() == null) {
							realUrl = realUrl + u.getPath();
						} else {
							realUrl = u.toString();
						}
					}
				}
				intent.put(Configs.REALURL, realUrl);
				handleCookie(response);
				String html = HttpClientUtil.getHtml(response);
				client.getCookieStore().getCookies();
				intent.put(HTML, html);
				HtmlQueue.put(intent);
			} catch (Exception e) {
				//e.printStackTrace();
				error(e);
			} finally {
				if (request != null)
					request.abort();
			}
			Thread.yield();
		}
	}

	public void handleCookie(HttpResponse response) {
		Header[] cks = response.getHeaders("set-cookie");
		BasicClientCookie ck = null;
		if (cks != null) {
			for (Header hd : cks) {
				ck = getCookie(hd.getValue());
				addCookie(ck);
			}

		}
	}

	public void addCookie(BasicClientCookie ck) {
		if (cookieMap.get(ck.getName()) == null) {
			cookieMap.put(ck.getName(), ck);
			cookieStore.addCookie(ck);
			synchronized (cookieStore) {
				if (cookieStore.getCookies().size() > 800) {
					cookieStore.clear();
					for (Cookie k : cookieMap.values()) {
						cookieStore.addCookie(k);
					}
				}
			}
		} else {
			cookieMap.get(ck.getName()).setValue(ck.getValue());
			;
		}

	}

	public BasicClientCookie getCookie(String s) {
		String[] ss = s.split(";");
		BasicClientCookie ck = null;
		if (ss != null && ss.length > 1) {
			ss = ss[0].split("=");
			if (ss != null) {
				if (ss.length > 1) {
					ck = new BasicClientCookie(ss[0], ss[1]);
				} else {
					ck = new BasicClientCookie(ss[0], "");
				}
			}
		}
		return ck;
	}

	public void error(Exception e) {
		if (request != null) {
			request.abort();
		}
		if (intent != null) {
			if (host != null) {
				// ProxyPool.delete(host);
			}
			// log.error("爬取失败"+intent.getString(URL));
			intent.put(Configs.SPERROR, "抓取网页出错" + e.getMessage() + "class:"
					+ e.getClass() + ",host:" + intent.get(Configs.HOST));
			// 判断是否为接口调用者
			SpiderEnum se = intent.get(Configs.SPIDERENUM);
			if (intent.getString(Configs.interfaceFlag) != null) {
				SpiderHandler spiderhandler = intent.get(Configs.spiderhandler);
				synchronized (spiderhandler) {
					spiderhandler.notifyAll();
				}
				log.error("++++++HtmlSpiderThread handle error,notifyall,intent :"
						+ intent.hashCode());
			} else if (se.resultHandler != null) {
				// log.info("处理结果");
				se.resultHandler.handler(intent);
			}

		}
	}

}
