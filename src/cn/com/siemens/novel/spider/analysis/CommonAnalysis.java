package cn.com.siemens.novel.spider.analysis;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.siemens.novel.bean.KeyValue;
import cn.com.siemens.novel.bean.Novel;
import cn.com.siemens.novel.bean.UrlBean;
import cn.com.siemens.novel.spider.analysis.util.CatalogueFilter;
import cn.com.siemens.novel.spider.analysis.util.CommonFileter;
import cn.com.siemens.novel.spider.core.AnalysisHandler;
import cn.com.siemens.novel.spider.core.IntentMap;
import cn.com.siemens.novel.util.ConfigProperties;

public class CommonAnalysis implements AnalysisHandler {

    @Override
    public void handler(IntentMap intent) {
        Pattern pattern = Pattern
                .compile("<a([^<]*)href=\"*([^<\"]+)\"*([^<]*)>(<[^<>]+>){0,3}(\\s*)((第*)([0123456789一二三四五六七八九零壹两叁肆伍陆柒捌玖]+)(\\s*)([^<>]+))(</[^<>]+>){0,3}</a>");
        Matcher matcher = pattern.matcher(intent
                .getString(ConfigProperties.HTML));
        List<KeyValue<String, String, String>> details = new ArrayList<KeyValue<String, String, String>>();
        UrlBean father = intent.get("father");
        String host = null;
        try {
            host = new URI(father.getUrl()).getHost();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String tm = null;
        while (matcher.find()) {
            tm = matcher.group(2);
            if (tm != null) {
                if (!tm.contains("http://")) {
                    tm = host + tm;
                }
            }
            // System.out.println(matcher.group());
            details.add(new KeyValue<String, String, String>(matcher.group(6),
                    tm, matcher.group(8)));
        }
        if ((new CatalogueFilter().handler(details))
                && father.getDepth() < ConfigProperties.dpth) {
            List<UrlBean> list = new ArrayList<UrlBean>();
            Pattern pattern2 = Pattern
                    .compile("<a([^<]*)href=[\"|']*([^<\"']+)[\"|']*([^<>'\"]*)>([^<>]+)</a>");
            Matcher matcher2 = pattern2.matcher(intent
                    .getString(ConfigProperties.HTML));
            UrlBean urlBean = null;
            CommonFileter CommonFileter = new CommonFileter();

            while (matcher2.find()) {
                urlBean = new UrlBean();
                urlBean.setFather(father.getUrl());
                if (matcher2.group(2).startsWith("/")) {
                    urlBean.setUrl(host + matcher2.group(2));
                } else {
                    urlBean.setUrl(matcher2.group(2));
                }
                urlBean.setText(matcher2.group(4));
                urlBean.setDepth(father.getDepth() + 1);
                urlBean.setUpdateTime(System.currentTimeMillis());
                if (CommonFileter.handler(urlBean)) {
                    list.add(urlBean);
                }
            }
            intent.put("reFlag", 0);
            intent.put(ConfigProperties.RESULT, list);
        } else {
            Pattern pa = Pattern.compile("作\\s*者\\s*[:：]\\s*([^<>]+)");
            Matcher matcher3 = pa.matcher(intent
                    .getString(ConfigProperties.HTML));
            Novel catalogue = new Novel();
            if (matcher3.find()) {
                catalogue.setAuthor(matcher3.group(1));
            }
            catalogue.setDetails(details);
            catalogue.setUrl(father.getUrl());
            catalogue.setName(father.getText());
            intent.put(ConfigProperties.RESULT, catalogue);
            intent.put("reFlag", 1);
        }
    }
}
