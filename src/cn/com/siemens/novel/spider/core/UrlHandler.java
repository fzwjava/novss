package cn.com.siemens.novel.spider.core;

/**
 * 可以在prameter中自定义设置HttpMethod,现在只支持HttpMethod和HttpGet
 * 
 * @author Administrator
 * 
 */

public interface UrlHandler {

    void handler(IntentMap prameter);

}
