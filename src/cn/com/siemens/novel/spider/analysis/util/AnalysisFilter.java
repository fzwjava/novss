package cn.com.siemens.novel.spider.analysis.util;

public interface AnalysisFilter<T> {

    public boolean handler(T t);

}
