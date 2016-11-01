package cn.com.siemens.novel.spider.analysis;

import static cn.com.siemens.novel.util.ConfigProperties.HTML;

import java.util.List;

import cn.com.siemens.novel.bean.UrlBean;
import cn.com.siemens.novel.spider.analysis.tool.FiledConfig;
import cn.com.siemens.novel.spider.analysis.tool.GetBeanByHtml;
import cn.com.siemens.novel.spider.core.AnalysisHandler;
import cn.com.siemens.novel.spider.core.IntentMap;
import cn.com.siemens.novel.util.ConfigProperties;

public class GoogleSearchAnalysis implements AnalysisHandler {

    @Override
    public void handler(IntentMap intent) {
        String html = intent.getString(HTML);
        IntentMap con = new IntentMap();
        con.put("listpath", new FiledConfig().setPath("//ol[@id='rso']/li"));
        con.put("url", new FiledConfig().setPath("//h3[@class='r']//a[1]$href"));

        // con.put("depth", new FiledConfig().setValue(0));
        // con.put("significance", new FiledConfig().setValue(0));
        con.put("updateTime",
                new FiledConfig().setValue(System.currentTimeMillis()));

        GetBeanByHtml<UrlBean> getBean = new GetBeanByHtml<UrlBean>(
                UrlBean.class, con, html);
        List<UrlBean> list = getBean.getListBean();
        intent.put(ConfigProperties.RESULT, list);
    }
}
