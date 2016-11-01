package cn.com.siemens.novel.spider.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.siemens.novel.bean.KeyValue;
import cn.com.siemens.novel.bean.Novel;
import cn.com.siemens.novel.spider.core.AnalysisHandler;
import cn.com.siemens.novel.spider.core.IntentMap;
import cn.com.siemens.novel.util.ConfigProperties;

public class CatalogueAnalysis implements AnalysisHandler {

    @Override
    public void handler(IntentMap intent) {
        Pattern pattern = Pattern
                .compile("<a([^<]*)href=\"*([^<\"]+)\"*([^<]*)>(<[^<>]+>){0,3}(\\s*)((第*)([-0123456789\u4E00\u4E8C\u4E09\u56DB\u4E94\u516D\u4E03\u516B\u4E5D\u5341\u767E\u5343\u4E24零]+)(\\s*)([^<>]+))(</[^<>]+>){0,3}</a>");
        Matcher matcher = pattern.matcher(intent
                .getString(ConfigProperties.HTML));
        Novel catalogue = new Novel();
        List<KeyValue<String, String, String>> details = new ArrayList<KeyValue<String, String, String>>();
        String tm = null;
        while (matcher.find()) {
            tm = matcher.group(2);
            if (tm != null) {
                if (!tm.contains("http://")) {
                    tm = intent.getString("pa") + tm;
                }
            }
            details.add(new KeyValue<String, String, String>(matcher.group(6),
                    tm));
        }
        catalogue.setDetails(details);
        intent.put(ConfigProperties.RESULT, catalogue);
    }

}
