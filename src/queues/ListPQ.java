package queues;

public class ListPQ<E> {

  private int size;

  private Node<E> first;
  private Node<E> last;

  public ListPQ() {
    this.size = 0;
    this.first = null;
    this.last = null;
  }

  public void enqueue(E e, Comparable priority) {

    final Node<E> newNode = new Node<E>(e, priority);
    if (first == null || priority.compareTo(first.getPriority()) < 0) {
      newNode.setNext(first);
      first = newNode;
      if (size == 0) { // this is the first element
        last = first;
      }
    } else {
      Node<E> cursor = first;
      while (cursor.getNext() != null && priority.compareTo(cursor.getNext().getPriority()) > 0) {
        cursor = cursor.getNext();
      }
      newNode.setNext(cursor.getNext());
      if (cursor.getNext() == null) { // last element
        last = newNode;
      }
      cursor.setNext(newNode);
    }
    size++;
  }

  public E dequeue() {
    if (size == 0) {
      return null;
    }

    E firstElement = first.getElement();
    first = first.getNext();
    if (--size == 0) {
      last = null;
    }
    return firstElement;
  }

  public E getFirst() {
    if (size == 0) {
      return null;
    }
    return first.getElement();
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("[");
    Node<E> cursor = first;
    while (cursor != null) {
      stringBuilder.append("(" + cursor.getElement() + ", " + cursor.getPriority() + ") ");
      cursor = cursor.getNext();
    }
    stringBuilder.append("]");
    return stringBuilder.toString();
  }

  private class Node<E> {

    private final Comparable priority;
    private E element;
    private Node<E> next;

    public Node(E element, Comparable priority) {
      this(element, priority, null);
    }

    public Node(E element, Comparable priority, Node<E> next) {
      this.element = element;
      this.priority = priority;
      this.next = next;
    }

    public E getElement() {
      return element;
    }

    public void setElement(E element) {
      this.element = element;
    }

    public Node<E> getNext() {
      return next;
    }

    public void setNext(Node<E> next) {
      this.next = next;
    }

    public Comparable getPriority() {
      return priority;
    }
  }
}
