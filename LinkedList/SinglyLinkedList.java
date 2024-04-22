package HW01;

import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular SinglyLinkedList with a tail pointer.
 *
 * @author SUNHO PARK
 * @version 1.0
 * @userid spark901
 * @GTID 903795870
 */
public class SinglyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private SinglyLinkedListNode<T> head;
    private SinglyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index to add the new element
     * @param data  the data to add
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index cannot be below 0 or above size.");
        }
        if (data == null) {
            throw new IllegalArgumentException("The null data cannot be added.");
        }

        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            int count = 0;
            SinglyLinkedListNode<T> curr = head;
            SinglyLinkedListNode<T> temp = null;
            while (count < index) {
                temp = curr;
                curr = curr.getNext();
                count++;
            }
            SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<T>(data);
            newNode.setNext(curr);
            temp.setNext(newNode);
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The null data cannot be added.");
        }
        SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<>(data);

        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setNext(head);
            head = newNode;
        }
        size++;

    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The null data cannot be added");
        }

        SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<>(data);
        if (size == 0) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
        size++;
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and O(n) for all other
     * cases.
     *
     * @param index the index of the element to remove
     * @return the data that was removed
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        T remove;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index should be above 0 or less than size.");
        }
        if (index == 0) {
            removeFromFront();
        }
        if (index == size - 1) {
            removeFromBack();
        }

        int count = 0;
        SinglyLinkedListNode<T> curr = head;
        SinglyLinkedListNode<T> prev = null;
        while (count < index) {
            prev = curr;
            curr = curr.getNext();
            count++;
        }
        SinglyLinkedListNode<T> temp = curr;
        remove = curr.getData();
        curr = prev;
        curr.setNext(temp.getNext());
        size--;
        return remove;
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty.");
        }
        T remove = head.getData();
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head = head.getNext();
        }
        size--;
        return remove;
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty.");
        }

        T remove = tail.getData();
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            SinglyLinkedListNode<T> curr = head;
            while (curr.getNext() != tail) {
                curr = curr.getNext();
            }
            tail = curr;
            tail.setNext(null);
        }
        size--;
        return remove;
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index should be above 0 or less than size.");
        }
        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else {
            int count = 0;
            SinglyLinkedListNode<T> curr = head;
            while (count < index) {
                curr = curr.getNext();
                count++;
            }
            return curr.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return head == null;    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The null data cannot be passed in.");
        }
        SinglyLinkedListNode<T> curr = head;
        SinglyLinkedListNode<T> temp = null;
        while (curr != null) {
            // if the data found
            if (curr.getData() == data) {
                // temp is current node
                temp = curr;
            }
            // iterate
            curr = curr.getNext();
        }

        if (temp == null) {
            throw new NoSuchElementException("There is no such data found.");
            // if data found, and the data is at the last index, remove the temp
        } else {
            curr = head;
            while (curr.getNext() != temp) {
                curr = curr.getNext();
            }
            if (temp.getNext() == null) {
                curr.setNext(null);
            } else {
                curr.setNext(temp.getNext());
            }
            size--;
        }
        return temp.getData();
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        int count = 0;
        T[] array = (T[]) new Object[size];
        SinglyLinkedListNode<T> curr = head;
        while (curr != null) {
            array[count] = curr.getData();
            curr = curr.getNext();
            count++;
        }
        return array;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public SinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public SinglyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
