package cn.com.siemens.novel.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlcleaner.XPatherException;

import cn.com.siemens.novel.util.FileUtil;

public class Test {
    public static void main(String[] args) throws XPatherException {
        test2();

        /*
         * new Thread() { public void run() { try { test2(); } catch
         * 
         * 
         * 
         * 
         * 
         * (XPatherException e) { // TODO Auto-generated catch block
         * e.printStackTrace(); } }; }.start(); new Thread() { public void run()
         * { try { test2(); } catch (XPatherException e) { // TODO
         * Auto-generated catch block e.printStackTrace(); } }; }.start(); new
         * Thread() { public void run() { try { test2(); } catch
         * (XPatherException e) { // TODO Auto-generated catch block
         * e.printStackTrace(); } }; }.start();
         */
    }

    public static void test1() {
        String ss = "><a href=\"http://www.doupocangqiong.org/quanwenyuedu/1.html\">第1章 陨落的天才</a></li> <li><a href=\"http://www.doupocangqiong.org/quanwenyuedu/2.html\">第2章 斗气大陆</a>";
        // TagNode node = cleaner.clean(ss);
        // System.out.println(node.getAllElements(true));;
        // System.out.println(node.getAttributes());
        Pattern pattern = Pattern
                .compile("<a([^<]*)href=\"*([^<\"]+)\"*([^<]*)>(\\s*)(第*)([-0123456789\u4E00\u4E8C\u4E09\u56DB\u4E94\u516D\u4E03\u516B\u4E5D\u5341\u767E\u5343\u4E24]*)(\\s*)([^<>]+)</a>");
        Matcher matcher = pattern.matcher(ss);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    public static void test2() throws XPatherException {
        int i = 0;
        String html = FileUtil.readFile("e://novel//catalogue.htm", "utf-8");

        // System.out.println(html);
        long l = System.currentTimeMillis();
        Pattern pattern = Pattern
                .compile("<a([^<]*)href=\"*([^<\"]+)\"*([^<]*)>(<[^<>]+>){0,3}(\\s*)((第*)([-0123456789\u4E00\u4E8C\u4E09\u56DB\u4E94\u516D\u4E03\u516B\u4E5D\u5341\u767E\u5343\u4E24零]*)(\\s*)([^<>]+))(</[^<>]+>){0,3}</a>");
        // Pattern pattern = Pattern.compile("<(.*)>.*</\\\\1>");
        Matcher matcher = pattern.matcher(html);
        // HtmlCleaner cleaner = new HtmlCleaner();
        while (matcher.find()) {
            i++;
            // TagNode node = cleaner.clean(matcher.group());
            System.out.println(matcher.group(2));
            // System.out.println(matcher.group(1));
            System.out.println(matcher.group(6));

            /*
             * System.out.println(((TagNode) node.evaluateXPath("//a")[0])
             * .getAttributeByName("href"));;
             */

            // matcher.group(1);
        }
        System.out.println(System.currentTimeMillis() - l);
    }

    public static void test3() {
        try {
            System.out
                    .println(URLDecoder
                            .decode("http://www.baidu.com/s?wd=%E6%90%9E%E6%90%9E%E7%9C%8B&pn=10&tn=baiduhome_pg&ie=utf-8&rsv_page=1",
                                    "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}
