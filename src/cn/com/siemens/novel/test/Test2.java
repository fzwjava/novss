package cn.com.siemens.novel.test;

import java.io.IOException;
import java.io.StringBufferInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import cn.com.siemens.novel.util.FileUtil;
import cn.com.siemens.novel.util.HttpUtil;

public class Test2 {

	public static void main(String[] args) {
		test1();
		
	}

	public static void test1() {
		String html = FileUtil.readFile("e://novel//details//t1.htm", "gbk");
		//System.out.println(html);
		Pattern pattern = Pattern
				.compile("[¡°]*[(\u4E00-\u9FA5)|*]+[,£¬¡£?£¿¡±¡­£¡!]{1,2}");
		// Pattern pattern = Pattern.compile("<(.*)>.*</\\\\1>");
		Matcher matcher = pattern.matcher(html);
		HtmlCleaner cleaner = new HtmlCleaner();
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			
			/*TagNode node = cleaner.clean(matcher.group());

			try {
				((TagNode) node.evaluateXPath("//a")[0])
						.getAttributeByName("href");
			} catch (XPatherException e) {

				e.printStackTrace();
			}

			node.getAttributeByName("href");*/
			sb.append(matcher.group());;
		}
		System.out.println(sb.toString());
	}
	
	public static void test2(){
		try {
			String html = HttpUtil.send(new URI("http://read.qidian.com/BookReader/1209977,23458423.aspx"), null);
			System.out.println(html);
			FileUtil.saveFile(html,"e://novel//details//t1.htm" );
		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public static void test3(){
		char c = ',';
		System.out.println(Integer.toHexString((int)c));
		System.out.println(Character.isValidCodePoint(0x2c));
	}
}
