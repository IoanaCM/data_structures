package bsts;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FineBST<E extends Comparable<E>> implements BST<E> {

  private LockableBSTNode<E> root;
  private Lock rootLock = new ReentrantLock();

  @Override
  public boolean add(E element) {
    LockableBSTNode<E> newNode = new LockableBSTNode<>(element);
    rootLock.lock();
    if (root == null) {
      root = newNode;
      rootLock.unlock();
      return true;
    }
    LockableBSTNode<E> prev = null;
    LockableBSTNode<E> curr = root;
    curr.lock();
    rootLock.unlock();
    int compare = 0;
    while (true) {
      prev = curr;

      compare = curr.element.compareTo(element);
      if (compare == 0) {
        curr.unlock();
        return false;
      } else {
        curr = (compare > 0) ? curr.left : curr.right;
      }

      if (curr == null) {
        break;
      } else {
        curr.lock();
        prev.unlock();
      }
    }
    if (compare > 0) {
      prev.left = new LockableBSTNode<>(element);
    } else {
      prev.right = new LockableBSTNode<>(element);
    }
    prev.unlock();
    return true;
  }

  @Override
  public boolean remove(E element) {
    LockableBSTNode<E> newNode = new LockableBSTNode<>(element);
    rootLock.lock();
    if (root == null) {
      rootLock.unlock();
      return false;
    }
    LockableBSTNode<E> prev = root;
    LockableBSTNode<E> curr = root;
    curr.lock();

    int compare = curr.element.compareTo(element);
    if (compare == 0) {
      LockableBSTNode<E> replacement = findReplacement(curr);
      if (replacement != null) {
        replacement.left = curr.left;
        replacement.right = curr.right;
      }

      root = replacement;
      curr.unlock();
      rootLock.unlock();
      return true;
    }

    curr.lock();
    rootLock.unlock();

    int prevComp = compare;
    while (true) {
      compare = curr.element.compareTo(element);
      if (compare == 0) {
        LockableBSTNode<E> replacement = findReplacement(curr);

        if (prevComp > 0) {
          prev.left = replacement;
        } else {
          prev.right = replacement;
        }

        if (replacement != null) {
          replacement.left = curr.left;
          replacement.right = curr.right;
        }
        curr.unlock();
        prev.unlock();
        return true;
      } else {
        prev.unlock();
        prev = curr;
        curr = (compare > 0) ? curr.left : curr.right;
        prevComp = compare;
      }

      if (curr == null) {
        break;
      } else {
        curr.lock();
      }
    }
    prev.unlock();
    return true;
  }

  private LockableBSTNode<E> findReplacement(LockableBSTNode<E> node) {
    if (node.left != null) {
      return findMaxInLeftSubtree(node);
    } else if (node.right != null) {
      return findMinInRightSubtree(node);
    } else {
      return null;
    }
  }

  private LockableBSTNode<E> findMaxInLeftSubtree(LockableBSTNode<E> subtree) {
    LockableBSTNode<E> prev = subtree;
    LockableBSTNode<E> curr = subtree.left;
    curr.lock();

    while (curr.right != null) {
      if (prev != subtree) {
        prev.unlock();
      }
      prev = curr;
      curr = curr.right;
      curr.lock();
    }

    if (curr.left != null) {
      curr.left.lock();
    }
    if (prev == subtree) {
      prev.left = curr.left;
    } else {
      prev.right = curr.left;
      prev.unlock();
    }
    if (curr.left != null) {
      curr.left.unlock();
    }
    curr.unlock();
    return curr;
  }

  private LockableBSTNode<E> findMinInRightSubtree(LockableBSTNode<E> subtree) {
    LockableBSTNode<E> prev = subtree;
    LockableBSTNode<E> curr = subtree.right;
    curr.lock();

    while (curr.left != null) {
      if (prev != subtree) {
        prev.unlock();
      }
      prev = curr;
      curr = curr.left;
      curr.lock();
    }

    if (curr.right != null) {
      curr.right.lock();
    }
    if (prev == subtree) {
      prev.right = curr.right;
    } else {
      prev.left = curr.right;
      prev.unlock();
    }
    if (curr.right != null) {
      curr.right.unlock();
    }
    curr.unlock();
    return curr;
  }

  @Override
  public boolean contains(E element) {
    LockableBSTNode<E> curr = root;
    LockableBSTNode<E> prev = null;

    rootLock.lock();

    if (root == null) {
      rootLock.unlock();
      return false;
    } else {
      curr.lock();
      rootLock.unlock();
      while (curr != null) {
        prev = curr;

        if (curr.element.compareTo(element) > 0) {
          curr = curr.left;
        } else {
          if (curr.element.compareTo(element) < 0) {
            curr = curr.right;
          } else {
            curr.unlock();
            return true;
          }
          if (curr == null) {
            break;
          } else {
            curr.lock();
            prev.unlock();
          }
        }
      }
    }
    prev.unlock();
    return false;
  }

  private LockableBSTNode<E> leftOrRight(LockableBSTNode<E> node, E element) {
    if (node.element.compareTo(element) > 0) {
      return node.left;
    } else if (node.element.compareTo(element) < 0) {
      return node.right;
    } else {
      return null;
    }
  }

  public class LockableBSTNode<E> {
    private Lock lock = new ReentrantLock();
    private E element;
    private LockableBSTNode<E> left;
    private LockableBSTNode<E> right;

    public LockableBSTNode(E element) {
      this.element = element;
      left = null;
      right = null;
    }

    public void lock() {
      this.lock.lock();
    }

    public void unlock() {
      this.lock.unlock();
    }
  }
}
