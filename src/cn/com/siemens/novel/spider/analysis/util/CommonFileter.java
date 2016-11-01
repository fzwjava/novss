package cn.com.siemens.novel.spider.analysis.util;

import cn.com.siemens.novel.bean.UrlBean;

public class CommonFileter implements AnalysisFilter<UrlBean> {

    @Override
    public boolean handler(UrlBean t) {
        boolean re = false;
        if (t.getUrl() != null && (t.getUrl().contains("http"))
                && !(t.getUrl().contains("javascript"))) {
            re = true;
        }
        return re;
    }

}
