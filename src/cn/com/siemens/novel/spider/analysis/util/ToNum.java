package cn.com.siemens.novel.spider.analysis.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ToNum {
    Log log = LogFactory.getLog(ToNum.class);
    private final String[] so = { "一", "二", "三", "四", "五", "六", "七", "八", "九",
            "零", "壹", "两", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
    private final String[] replace = { "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

    public Integer handle(String sr) {
        Integer re = null;
        String tm = sr.replaceAll("[万千百十]", "");

        String[] sp = new String[tm.length()];
        for (int i = 0; i < tm.length(); i++) {
            sp[i] = tm.charAt(i) + "";
        }
        StringBuffer rep = new StringBuffer();
        if (sp.length > 0) {
            for (String s : sp) {
                int i = 0;
                for (String k : so) {
                    if (s.equals(k)) {
                        rep.append(replace[i]);
                        break;
                    }
                    if (i == 18) {
                        rep.append(s);
                    }
                    i++;
                }
            }
            if (sr.endsWith("十")) {
                rep.append("0");
            }
        } else {
            rep.append(sr);
        }
        try {
            re = Integer.parseInt(rep.toString());
        } catch (Exception e) {
            e.printStackTrace();
            re = 0;
        }

        return re;

    }

    public static void main(String[] args) {
        long l = System.currentTimeMillis();
         ToNum num = new ToNum();
        
         System.out.println(num.handle("一份早餐"));
        
    }

}
