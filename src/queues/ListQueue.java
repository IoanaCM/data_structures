package queues;

public class ListQueue<E> implements Queue<E> {

  private int size;
  private Node<E> first;
  private Node<E> last;

  public ListQueue() {
    size = 0;
    first = null;
    last = null;
  }

  @Override
  public void enqueue(E element) {
    // TODO for Question 1
    if (first == null) {
      first = new Node<>(element);
      last = first;

    } else {
      Node<E> newNode = new Node<>(element);
      Node<E> prevLast = last;
      last = newNode;
      prevLast.next = newNode;
    }
    size++;
  }

  @Override
  public E dequeue() {
    // TODO for Question 1
    if (size > 0) {
      E result = first.value;
      first = first.next;
      if (size == 1) {
        last = null;
      }
      size--;
      return result;
    }
    return null;
  }

  @Override
  public E getFirst() {
    if(size == 0){
    return null;}
    return first.value;
  }

  @Override
  public int size() {
    // TODO for Question 1
    return size;
  }

  public class Node<E> {
    private E value;
    private Node<E> next;

    public Node(final E value) {
      this.value = value;
      next = null;
    }

    public Node(final E value, final Node<E> next) {
      this.value = value;
      this.next = next;
    }
  }
}
