package lists;

import lists.List;

public class OrderedList<E extends Comparable<E>> implements List<E> {


    private Node<E> head;
    private int size;

    public OrderedList() {
        head = null;
        size = 0;
    }

    @Override
    public void add(E element) {
        Node<E> newNode = new Node(element);
        if (head == null || element.compareTo(head.value) < 0) {
            newNode.setNext(head);
            head = newNode;
        } else {
            Node<E> curr = head;
            while (curr.next.value.compareTo(element)<0) {
                curr = curr.next;
            }
            newNode.setNext(curr.next);
            curr.setNext(newNode);
        }
        size++;
    }

    @Override
    public void add(E element, int position) {
        // not supported
    }

    @Override
    public E get(int position) {
        if (0 > position || position >= size) {
            throw new IndexOutOfBoundsException();
        }
        int i = 0;
        Node<E> curr = head;
        while (i < position) {
            curr = curr.next;
            i++;
        }
        return curr.value;
    }

    @Override
    public E remove(int position) {
        if (0 > position || position >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (position == 0) {
            E val = head.value;
            head = head.next;
            return val;
        }
        Node<E> prev = head;
        Node<E> curr = head;
        int i = 0;
        while (i < position) {
            prev = curr;
            curr = curr.next;
            i++;
        }
        prev.next.setNext(curr.next);
        return curr.getValue();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
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

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }
}
