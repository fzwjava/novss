package cn.com.siemens.novel.bean;

public class KeyValue<K, V, T> {
    public K key;
    public V value;
    public T three;

    public void setThree(T three) {
        this.three = three;
    }

    public T getThree() {
        return three;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getV() {
        return value;
    }

    public void setV(V v) {
        this.value = v;
    }

    public KeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public KeyValue(K key, V value, T three) {
        this.key = key;
        this.value = value;
        this.three = three;
    }

    public KeyValue() {
    }

    @Override
    public String toString() {
        return BeanUtil.BeantoString(this);
    }

}
