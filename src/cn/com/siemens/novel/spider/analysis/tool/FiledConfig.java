package cn.com.siemens.novel.spider.analysis.tool;

import cn.com.siemens.novel.spider.core.IntentMap;



public class FiledConfig extends IntentMap {
    public FiledConfig setPath(String path) {
        super.put("path", path);
        return this;
    }

    public <T> FiledConfig setFiledMath(FiledMathch<T> filedMath) {
        super.put("filedmatch", filedMath);
        return this;
    }

    public FiledConfig setFiledCustom(FiledCustom custom) {
        super.put("custom", custom);
        return this;
    }

    public FiledConfig setValue(Object o) {
        super.put("value", o);
        return this;
    }
}
