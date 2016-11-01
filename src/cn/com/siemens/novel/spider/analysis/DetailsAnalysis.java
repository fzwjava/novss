package cn.com.siemens.novel.spider.analysis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.siemens.novel.spider.core.AnalysisHandler;
import cn.com.siemens.novel.spider.core.IntentMap;
import cn.com.siemens.novel.util.ConfigProperties;

public class DetailsAnalysis implements AnalysisHandler {

    @Override
    public void handler(IntentMap intent) {
        Pattern pattern = Pattern
                .compile("[“]*([(\u4E00-\u9FA5)|(\\uF900-\\uFA2D)]+[^“,，。?？”…！!]{0,4}[(\u4E00-\u9FA5)|(\\uF900-\\uFA2D)]+)[“,，。?？”…！!]{0,2}[“,，。?？”…！!]{1,2}");
        Matcher matcher = pattern.matcher(intent
                .getString(ConfigProperties.HTML));
        // System.out.println(intent.getString(ConfigProperties.HTML));
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            sb.append(matcher.group());

        }
        intent.put(ConfigProperties.RESULT, sb.toString());

    }

}
