package cn.com.siemens.novel.bean;

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
                         + "\n");
             } catch (IllegalArgumentException e) {
                 e.printStackTrace();
             } catch (IllegalAccessException e) {
                 e.printStackTrace();
             }
         }
         return ss.toString();
    }
}
