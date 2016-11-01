package cn.com.siemens.novel.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.siemens.novel.util.FileUtil;

public class Test4 {
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        String html = FileUtil.readFile("e://novel//root.htm", "gbk");
        // System.out.println(html);
        Pattern pattern = Pattern
                .compile("<a([^<]*)href=\"*([^<\"]+)\"*([^<]*)>[^<>]+</a>");
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            if (matcher.group(2).contains("/")
                    || matcher.group(2).contains("http")) {
                System.out.println(matcher.group(2) + "------------"
                        + matcher.group());
            }
        }
    }

    public static void test2() {

    }

}
