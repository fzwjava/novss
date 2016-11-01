package cn.com.siemens.novel.spider.util;

import java.lang.reflect.Field;

public class BeanUtil {
	 public static String BeantoString(Object ob) {
    	 Field[] fields = ob.getClass().getDeclaredFields();
         StringBuffer ss = new StringBuffer();
         for (Field f : fields) {
             f.setAccessible(true);

             try {
                 ss.append(f.getName() + ":"
                         + (f.get(ob) == null ? null : f.get(ob).toString())
                         + ", ");
             } catch (IllegalArgumentException e) {
                 e.printStackTrace();
             } catch (IllegalAccessException e) {
                 e.printStackTrace();
             }
         }
         if(ss.length()>0){
        	 ss.append("\r\n");
         }
         return ss.toString();
    }
}
