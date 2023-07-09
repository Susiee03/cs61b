package hashmap;

import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {



    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!

    private int size;
    private double loadFactor = 0.75;
    private int num;

    private HashSet<K> keys;

    /** Constructors */
    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        size = initialSize;
        loadFactor = maxLoad;
        num = 0;
        keys = new HashSet<>();
        buckets = createTable(size);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects.
     * Seen as the first column of hash map.
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table = new Collection[tableSize];
        for (int i=0; i < tableSize; i++) {
            table[i] = createBucket();
        }
        return table;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    @Override public void clear() {
        num = 0;
        buckets = createTable(size);
        keys = new HashSet<>();

    }

    @Override public boolean containsKey(K key) {
        return keys.contains(key);
    }

    @Override public V get(K key) {
        int hashCode = key.hashCode();
        int position = Math.floorMod(hashCode, size);

        for (Node node : buckets[position]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    @Override public int size() {
        return num;
    }

    @Override public void put(K key, V value) {
        if ((double) num/size > loadFactor) {
            resize(size*2);
        }

        Node putItem = createNode(key, value);
        int hashCode = key.hashCode();
        int position = Math.floorMod(hashCode, size);
        if (buckets[position].isEmpty()) {
            buckets[position].add(putItem);
            keys.add(key);
            num++;
        }
        else {
            for (Node node : buckets[position]) {
                if (node.key.equals(key)) {
                    node.value = value;
                    return;
                }
            }
            buckets[position].add(putItem);
            num++;
            keys.add(key);
        }
    }



    private void resize(int resize) {
        size = resize;
        Collection<Node>[] oldBucket = buckets;
        buckets = createTable(resize);
        for (int i = 0; i < size/2; i++) {
            for (Node node: oldBucket[i]) {
                if (node != null) {
                    this.put(node.key, node.value);
                    num --;
                }
            }
        }
    }

    @Override public Set<K> keySet() {
        return keys;
    }

    @Override public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override public Iterator<K> iterator() {
        return new hashIter();
    }

    private class hashIter implements Iterator<K> {
        private HashSet<K> k;
        public hashIter() {
            k = keys;

        }
        @Override public boolean hasNext() {
            return k.size() > 0;
        }

        @Override public K next() {
            K curr = k.stream().findFirst().get();
            k.remove(curr);
            return curr;
        }
    }

}
