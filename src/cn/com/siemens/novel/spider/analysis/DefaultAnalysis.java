package cn.com.siemens.novel.spider.analysis;

import static cn.com.siemens.novel.util.ConfigProperties.HTML;
import static cn.com.siemens.novel.util.ConfigProperties.RESULT;

import cn.com.siemens.novel.spider.core.AnalysisHandler;
import cn.com.siemens.novel.spider.core.IntentMap;

public class DefaultAnalysis implements AnalysisHandler {

    @Override
    public void handler(IntentMap intent) {
        intent.put(RESULT, intent.getString(HTML));
    }

}
