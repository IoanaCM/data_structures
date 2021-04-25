package heaps;

//import static com.google.common.base.Preconditions.checkArgument;
//import static com.google.common.base.Preconditions.checkNotNull;

public class MaxHeap<E extends Comparable> {

    private static final int DEFAULT_CAPACITY = 100;
    private E[] heap;
    private int nextIndex;

    public MaxHeap() {
        this(MaxHeap.DEFAULT_CAPACITY);
    }

    public MaxHeap(int capacity) {
      //  checkArgument(capacity > 0);
        heap = (E[]) new Comparable[capacity];
        nextIndex = 0;
    }

    public void push(E element) {
  //      checkNotNull(element);
    //    checkArgument(nextIndex < heap.length - 1, "The heap is full");

        // Insert the element in the next available position
        int cursor = nextIndex++;
        heap[cursor] = element;

        // Sift up until the heap property is re-established
        while ((cursor != 0) &&
                (heap[cursor].compareTo(heap[parent(cursor)]) > 0)) {
            swap(cursor, parent(cursor));
            cursor = parent(cursor);
        }
    }

    public E pollMax() {
        if (nextIndex == 0) {
            //The heap is empty
            return null;
        }

        // Swap the maximum with the last element
        swap(0, --nextIndex);
        // Re-establish heap property by sifting the root down
        if (nextIndex != 0) {
            siftdown(0);
        }

        return heap[nextIndex];
    }

    public E peekMax() {
        if (isEmpty()) {
            return null;
        }
        return heap[0];
    }

    private void siftdown(int index) {
      //  checkArgument(index >= 0 && (index < nextIndex));

        while (!isLeaf(index)) {
            int maxChild = leftchild(index);
            if ((maxChild < (nextIndex - 1)) && (heap[maxChild].compareTo(heap[maxChild + 1]) < 0)) {
                // the left child is smaller than the right child -> maxChild should point to the right child
                maxChild++;
            }
            if (heap[index].compareTo(heap[maxChild]) >= 0) {
                return;
            }
            swap(index, maxChild);
            index = maxChild;  // Move to the lower level
        }
    }

    private void swap(int i, int j) {
        E temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public int size() {
        return nextIndex;
    }

    public boolean isEmpty() {
        return nextIndex == 0;
    }

    private boolean isLeaf(int index) {
        return (index >= nextIndex / 2) && (index < nextIndex);
    }

    private int leftchild(int index) {
      //  checkArgument(index < nextIndex / 2, "The element at index " + index + " has no left child");
        return 2 * index + 1;
    }

    private int rightchild(int index) {
      //  checkArgument(index < (nextIndex - 1) / 2,
          //      "The element at index " + index + " has no right child");
        return 2 * index + 2;
    }

    private int parent(int index) {
        //checkArgument(index > 0, "The element at index " + index + " has no parent");
        return (index - 1) / 2;
    }

    //Added to simplify the implementation of heapsort
    protected E[] getArray() {
        return heap.clone();
    }
}