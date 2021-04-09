package queues;

public interface Queue<E> {
    public void enqueue(E e);

    public E dequeue();

    public E getFirst();

    default public boolean isEmpty() {
        return size() == 0;
    }

    public int size();
}
