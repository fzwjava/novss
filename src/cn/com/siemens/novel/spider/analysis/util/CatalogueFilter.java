package cn.com.siemens.novel.spider.analysis.util;

import java.util.List;

import cn.com.siemens.novel.bean.KeyValue;

public class CatalogueFilter implements
        AnalysisFilter<List<KeyValue<String, String, String>>> {

    @Override
    public boolean handler(List<KeyValue<String, String, String>> t) {
        int tem = 0;
        int i = 0;
        ToNum toNum = new ToNum();
        int ti = 0;
        for (KeyValue<String, String, String> kv : t) {
            if ((ti = toNum.handle(kv.three)) - tem != 1) {
                i++;
            }
            tem = ti;
        }

        if (i > t.size() / 4 || t.size() < 5) {
            return false;
        }
        return true;
    }
}
