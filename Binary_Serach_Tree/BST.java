
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.NoSuchElementException;
/**
 * Your implementation of a BST.
 *
 * @author Sunho Park
 * @version 1.0
 * @userid spark901
 * @GTID 903795870
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be added to tree");
        }
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("Null data cannot be added to tree");
            }
            add(element);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be added to the tree.");
        }
        root = addHelp(root, data);
    }

    /**
     * A private recursive helper method for the add method
     *
     * @param curr the current node traversed into the tree
     * @param data the data to add
     * @return current node whose left and right are set with data
     */
    private BSTNode<T> addHelp(BSTNode<T> curr, T data) {
        if (curr == null) {
            BSTNode<T> node = new BSTNode<T>(data);
            size++;
            return node;
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addHelp(curr.getRight(), data));
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addHelp(curr.getLeft(), data));
        }
        return curr;
    }
    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be added to the tree.");
        }
        BSTNode<T> dummy = new BSTNode<T>(null);
        root = removeHelp(data, root, dummy);
        size--;
        return dummy.getData();
    }

    /**
     * A private recursive helper method for the remove method
     *
     * @param curr the current node traversed into the tree
     * @param data the data to add
     * @param dummy the node that is to be deleted
     * @return the current node
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    private BSTNode<T> removeHelp(T data, BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("Data cannot be found in the tree.");
        }
        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeHelp(data, curr.getLeft(), dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight((removeHelp(data, curr.getRight(), dummy)));
        } else {
            dummy.setData(curr.getData());
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else {
                BSTNode<T> dummy2 = new BSTNode<T>(null);
                curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        return curr;
    }

    /**
     * A private recursive helper method for removing successor
     *
     * @param curr the current node traversed into the tree
     * @param dummy the node that is to be deleted
     * @return the current node
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy));
        }
        return curr;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be added to the tree.");
        }
        return getHelp(root, data);
    }

    /**
     * A private recursive helper method for the get method
     *
     * @param curr the current node traversed into the tree
     * @param data the data to get
     * @return the data of the current node
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    private T getHelp(BSTNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Data cannot be found in the tree.");
        } else {
            if (curr.getData() == data) {
                return curr.getData();
            } else if (data.compareTo(curr.getData()) > 0) {
                return getHelp(curr.getRight(), data);
            } else if (data.compareTo(curr.getData()) < 0) {
                return getHelp(curr.getLeft(), data);
            }
        }
        return curr.getData();
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be added to the tree.");
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
    private boolean containsHelp(BSTNode<T> curr, T data) {
        if (curr == null) {
            return false;
        } else if (curr.getData().equals(data)) {
            return true;
        } else if (data.compareTo(curr.getData()) > 0) {
            return containsHelp(curr.getRight(), data);
        } else {
            return containsHelp(curr.getLeft(), data);
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<T>();
        return preorderHelp(root, list);
    }

    /**
     * A private recursive helper method for the preorder method
     *
     * @param curr the current node traversed into the tree
     * @param list the list to record data
     * @return arraylist in sequence of preorder traversal of nodes
     */

    private List<T> preorderHelp(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return list;
        } else {
            list.add(curr.getData());
            preorderHelp(curr.getLeft(), list);
            preorderHelp(curr.getRight(), list);
        }
        return list;
    }


    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<T>();
        return inorderHelp(root, list);
    }

    /**
     * A private recursive helper method for the inorder method
     *
     * @param curr the current node traversed into the tree
     * @param list the list to record data
     * @return arraylist in sequence of inorder traversal of nodes
     */

    private List<T> inorderHelp(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return list;
        } else {
            inorderHelp(curr.getLeft(), list);
            list.add(curr.getData());
            inorderHelp(curr.getRight(), list);
        }
        return list;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<T>();
        return postorderHelp(root, list);
    }

    /**
     * A private recursive helper method for the postorder method
     *
     * @param curr the current node traversed into the tree
     * @param list the list to record data
     * @return arraylist in sequence of preorder traversal of nodes
     */
    private List<T> postorderHelp(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return list;
        } else {
            postorderHelp(curr.getLeft(), list);
            postorderHelp(curr.getRight(), list);
            list.add(curr.getData());
        }
        return list;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> traversal = new ArrayList<T>();
        LinkedList<BSTNode<T>> queue = new LinkedList<>();
        queue.addLast(root);
        while (!queue.isEmpty()) {
            BSTNode<T> node = queue.removeFirst();
            if (node != null) {
                traversal.add(node.getData());
                queue.addLast(node.getLeft());
                queue.addLast(node.getRight());
            }
        }
        return traversal;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelp(root);
    }

    /**
     * A private recursive helper method for the height method
     *
     * @param curr the current node traversed into the tree
     * @return the height of the tree
     */
    private int heightHelp(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            return 1 + Math.max(heightHelp(curr.getLeft()),
                    heightHelp(curr.getRight()));
        }
    }
    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Removes all elements strictly greater than the passed in data.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * Using ?????
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * pruneGreaterThan(27) should remove 37, 40, 50, 75. Below is the resulting BST
     *             25
     *            /
     *          12
     *         /  \
     *        10  15
     *           /
     *          13
     *
     * Should have a running time of O(n)??? O(log(n)) for balanced tree
     *
     * @throws java.lang.IllegalArgumentException if data is null
     * @param data the threshold data. Elements greater than data should be removed
     * @param tree the root of the tree to prune nodes from
     * @param <T> the generic typing of the data in the BST
     * @return the root of the tree with all elements greater than data removed
     */
    public static <T extends Comparable<? super T>> BSTNode<T> pruneGreaterThan(BSTNode<T> tree, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be rmoved.");
        }
        return pruneGreaterThanHelper(tree, data);
    }

    /**
     * A private recursive helper method for the pruneGreaterThan
     *
     * @param curr - current node that is checked
     * @param data - the given data input
     * @param <T> - the generic typing of the data in the BST
     * @return the updated tree with all elements greater than given data removed
     */
    private static <T extends Comparable<? super T>> BSTNode<T> pruneGreaterThanHelper(BSTNode<T> curr, T data) {
        if (curr == null) {
            return null;
        }
        if (curr.getData().compareTo(data) > 0) {
            return pruneGreaterThanHelper(curr.getLeft(), data);
        }
        curr.setRight(pruneGreaterThanHelper(curr.getRight(), data));
        return curr;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
