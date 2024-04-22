
import java.util.Collection;
import java.util.NoSuchElementException;
/**
 * Your implementation of an AVL Tree.
 *
 * @author Sunho Park
 * @userid spark901
 * @GTID 903795870
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Null collection cannot be constructed.");
        }
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("Null element cannot be constructed to AVL.");
            } else {
                add(element);
            }
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be added.");
        }
        root = addHelp(root, data);
    }

    /**
     * A private recursive helper method for the add method
     *
     * @param curr the current node traversed into the tree
     * @param data the data to add
     * @return node to add and other nodes to be go back to its place as recursion works
     */
    private AVLNode<T> addHelp(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new AVLNode<T>(data);
        }
        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addHelp(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addHelp(curr.getRight(), data));
        }
        update(curr);
        return balance(curr);
    }

    /**
     * private helper method to update Node's height and balance factor
     *
     * @param node the node passed in to update
     */
    private void update(AVLNode<T> node) {
        int leftH = -1;
        int rightH = -1;
        if (node.getLeft() != null) {
            leftH = node.getLeft().getHeight();
        }
        if (node.getRight() != null) {
            rightH = node.getRight().getHeight();
        }

        int currH = Math.max(leftH, rightH) + 1;
        node.setHeight(currH);
        node.setBalanceFactor(leftH - rightH);
    }

    /**
     * private helper method to balance the tree
     *
     * @param root the root of the subtree to balance
     * @return root that is a new root of the subtree after balancing
     */
    private AVLNode<T> balance(AVLNode<T> root) {
        if (root.getBalanceFactor() > 1) {
            if (root.getLeft().getBalanceFactor() < 0) {
                root.setLeft(leftR(root.getLeft()));
                root = rightR(root);
            } else {
                root = rightR(root);
            }
        } else if (root.getBalanceFactor() < -1) {
            if (root.getRight().getBalanceFactor() > 0) {
                root.setRight(rightR(root.getRight()));
                root = leftR(root);
            } else {
                root = leftR(root);
            }
        }
        return root;
    }

    /**
     * private helper method to right rotate
     *
     * @param node the current node before the update
     * @return the root node of the updated tree
     */

    private AVLNode<T> rightR(AVLNode<T> node) {
        AVLNode<T> newNode = node.getLeft();
        node.setLeft(newNode.getRight());
        newNode.setRight(node);
        update(node);
        update(newNode);
        return newNode;
    }

    /**
     * private helper method to left rotate
     *
     * @param node the current node before the update
     * @return the root node of the updated tree
     */

    private AVLNode<T> leftR(AVLNode<T> node) {
        AVLNode<T> newNode = node.getRight();
        node.setRight(newNode.getLeft());
        newNode.setLeft(node);
        update(node);
        update(newNode);
        return newNode;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the predecessor to replace the data,
     * not the successor. As a reminder, rotations can occur after removing
     * the predecessor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("There can't be null data in the tree");
        }
        AVLNode<T> temp = new AVLNode<T>(null);
        root = removeHelp(root, data, temp);
        return temp.getData();
    }

    /**
     * helper method to remove
     *
     * @param curr  current node traversing
     * @param data  the data to remove
     * @param temp dummy node to reference thr removed data
     * @return the new root of the subtree
     */
    private AVLNode<T> removeHelp(AVLNode<T> curr, T data, AVLNode<T> temp) {
        if (curr == null) {
            throw new NoSuchElementException("There is no data found from the tree.");
        }
        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeHelp(curr.getLeft(), data, temp));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight((removeHelp(curr.getRight(), data, temp)));
        } else {
            temp.setData(curr.getData());
            size--;
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() != null && curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getLeft() == null && curr.getRight() != null) {
                return curr.getRight();
            } else {
                AVLNode<T> temp2 = new AVLNode<T>(null);
                curr.setLeft(removePredecessor(curr.getLeft(), temp2));
                curr.setData(temp2.getData());
            }
        }
        update(curr);
        balance(curr);
        return curr;
    }

    /**
     * helper method to remove in predecessor mode
     *
     * @param curr  current node traversing
     * @param temp dummy node to reference thr removed data
     * @return the new root of the subtree
     */
    private AVLNode<T> removePredecessor(AVLNode<T> curr, AVLNode<T> temp) {
        if (curr.getRight() != null) {
            curr.setRight(removePredecessor(curr.getRight(), temp));
            update(curr);
            if (Math.abs(curr.getBalanceFactor()) > 1) {
                curr = balance(curr);
            }
        } else {
            temp.setData(curr.getData());
            return curr.getLeft();
        }
        return curr;
    }
    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be searched");
        }
        if (size == 0) {
            throw new NoSuchElementException("There is no data to be found");
        }
        T target = getHelp(root, data);
        if (target == null) {
            throw new NoSuchElementException("The target data isn't in the tree.");
        }
        return target;
    }
    /**
     * A private recursive helper method for the get method
     *
     * @param curr the current node traversed into the tree
     * @param data the data to get
     * @return the data of the current node
     */
    private T getHelp(AVLNode<T> curr, T data) {
        if (curr == null) {
            return null;
        }
        if (curr.getData().compareTo(data) < 0) {
            return getHelp(curr.getRight(), data);
        } else if (curr.getData().compareTo(data) > 0) {
            return getHelp(curr.getLeft(), data);
        }
        return curr.getData();
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be searched");
        }
        if (size == 0) {
            return false;
        }
        return containsHelp(root, data);
    }

    /**
     * A private recursive helper method for the contains method
     *
     * @param curr the current node traversed into the tree
     * @param data the data of our target node
     * @return whether the data of the parameter passed in inside the tree
     */
    private boolean containsHelp(AVLNode<T> curr, T data) {
        if (curr == null) {
            return false;
        }
        if (curr.getData().compareTo(data) > 0) {
            return containsHelp(curr.getLeft(), data);
        } else if (curr.getData().compareTo(data) < 0) {
            return containsHelp(curr.getRight(), data);
        }
        return true;
    }

    /**
     * Finds and retrieves the median data of the passed in AVL.
     *
     * This method will not need to traverse the entire tree to
     * function properly, so you should only traverse enough branches of the tree
     * necessary to find the median data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     *
     * findMedian() should return 40
     *
     * @throws NoSuchElementException if the tree is empty or contains an even number of data
     * @return the median data of the AVL
     */
    public T findMedian() {
        if (size == 0) {
            throw new NoSuchElementException("The tree is empty");
        } else if (size % 2 == 0) {
            throw new NoSuchElementException("Three tree has even number of data");
        } else {
            int[] count = new int[1];
            return findMedianHelper(root, count, (size + 1) / 2).getData();
        }
    }
    /**
     * A private recursive helper method for the findMedian method
     *
     * @param node the current node traversed into the tree
     * @param count the data of our target node
     * @param median the median data
     *
     * @return the median
     */
    private AVLNode<T> findMedianHelper(AVLNode<T> node, int[] count, int median) {
        if (node == null) {
            return null;
        }
        AVLNode<T> left = findMedianHelper(node.getLeft(), count, median);
        if (left != null) {
            return left;
        }

        count[0]++;
        if (count[0] == median) {
            return node;
        }

        return findMedianHelper(node.getRight(), count, median);
    }


    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}