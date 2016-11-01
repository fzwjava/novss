package cn.com.siemens.novel.spider.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IntentMap {
	public IntentMap(){}
	public IntentMap(Map<String, Object> mp){
		this.date = mp;
	}
    private Map<String, Object> date = new HashMap<String, Object>();

    public synchronized Object put(String key, Object value) {
        return date.put(key, value);
    }

    public Map<String, Object> getDate() {
        return date;
    }

    public void setDate(Map<String, Object> date) {
        this.date = date;
    }

    public synchronized String getString(String key) {

        Object o = date.get(key);
        if (o != null) {
            return o.toString();
        }
        return null;
    }

    public synchronized int getInt(String key) {
        Object o = date.get(key);
        if (o == null || o.toString().equals("")) {
            // 抛异常或未处�?
            return -1;
        }
        return Integer.valueOf(o.toString());
    }

    @SuppressWarnings("unchecked")
	public synchronized <T> T get(String key) {
        Object o = date.get(key);
        if(o==null){
        	return null;
        }
        return (T) o;
    }

    public synchronized Set<String> keySet() {
        return date.keySet();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return date.toString();
    }

}
