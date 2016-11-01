package cn.com.siemens.novel.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class ATests {
    @Test
    public void test() {
        Pattern pattern = Pattern
                .compile("<a([^<]*)href=\"*([^<\"]+)\"*([^<]*)>(<[^<>]+>){0,3}(\\s*)(第*)([0123456789一二三四五六七八九零壹两叁肆伍陆柒捌玖]+)(\\s*)([^<>]+)(</[^<>]+>){0,3}</a>");
        Matcher matcher = pattern
                .matcher("<a href=\"http://leduwo.com/modules/article/toplist.php?sort=size&page=669\"></a>");
        if (matcher.find()) {
            System.out.println(matcher.group());
            System.out.println(matcher.group(8));
        }
    }
}
