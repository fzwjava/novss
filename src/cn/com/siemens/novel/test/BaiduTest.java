package cn.com.siemens.novel.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.siemens.novel.util.FileUtil;

public class BaiduTest {
	public static void main(String[] args) {
		test1();
	}
	public static void test1(){
		String html = FileUtil.readFile("e://novel//baidu.htm", "utf-8");
		
		 //System.out.println(html);
		Pattern pattern = Pattern
				.compile("<a([^<]*)href=\"*([^<\"]+)\"*([^<]*)>\\s*∞Ÿ∂»øÏ’’\\s*</a>");
		// Pattern pattern = Pattern.compile("<(.*)>.*</\\\\1>");
		Matcher matcher = pattern.matcher(html);
		int i = 0;
		while(matcher.find()){
			System.out.println(matcher.group());
			System.out.println("---------------");
			System.out.println(matcher.group(2));
			System.out.println("-------------");
			i++;
		}
		System.out.println(i);
	}

}
