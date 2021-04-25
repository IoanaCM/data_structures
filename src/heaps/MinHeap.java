package heaps;

public class MinHeap<E extends Comparable> {

  private static final int DEFAULT_MAX_CAPACITY = 100;
  private int nextIndex;
  private E[] contents;

  public MinHeap(int size) {
    nextIndex = 0;
    contents = (E[]) new Object[size];
  }

  public MinHeap(){
      this(DEFAULT_MAX_CAPACITY);
  }

  public E peekMin(){
      if(isEmpty()){
          return null;
      }
      return contents[0];
  }

  public E pollMin(){
      E result = contents[0];
      contents[0] = contents[nextIndex--];
      contents[nextIndex] = null;
      siftDown(0);
      return result;
  }

  public void siftDown(int index){
      while(!isLeaf(index)){
          int child = leftChild(index);
          if(child<(nextIndex - 1) && contents[child].compareTo(contents[child+1])>0){
              child++;
          }
          if(contents[index].compareTo(contents[child])<=0){
              return;
          }
          swap(index,child);
          index = child;
      }
  }

  public boolean isEmpty(){
      return nextIndex==0;
  }

  public boolean isLeaf(int index){
      return index<nextIndex && index>= nextIndex/2;
  }

  public int leftChild(int index){
      return 2*index + 1;
  }

  public int prev(int index){
      return (index-1)/2;
  }

  public void swap(int i, int j){
      E tmp = contents[i];
      contents[i] = contents[j];
      contents[j] = tmp;
  }

  public int size(){
      return nextIndex;
  }

}
