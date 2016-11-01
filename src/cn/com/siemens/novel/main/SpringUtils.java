package cn.com.siemens.novel.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringUtils {
	
	public static ApplicationContext springFactory = new FileSystemXmlApplicationContext("applicationContext-init.xml","applicationContext-mongodb.xml");
	
	
}
