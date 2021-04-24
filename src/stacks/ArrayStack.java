package stacks;

public class ArrayStack<E> implements StackInterface<E> {

  private static final int MAX_VALUE_SIZE = 100;

  private E[] elements;
  private int top;

  public ArrayStack(int size) {
    elements = (E[]) new Object[size];
    top = -1;
  }

  public ArrayStack() {
    this(MAX_VALUE_SIZE);
  }

  @Override
  public void push(E element) {
    if (top >= MAX_VALUE_SIZE - 1) {
      throw new StackOverflowError();
    }
    elements[top++] = element;
  }

  @Override
  public E pop() {
    if (isEmpty()) {
      return null;
    }
    E result = elements[top];
    elements[top] = null;
    top--;
    return result;
  }

  @Override
  public E peek() {
    if (isEmpty()) return null;
    return elements[top];
  }

  @Override
  public boolean isEmpty() {
    return top == -1;
  }

  @Override
  public int size() {
    return top + 1;
  }
}
