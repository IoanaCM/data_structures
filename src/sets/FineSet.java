package sets;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FineSet<E> implements Set<E> {
    AtomicInteger size = new AtomicInteger(0);
    private Node<E> head, tail;

    public FineSet() {
        head = new Node(null, Integer.MIN_VALUE, null);
        tail = new Node(null, Integer.MAX_VALUE, null);
        head.next = tail;
    }

    private Location<E> find(Node<E> start, int key) {
        Node<E> pred, curr;
        pred = start;
        pred.lock();
        curr = start.next();
        curr.lock();
        while (curr.key() < key) {
            pred.unlock();
            pred = curr;
            curr = curr.next();
            curr.lock();
        }
        return new Location<E>(pred, curr);
    }

    @Override
    public boolean add(E item) {
        Node<E> node = new Node<>(item);
        Node<E> pred = null, curr = null;
        try {
            Location<E> where = find(head, node.key());
            pred = where.pred;
            curr = where.curr;
            if (where.curr.key() == node.key()) {
                return false;
            } else {
                node.setNext(where.curr);
                where.pred.setNext(node);
                size.incrementAndGet();
                return true;
            }
        } finally {
            pred.unlock();
            curr.unlock();
        }
    }

    @Override
    public boolean remove(E item) {
        Node<E> node = new Node<>(item);
        Node<E> pred = null, curr = null;
        try {
            Location<E> where = find(head, node.key());
            pred = where.pred;
            curr = where.curr;
            if (where.curr.key() > node.key()) {
                return false;
            } else {
                where.pred.setNext(where.curr.next());
                size.decrementAndGet();
                return true;
            }
        } finally {
            pred.unlock();
            curr.unlock();
        }
    }

    @Override
    public boolean contains(E item) {
        Node<E> node = new Node<>(item);
        Node<E> pred = null, curr = null;
        try {
            Location<E> expectedPosition = find(head, node.key());
            pred = expectedPosition.pred;
            curr = expectedPosition.curr;
            return expectedPosition.curr.key() == node.key();
        } finally {
            pred.unlock();
            curr.unlock();
        }
    }

    @Override
    public int size() {
        return size.get();
    }

    private static class Location<T> {

        public final Node<T> pred, curr;

        public Location(Node<T> pred, Node<T> curr) {
            this.pred = pred;
            this.curr = curr;
        }
    }

    private static class Node<E> {

        private final Lock lock = new ReentrantLock();
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

        public void lock(){
            lock.lock();
    }
    public void unlock(){
            lock.unlock();
    }
    public Node<E> next(){
            return next;
    }


        public E getContent() {
            return content;
        }


        public int key() {
            return key;
        }
        public void setItem(E content) {
            this.content = content;
        }


        public void setKey(int key) {
            this.key = key;
        }


        public void setNext(Node<E> next) {
            this.next = (Node<E>) next;
        }

    }
}
