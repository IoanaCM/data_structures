package bsts;

public class SequentialBST<E extends Comparable<E>> implements BST<E> {

  private BSTNode<E> root;

  public SequentialBST(){
    root = null;
  }

  @Override
  public boolean add(E element) {
    BSTNode<E> newNode = new BSTNode<>(element);
    if (root == null) {
      root = newNode;
      return true;
    }
    BSTNode<E> prev = root;
    BSTNode<E> curr = root;
    while (curr != null) {
      if (curr.element == element) return false;
      if (curr.element.compareTo(element) < 0) {
        prev = curr;
        curr = curr.right;
      } else {
        prev = curr;
        curr = curr.left;
      }
    }
    curr = newNode;
    return true;
  }

  @Override
  public boolean remove(E element) {
    BSTNode<E> curr = root;
    BSTNode<E> prev = root;
    while (curr != null) {
      if (curr.element.compareTo(element) == 0) {
        if (prev == null) {
          //remove the root
          root = deleteNode(root);
        } else if (prev.element.compareTo(element) > 0) {
          prev.left = deleteNode(prev.left);
        } else {
          prev.right = deleteNode(prev.right);
        }
        return true;
      } else {
        if (curr.element.compareTo(element) < 0) {
          curr = curr.right;
        } else {
          curr = curr.left;
        }
      }
    }
    return false;
  }

  public BSTNode<E> deleteNode(BSTNode<E> node){
      if(node.right!=null) {
        BSTNode<E> replacementNode = findMinNode(node.right);
        replacementNode.right = removeMinNode(node.right);
        replacementNode.left = node.left;
        return replacementNode;
      }else if (node.left!=null){
        BSTNode<E> replacementNode = findMaxNode(node.left);
        replacementNode.right = node.right;
        replacementNode.left = removeMaxNode(node.left);;
        return replacementNode;
      }
      return null; //leaf node
    }

    private BSTNode<E> findMinNode(BSTNode<E> subtree) {
      BSTNode<E> curr = subtree;
      while (curr.left != null) {
        curr = curr.left;
      }
      return curr;
    }

    private BSTNode<E> findMaxNode(BSTNode<E> subtree) {
      BSTNode<E> curr = subtree;
      while (curr.right != null) {
        curr = curr.right;
      }
      return curr;
    }

    private BSTNode<E> removeMinNode(BSTNode<E> subtree) {
      assert subtree!=null;

      BSTNode<E> curr = subtree;
      BSTNode<E> prev = null;
      while (curr.left != null) {
        prev = curr;
        curr = curr.left;
      }
      if (prev == null) {
        return curr.right;
      } else {
        prev.left = curr.right;
        return subtree;
      }
    }

    private BSTNode<E> removeMaxNode(BSTNode<E> subtree) {
      assert subtree!=null;

      BSTNode<E> curr = subtree;
      BSTNode<E> prev = null;
      while (curr.right != null) {
        prev = curr;
        curr = curr.right;
      }
      if (prev == null) {
        return curr.left;
      } else {
        prev.right = curr.left;
        return subtree;
      }
    }


  @Override
  public boolean contains(E element) {
    BSTNode<E> curr = root;
    while (curr != null) {
      if (curr.element.compareTo(element) == 0) {
        return true;
      } else {
        if (curr.element.compareTo(element) < 0) {
          curr = curr.right;
        } else {
          curr = curr.left;
        }
      }
    }
    return false;
  }

  public class BSTNode<E> {
    private E element;
    private BSTNode<E> left;
    private BSTNode<E> right;

    public BSTNode(E element) {
      this.element = element;
      left = null;
      right = null;
    }
  }
}
