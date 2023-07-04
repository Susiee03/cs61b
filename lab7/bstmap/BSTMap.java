package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
  private class BSTNode{
    K key;
    V value;
    BSTNode left;
    BSTNode right;

    BSTNode(K k, V v, BSTNode l, BSTNode r) {
      key = k;
      value = v;
      left = l;
      right = r;
    }

  }

  private int size;
  private BSTNode root ;


  public BSTMap() {
    size = 0;
    root = null;
  }

  @Override public void clear() {
    root = null;
    size = 0;
  }

  @Override public boolean containsKey(K key) {
    return contains(key, root);
  }

  private boolean contains(K key, BSTNode node) {
    if (node == null) {
      return false;
    }
    if (key.compareTo(node.key) < 0) {
      return contains(key, node.left);
    }
    else if (key.equals(node.key)) {
      return true;
    }
    else {
      return contains(key, node.right);
    }

  }

  @Override public V get(K key) {
    return getValue(key, root);
  }

  private V getValue(K key, BSTNode node) {
    if (node == null) {
      return null;
    }
    if (key.compareTo(node.key) < 0) {
      return getValue(key, node.left);
    }
    else if (key.equals(node.key)) {
      return node.value;
    }
    else {
      return getValue(key, node.right);
    }
  }

  @Override public int size() {
    return size;
  }

  @Override public void put(K key, V value) {
    root = putNode(root, key, value);
  }

  private BSTNode putNode(BSTNode node, K key, V value) {
    if (node == null) {
      size++;
      return new BSTNode(key, value, null, null);
    }
    else if (node.key.compareTo(key) < 0) {
      node.right = putNode(node.right, key, value);
    }
    else if (node.key.equals(key)) {
      node.value = value;
    }
    else {
      node.left = putNode(node.left, key, value);
    }
    return node;
  }

  public void printInOrder() {
    printInOrder(root);
  }

  private void printInOrder(BSTNode node) {
    if (node != null) {
//      if (node.left != null && node.right != null) {
//        printInOrder(node.left);
//        printInOrder(node);
//        printInOrder(node.right);
//      }
//      else if (node.left != null) {
//        printInOrder(node.left);
//        printInOrder(node);
//      }
//
//      else if (node.right != null) {
//        printInOrder(node);
//        printInOrder(node.right);
//      }
//
//      else {
//        System.out.println(node.key.toString() + " -> " + node.value.toString());
//      }
//
      printInOrder(node.left);
      System.out.println(node.key.toString() + " -> " + node.value.toString());
      printInOrder(node.right);
    }
    else {
      return;
    }
  }

  @Override public Set<K> keySet() {
    throw new UnsupportedOperationException();
  }

  @Override public V remove(K key) {
    throw new UnsupportedOperationException();
  }

  @Override public V remove(K key, V value) {
    throw new UnsupportedOperationException();
  }

  @Override public Iterator<K> iterator() {
    throw new UnsupportedOperationException();
  }
}
