/*
 * Name: Jay Wu
 * PID: A19135498
 */

import java.util.NoSuchElementException;
import java.util.Arrays;

/**
 * Array-based d-ary heap of Float values that can act as a min-heap
 * or a max-heap. The root is at index 0, the children of index j are at
 * d*j+1 .. d*j+d, and the parent of index j is at (j-1)/d
 */
public class dHeap {

    private static final int DEFAULT_CAPACITY = 10;
    private static final int BINARY_HEAP = 2;
    private static final int GROWTH_FACTOR = 2;

    private Float[] heap;
    private int d;
    private int nelems;
    private boolean isMaxHeap;

    /**
     * Creates a binary max-heap with an initial capacity of 10.
     */
    public dHeap() {
        this(BINARY_HEAP, DEFAULT_CAPACITY, true);
    }

    /**
     * Creates a binary max-heap with an initial capacity of 10.
     * @param heapSize initial capacity of heap
     */
    public dHeap(int heapSize) {
        this(BINARY_HEAP, heapSize, true);
    }

    /**
     * Creates a d-ary heap with the given branching factor and capacity.
     *
     * @param d branching factor
     * @param heapSize initial capacity of the heap
     * @param isMaxHeap true for a max-heap, false for a min-heap
     * @throws IllegalArgumentException if d is less than 1
     */
    public dHeap(int d, int heapSize, boolean isMaxHeap) {
        if (d < 1) {
            throw new IllegalArgumentException();
        }
        this.d = d;
        this.isMaxHeap = isMaxHeap;
        this.heap = new Float[heapSize];
        this.nelems = 0;
    }

    /**
     * Returns the number of elements stored in the heap.
     *
     * @return number of elements
     */
    public int size() {
        return nelems;
    }

    /**
     * Adds the given value to the heap.
     *
     * @param value value to add
     * @throws NullPointerException if value is null
     */
    public void add(Float value) {
        if (value == null) {
            throw new NullPointerException();
        }
        if (nelems == heap.length) {
            resize();
        }
        heap[nelems] = value;
        nelems++;
        bubbleUp(nelems - 1);
    }

    /**
     * Removes and returns the root element.
     *
     * @return the root element
     * @throws NoSuchElementException if the heap is empty
     */
    public Float remove() {
        if (nelems == 0) {
            throw new NoSuchElementException();
        }
        Float root = heap[0];
        nelems--;
        heap[0] = heap[nelems];
        heap[nelems] = null;
        if (nelems > 0) {
            trickleDown(0);
        }
        return root;
    }

    /**
     * Removes all elements from the heap.
     */
    public void clear() {
        Arrays.fill(heap, 0, nelems, null);
        nelems = 0;
    }

    /**
     * Returns the root element without removing it.
     *
     * @return the root element
     * @throws NoSuchElementException if the heap is empty
     */
    public Float element() {
        if (nelems == 0) {
            throw new NoSuchElementException();
        }
        return heap[0];
    }

    /**
     * Returns the parent index of the given index.
     *
     * @param index child index
     * @return parent index
     */
    private int parent(int index) {
        return (index - 1) / d;
    }

    /**
     * Moves the element at the given index up until the heap property holds.
     *
     * @param index index of the element to move up
     */
    private void bubbleUp(int index) {
        Float value = heap[index];
        while (index > 0) {
            int parentIndex = parent(index);
            if (!hasHigherPriority(value, heap[parentIndex])) {
                break;
            }
            heap[index] = heap[parentIndex];
            index = parentIndex;
        }
        heap[index] = value;
    }

    /**
     * Moves the element at the given index down until the heap property holds.
     *
     * @param index index of the element to move down
     */
    private void trickleDown(int index) {
        Float value = heap[index];
        int firstChild = d * index + 1;
        while (firstChild < nelems) {
            int bestChild = firstChild;
            int lastChild = Math.min(firstChild + d - 1, nelems - 1);
            for (int child = firstChild + 1; child <= lastChild; child++) {
                if (hasHigherPriority(heap[child], heap[bestChild])) {
                    bestChild = child;
                }
            }
            if (!hasHigherPriority(heap[bestChild], value)) {
                break;
            }
            heap[index] = heap[bestChild];
            index = bestChild;
            firstChild = d * index + 1;
        }
        heap[index] = value;
    }

    /**
     * Returns true if the first value belongs above the second in this heap:
     * larger wins in a max-heap, smaller wins in a min-heap.
     *
     * @param first first value
     * @param second second value
     * @return whether first has strictly higher priority than second
     */
    private boolean hasHigherPriority(Float first, Float second) {
        int comparison = first.compareTo(second);
        return isMaxHeap ? comparison > 0 : comparison < 0;
    }

    /**
     * Doubles the capacity of the backing array.
     */
    private void resize() {
        heap = Arrays.copyOf(heap, Math.max(1, heap.length * GROWTH_FACTOR));
    }
}
