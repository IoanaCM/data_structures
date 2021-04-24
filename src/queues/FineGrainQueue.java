package queues;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FineGrainQueue<E> implements Queue<E>{


    @Override
    public E getFirst() {
        return first.value;
    }


    private final AtomicInteger size;
    private QNode<E> first;
    private QNode<E> last;

    public FineGrainQueue() {
        size = new AtomicInteger(0);
        first = new QNode<E>(null);
        last = new QNode<E>(null);
        first.next = last;
        last.pred = first;
    }

    @Override
    public void enqueue(E element) {
        //TODO for Question 1
        QNode<E> newNode = new QNode<>(element);
        while (true) {
            QNode<E> lastPred = last.pred;

            lastPred.lock();
            last.lock();

            if (lastPred == last.pred) {
                lastPred.next = newNode;
                newNode.pred = lastPred;
                newNode.next = last;
                last.pred = newNode;
                size.incrementAndGet();

                last.unlock();
                lastPred.unlock();
                break;
            }
            last.unlock();
            lastPred.unlock();
        }

    }

    @Override
    public E dequeue() {
        //TODO for Question 1
        while (true) {
            QNode<E> firstNext = first.next;
            first.lock();
            firstNext.lock();

            if (firstNext == first.next) {
                if (first.next == last) {
                    return null;
                }

                E result = firstNext.value;
                first.next = firstNext.next;
                first.next.pred = first;


                size.decrementAndGet();

                firstNext.unlock();
                first.unlock();
                return result;
            }
            firstNext.unlock();
            first.unlock();
        }

    }

    @Override
    public int size() {
        //TODO for Question 1
        return size.get();
    }

    public class QNode<E> {
        private E value;
        private QNode<E> next;
        private QNode<E> pred;
        private Lock lock = new ReentrantLock();

        public QNode(final E value) {
            this.value = value;
            next = null;
            pred = null;
        }

        public QNode(final E value, final QNode<E> next, final QNode<E> pred) {
            this.value = value;
            this.next = next;
            this.pred = pred;
        }

        public void lock() {
            lock.lock();
        }

        public void unlock() {
            lock.unlock();
        }
    }
}
