package lists;

import java.util.Deque;

public class ArrayList<E> implements List<E> {

    public static int DEFAULT_INITIAL_SIZE = 100;
    private int size;
    private final E[] elements;

    public ArrayList(int initialSize){
        size = 0;
        elements =(E[]) new Object[initialSize];
    }

    public ArrayList(){
        this(DEFAULT_INITIAL_SIZE);
    }

    @Override
    public void add(E element) {
        if(size<DEFAULT_INITIAL_SIZE){
            elements[size] = element;
            size++;
        }
    }

    @Override
    public void add(E element, int position) {
        if(0<=position && size<DEFAULT_INITIAL_SIZE){
            shiftRight(position);
            elements[position] = element;
            size++;
        }else
            if(0<=position){
                extendCapacity(position+1);

        }

    }

    @Override
    public E get(int position) {
        if(0<=position && position<size){
            return elements[position];
        }
        else {
            return null;
        }
    }

    @Override
    public E remove(int position) {
        if(0<=position && position<size){
            E result = elements[position];
            shiftLeft(position);
            size--;
            return result;
        }
        else {
            return null;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public void shiftRight(int position){
        for(int i = size;i>position;i--){
            elements[i] = elements[i-1];
        }
    }

    public void shiftLeft(int position){
        for(int i = position;i<size;i++){
            elements[i] = elements[i+1];
        }
    }


    public void extendCapacity(int minCapacity){
        int capacity = DEFAULT_INITIAL_SIZE;
        while(capacity <= minCapacity){
            capacity = capacity + capacity >> 1;
        }

        DEFAULT_INITIAL_SIZE = capacity;
    }

}
