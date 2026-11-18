/*
 * Name: Jay Wu
 * PID: A19135498
 */

/**
 * Priority queue of Float values where the smallest value is at the head.
 * Adapter over a 4-ary min-dHeap.
 */
public class MyPriorityQueue {
    private static final int BRANCHING_FACTOR = 4;
    private dHeap pQueue;
    /**
     * Creates an empty priority queue backed by a 4-ary min-heap.
     *
     * @param initialSize initial capacity of underlying heap
     */
    public MyPriorityQueue(int initialSize) {
        pQueue = new dHeap(BRANCHING_FACTOR, initialSize, false);
    }
    /**
     * Inserts the given value into the queue.
     *
     * @param value value to insert
     * @return true
     * @throws NullPointerException if value is null
     */
    public boolean offer(Float value) {
        if (value == null) {
            throw new NullPointerException();
        }
        pQueue.add(value);
        return true;
    }
    /**
     * Removes and returns the smallest value, or null if the queue is empty.
     *
     * @return smallest value, or null
     */
    public Float poll() {
        return isEmpty() ? null : pQueue.remove();
    }
    /**
     * Removes all values from the queue.
     */
    public void clear() {
        pQueue.clear();
    }
    /**
     * Returns the smallest value without removing it, or null if empty.
     * @return smallest value, or null
     */
    public Float peek() {
        return isEmpty() ? null : pQueue.element();
    }
    /**
     * Returns whether the queue is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return pQueue.size() == 0;
    }
}
