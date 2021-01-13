/*
 * Course: CS2852
 * Spring 2020
 * File header contains class LookupTable
 * Name: poptilec
 * Created 5/09/2020
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Class creates a LookupTable to store a morse code table
 * within a map
 * @param <K> key as character to be encoded
 * @param <V> value as code associated with key
 */
public class LookupTable<K extends Comparable<? super K>, V> implements Map<K, V> {

    private int size = 0;
    private List<Entry<K, V>> dataList;

    /**
     * Constructor to create a LookupTable object
     */
    public LookupTable(){
        dataList = new ArrayList<>();
    }

    private static class Entry<K extends Comparable<? super K>, V>
            implements Comparable<Entry<K, V>>, Map.Entry<K, V> {

        private K key;
        private V value;

        public Entry(K key, V value){
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public int compareTo(LookupTable.Entry<K, V> o) {
            return o.key.compareTo(key);
        }

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return dataList.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        Entry<K, V> target = new Entry<>((K) key, null);
        return Collections.binarySearch(dataList, target) >= 0;
    }

    @Override
    public V get(Object key) {
        if (key == null) {
            return null;
        } else {
            Entry<K, V> target = new Entry<>((K) key, null);
            int index = Collections.binarySearch(dataList, target);
            if (index >= 0) {
                return dataList.get(index).value;
            } else {
                return null;
            }
        }
    }


    @Override
    public V put(K key, V value) {
        Entry<K, V> newEntry = new Entry<>(key, value);
        V returns = null;
        if (dataList.size() == 0) {
            dataList.add(newEntry);
        } else {
            int index = Collections.binarySearch(dataList, newEntry);
            if (index < 0) {
                index = ++index * -1;
                dataList.add(index, newEntry);
                size++;
            } else {
                returns = dataList.get(index).value;
                dataList.get(index).setValue(value);
            }
        }
        return returns;
    }

    @Override
    public V remove(Object key) {
        Entry<K, V> target = new Entry<>((K) key, null);
        int index = Collections.binarySearch(dataList, target);
        if (index >= 0) {
            target = dataList.get(index);
            dataList.remove(index);
            size--;
        }
        if (index >= 0) {
            return target.value;
        } else {
            return null;
        }
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map m) {

    }

    @Override
    public void clear() {
        dataList = new ArrayList<>();
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }

    @Override
    public Set entrySet() {
        return null;
    }
}
