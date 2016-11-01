package cn.com.siemens.novel.spider.core;

public interface AnalysisHandler {
    /**
     * 实现类需保证此方法为线程安全，且拥有空构造器
     * 
     * @param intent
     */
    void handler(IntentMap intent);

}
