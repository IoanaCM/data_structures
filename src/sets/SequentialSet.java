package sets;

import bsts.SequentialBST;

public class SequentialSet<E> implements Set<E> {

  private int size;
  private Node<E> first, last;

  public SequentialSet() {
    first = new Node<>(null, Integer.MIN_VALUE, null);
    last = new Node<>(null, Integer.MAX_VALUE, null);
    first.next = last;
  }

  private Location<E> find(Node<E> start, int key) {
    Node<E> pred, curr;
    curr = start;
    do {
      pred = curr;
      curr = curr.next;
    } while (curr.key < key);
    return new Location<>(pred, curr);
  }

  @Override
  public boolean add(E item) {
    Node<E> node = new Node<>(item);
    Location<E> loc = find(first, node.key);
    if (loc.curr.key == node.key) {
      return false;
    } else {
      node.next = loc.curr;
      loc.pred.next = node;
      size++;
      return true;
    }
  }

  @Override
  public boolean remove(E item) {
    Node<E> node = new Node<>(item);
    Location<E> loc = find(first, node.key);
    if (loc.curr.key > node.key) {
      return false;
    } else {
      loc.pred.next = loc.curr.next;
      size--;
      return true;
    }
  }

  @Override
  public boolean contains(E item) {
    Node<E> node = new Node<>(item);
    Location<E> loc = find(first, node.key);
    return loc.curr.key == node.key;
  }

  @Override
  public int size() {
    return size;
  }

  private static class Node<E> {

    private E content;
    private int key;
    private Node<E> next;

    public Node(E content) {
      this(content, null);
    }

    public Node(E content, Node<E> next) {
      this(content, content.hashCode(), next);
    }

    public Node(E content, int key, Node<E> next) {
      this.content = content;
      this.next = next;
      this.key = key;
    }
  }

  private static class Location<T> {

    public final Node<T> pred, curr;

    public Location(Node<T> pred, Node<T> curr) {
      this.pred = pred;
      this.curr = curr;
    }
  }
}
