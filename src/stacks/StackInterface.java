package stacks;

public interface StackInterface<E> {

  void push(E element);

  E pop();

  E peek();

  boolean isEmpty();

  int size();
  
}
