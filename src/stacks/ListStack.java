package stacks;

import lists.LinkedList;

public class ListStack<E> implements StackInterface<E> {


    private Node<E> top;
    private int size;

    public ListStack(){
        top = null;
        size = 0;
    }

    @Override
    public void push(E element) {
        Node<E> newNode = new Node<>(element);
        if(isEmpty()){
            top = newNode;
        }
        Node<E> oldTop = top;
        top = newNode;
        top.next = oldTop;
    }

    @Override
    public E pop() {
        if(isEmpty()){
        return null;}
        E result = top.value;
        top = top.next;
        return result;
    }

    @Override
    public E peek() {
        if(isEmpty())
            return null;
    return top.value;
    }

    @Override
    public boolean isEmpty() {
        return size<=0;
    }

    @Override
    public int size() {
        return size;
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
