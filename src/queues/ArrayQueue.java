package queues;

import java.util.Objects;

public class ArrayQueue<E> implements Queue<E> {

    private int first;
    private int last;
    private E[] elements;
    private static int DEFAULT_INITIAL_SIZE = 100;


    public ArrayQueue(){
        this(DEFAULT_INITIAL_SIZE);
    }

    public ArrayQueue(int size){
        elements = (E[]) new Objects[size];
        first = -1;
        last = -1;
    }

    @Override
    public void enqueue(E e) {
        checkCapacity();
            if(isEmpty()){
                first++;
            }
            last = (last+1) % elements.length;
            elements[last+1] = e;
    }

    @Override
    public E dequeue() {
        if(isEmpty()){
            return null;
        }

        final E firstVal = elements[first];
        elements[first] = null;

        if(first==last){
            first = -1;
            last = -1;
        }else{
            first = (first + 1) % elements.length;
        }
        return firstVal;
    }

    @Override
    public E getFirst() {
        return (isEmpty()) ? null : elements[first];
    }

    @Override
    public int size() {
        return last+1;
    }

    public void checkCapacity(){
        if((last + 1) % elements.length == first) {
            DEFAULT_INITIAL_SIZE++;
        }
    }
}
