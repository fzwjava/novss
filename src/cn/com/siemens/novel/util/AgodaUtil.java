package cn.com.siemens.novel.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import cn.com.siemens.novel.spider.util.UrlBean;

public class AgodaUtil {



	static WebClient webClient;

	private static WebClient getWebClient() {
		if (webClient == null) {
			webClient = new WebClient();
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setTimeout(10000);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			
			// webClient.getCookieManager().addCookie(new
			// Cookie("","agoda.version.03", ss));
		}

		return webClient;
	}
	
	public static void main(String[] args) {
		WebClient webClient = getWebClient();
		try {
			HtmlPage page = webClient.getPage("http://www.biquge.com/0_399/1433859.html");
			/*HtmlInput su = (HtmlInput)page.getElementById("su");
			HtmlTextInput kw = (HtmlTextInput)page.getElementById("kw");
			//kw.setValueAttribute("全职高手");
			page = su.click();*/
			/*DomNodeList<DomElement> list = page.getElementsByTagName("a");
			for (DomElement el : list) {
				if("点击查看更多章节".equals(el.getTextContent())){
					page = el.click();
					System.out.println(page.asXml());
					//System.out.println(page.asXml());
				}
			}*/
			String s = page.asXml();
			Pattern pattern = Pattern
	                .compile("[“]*([(\u4E00-\u9FA5)|(\\uF900-\\uFA2D)]+[^“,，。?？”…！!]{0,4}[(\u4E00-\u9FA5)|(\\uF900-\\uFA2D)]+)[“,，。?？”…！!]{0,2}[“,，。?？”…！!]{1,2}");
	        Matcher matcher = pattern.matcher(s);
	        StringBuffer sb = new StringBuffer();
	        while (matcher.find()) {
	            sb.append(matcher.group());

	        }
	        System.out.println(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getSearchUrl(String city) {
		String re = null;
		WebClient webClient = getWebClient();
		try {
			HtmlPage page = webClient.getPage("http://www.agoda.com/zh-cn");
			HtmlTextInput searchText = (HtmlTextInput) page
					.getElementById("SearchInput");
			searchText.setValueAttribute(city);
			DomNodeList<DomElement> list = page.getElementsByTagName("input");
			HtmlInput input = null;
			for (DomElement el : list) {
				input = (HtmlInput) el;
				if (input.getValueAttribute().equals("搜索")) {
					page = input.click();
					re = page.getUrl().toString();
					break;
				}
			}
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return re;
	}

	public String getHtml(UrlBean url, String search) {
		WebClient webClient = getWebClient();
		HtmlPage page = null;

		HtmlPage realpage = null;
		try {
			if (page == null) {
				page = webClient.getPage(URLDecoder.decode(url.getUrl(),
						"utf-8"));
			}
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sim.parse(url.getStayDate()));
			url.setOutDate(url.getOutDate() == 0 ? 1 : url.getOutDate());
			HtmlSelect selectMonth = (HtmlSelect) page
					.getElementById("ddlCheckInMonthYear_left");
			HtmlOption optionMonth = selectMonth.getOptionByValue(calendar
					.get(Calendar.MONTH)
					+ 1
					+ ","
					+ calendar.get(Calendar.YEAR));
			selectMonth.setSelectedAttribute(optionMonth, true);
			HtmlSelect selectDay = (HtmlSelect) page
					.getElementById("ddlCheckInDay_left");
			int da = calendar.get(Calendar.DATE);
			String d = null;
			if (da < 10) {
				d = "0" + da;
			} else {
				d = da + "";
			}
			HtmlOption optionDay = selectDay.getOptionByValue(d);
			selectDay.setSelectedAttribute(optionDay, true);
			HtmlSelect selectContinuous = (HtmlSelect) page
					.getElementById("ctl00_ctl00_MainContent_AlternateHotelLis_hotelleftsearchbox_ddlNights_left");
			HtmlOption ContinuousDay = selectContinuous.getOptionByValue(url
					.getOutDate().toString());
			selectContinuous.setSelectedAttribute(ContinuousDay, true);

			int i = 0;
			String realUrl = null;
			while (i < 3) {
				realpage = page
						.getHtmlElementById(
								"ctl00_ctl00_MainContent_AlternateHotelLis_hotelleftsearchbox_SearchButton")
						.click();
				realUrl = realpage.getUrl().toString();
				if (checkUrl(realUrl)) {
					/*
					 * pageMaps.put(url.getUrl(), new MyPage(realpage,
					 * System.currentTimeMillis()));
					 */
					break;
				}
				i++;
			}
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String pageAsXml = null;
		if (realpage != null) {
			pageAsXml = realpage.asXml();
		}
		webClient.close();
		return pageAsXml;
	}


	// 检验url是否合法
	private static boolean checkUrl(String url) {
		boolean re = false;
		String[] ss = url.split("\\?");
		if (ss != null && ss.length > 1) {
			ss = ss[1].split("&");
			if (ss != null) {
				for (String s : ss) {
					if (s.contains("asq=")) {
						ss = s.split("=");
						if (ss != null && ss[1] != null && !ss[1].equals("")) {
							re = true;
						}
						break;
					}
				}
			}
		}
		return re;
	}

}
