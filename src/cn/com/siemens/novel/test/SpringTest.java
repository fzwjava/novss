package cn.com.siemens.novel.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import cn.com.siemens.novel.entity.TestM;

public class SpringTest {
	

	
	@org.junit.Test
	public void test(){
		ApplicationContext ac = new FileSystemXmlApplicationContext("applicationContext-init.xml","applicationContext-mongodb.xml");
		MongoTemplate mongoTemplate =(MongoTemplate) ac.getBean("mongoTemplate");
		TestM t = new TestM();
		t.setId("111111");
		t.setMsgId("msg");
		mongoTemplate.save(t);
		
	}

	public static void main(String[] args) {
		ApplicationContext ac = new FileSystemXmlApplicationContext("applicationContext-init.xml","applicationContext-mongodb.xml");
		MongoTemplate mongoTemplate =(MongoTemplate) ac.getBean("mongoTemplate");
		TestM t = new TestM();
		t.setMsgId("msg");
		mongoTemplate.insert(t);
		List<TestM>  l = mongoTemplate.findAll(TestM.class);
		System.out.println(l.size());
	}
}
