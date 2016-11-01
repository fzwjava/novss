package cn.com.siemens.novel.spider.analysis.tool;

import java.util.HashMap;


//字段...,path,字段表达式,type,属性,类表达式
public class BeanConfig extends HashMap<String, FiledConfig> {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 8550421981831313220L;

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
	        Object o = super.get(key);
	        return (T) o;
	    }
	
}
