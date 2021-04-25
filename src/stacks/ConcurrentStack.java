package stacks;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentStack<E>  implements StackInterface<E> {

    private final AtomicReference<Node<E>> top = new AtomicReference<>();
    private final AtomicInteger size = new AtomicInteger(0);


    @Override
    public void push(E element) {
        Node<E> oldTop;
        Node<E> newTop = new Node(element);
        do{
            oldTop = top.get();
            newTop.next = oldTop;
        }while(!top.compareAndSet(oldTop,newTop));
        size.incrementAndGet();
    }

    @Override
    public E pop() {
        Node<E> oldTop;
        Node<E> newTop;
        do{
            oldTop = top.get();
            if(oldTop == null){
                return null;
            }
            newTop = oldTop.next;

        }
        while(!top.compareAndSet(oldTop,newTop));
        size.decrementAndGet();
        return oldTop.value;
    }

    @Override
    public E peek() {
        return top.get().value;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return size.get();
    }

    public class Node<E> {
        private E value;
        private Node<E> next;


        public Node(final E value) {
            this.value = value;
            next = null;

        }

        public Node(final E value, final Node<E> next, final Node<E> prev) {
            this.value = value;
            this.next = next;

        }

    }
}
