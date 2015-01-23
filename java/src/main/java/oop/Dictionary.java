package oop;

/**
 * Created by sandro on 1/12/15.
 */
public interface Dictionary<K,V> {
    public V get(K key);
    public void set(K key, V value);
}
