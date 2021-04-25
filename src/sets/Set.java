package sets;

public interface Set<E> {

  boolean add(E item);

  boolean remove(E item);

  boolean contains(E item);

  int size();
}
