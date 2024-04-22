
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
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
public class Sorting {

    /**
     * private helper method for swapping elements
     *
     * @param arr  array that has items to swap
     * @param index1 first index
     * @param index2 second index
     * @param <T>  data type to sort
     */
    private static <T> void swap(T[] arr, int index1, int index2) {
        T temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Null array or null comparator "
                    + "cannot be sorted or sort the array.");
        }

        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            swap(arr, i, minIndex);
        }

    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Null array or null comparator "
                    + "cannot be sorted or sort the array.");
        }

        for (int n = 1; n < arr.length; n++) {
            int i = n;
            while (i != 0 && comparator.compare(arr[i - 1], arr[i]) > 0) {
                swap(arr, i - 1, i);
                i--;
            }
        }
    }

    /**
     * Implement bubble sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for bubble sort. You
     * MUST implement bubble sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Null array or null comparator "
                    + "cannot be sorted or sort the array.");
        }

        int n = arr.length;
        int lastSwapIndex = n;

        while (lastSwapIndex != 0) {
            lastSwapIndex = 0;
            for (int i = 1; i < n; i++) {
                if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                    swap(arr, i - 1, i);
                    lastSwapIndex = i;
                }
            }
            n = lastSwapIndex;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Null array or null comparator "
                    + "cannot be sorted or sort the array.");
        }

        if (arr.length <= 1) {
            return;
        }

        int len = arr.length;
        int midIdx = len / 2;

        //sub array
        T[] leftArr = (T[]) (new Object[midIdx]);
        for (int k = 0; k < midIdx; k++) {
            leftArr[k] = arr[k];
        }
        T[] rightArr = (T[]) (new Object[len - midIdx]);
        for (int k = midIdx; k < len; k++) {
            rightArr[k - midIdx] = arr[k];
        }

        mergeSort(leftArr, comparator);
        mergeSort(rightArr, comparator);

        int i = 0;  // left
        int j = 0;  // right
        while (i < midIdx && j < len - midIdx) {
            if (comparator.compare(leftArr[i], rightArr[j]) <= 0) {
                arr[i + j] = leftArr[i];
                i++;
            } else {
                arr[i + j] = rightArr[j];
                j++;
            }
        }

        //when right becomes empty
        while (i < leftArr.length) {
            arr[i + j] = leftArr[i];
            i++;
        }
        //when left becomes empty
        while (j < rightArr.length) {
            arr[i + j] = rightArr[j];
            j++;
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Null array cannot be sorted.");
        }
        LinkedList<Integer>[] buckets = (LinkedList<Integer>[]) new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<>();
        }
        int div = 1;
        boolean loop = true;
        while (loop) {
            loop = false;
            for (int num : arr) {
                int bucket = num / div;
                if (bucket / 10 != 0) {
                    loop = true;
                }
                buckets[bucket % 10 + 9].add(num);
            }
            int arrIdx = 0; // array index
            for (LinkedList<Integer> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    Integer data = bucket.removeFirst();
                    arr[arrIdx++] = data;
                }
            }
            div *= 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */

    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be in the array.");
        }
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(data);
        int[] result = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            result[i] = minHeap.remove();
        }
        return result;
    }
}