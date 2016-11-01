package cn.com.siemens.novel.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.siemens.novel.util.FileUtil;

public class Atest {
    public static void main(String[] args) {
        String html = FileUtil.readFile("e://novel//a.htm", "utf-8");
        Pattern pattern = Pattern
                .compile("<a([^<]*)href=\"*([^<\"]+)\"*([^<]*)>\\s*[^<>]+\\s*</a>");
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

}
