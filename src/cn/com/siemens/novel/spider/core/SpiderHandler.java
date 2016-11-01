package cn.com.siemens.novel.spider.core;



public abstract class SpiderHandler {

    // 当前流程上下�?
    public IntentMap intent;

    public SpiderHandler() {
        this.intent = new IntentMap();
    }

    public <T> T handler(SpiderEnum sn) {
    	beforeMethod(sn);
        intent.put(Configs.SPIDERENUM, sn);
        try {
            if (sn.urlHandler != null) {
                sn.urlHandler.handler(intent);
            }
            intent.put(Configs.spiderhandler, this);
            SpManager.urlMsg.put(intent);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return customMethod(sn);
    }
    
    public  void beforeMethod(SpiderEnum sn){}

    public abstract <T> T customMethod(SpiderEnum sn);
}
