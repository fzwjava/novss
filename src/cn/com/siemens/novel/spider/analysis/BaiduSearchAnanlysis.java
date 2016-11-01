package cn.com.siemens.novel.spider.analysis;

import static cn.com.siemens.novel.util.ConfigProperties.HTML;
import static cn.com.siemens.novel.util.ConfigProperties.RESULT;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.siemens.novel.spider.core.AnalysisHandler;
import cn.com.siemens.novel.spider.core.IntentMap;

public class BaiduSearchAnanlysis implements AnalysisHandler {
    @Override
    public void handler(IntentMap intent) {
        Pattern pattern = Pattern
                .compile("<a([^<]*)href=\"*([^<\"]+)\"*([^<]*)>\\s*百度快照\\s*</a>");
        Matcher matcher = pattern.matcher(intent.getString(HTML));

        ArrayList<String> list = new ArrayList<String>();
        while (matcher.find()) {
            list.add(matcher.group(2));
        }
        intent.put(RESULT, list);

    }
}
