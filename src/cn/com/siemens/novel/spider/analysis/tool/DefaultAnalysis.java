package cn.com.siemens.novel.spider.analysis.tool;

import static cn.com.siemens.novel.spider.core.Configs.HTML;
import static cn.com.siemens.novel.spider.core.Configs.RESULT;

import cn.com.siemens.novel.spider.core.AnalysisHandler;
import cn.com.siemens.novel.spider.core.IntentMap;



public class DefaultAnalysis implements AnalysisHandler {

    public void handler(IntentMap intent) {
        intent.put(RESULT, intent.getString(HTML));
    }

}
