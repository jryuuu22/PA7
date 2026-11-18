/*
 * Name: Jay Wu
 * PID: A19135498
 */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for MyPriorityQueue: every constructor and public method, resizing,
 * duplicates, empty-queue behavior, and the null-entry exception.
 */
public class MyPriorityQueueTest {


    @Test
    public void testConstructor() {
        MyPriorityQueue q = new MyPriorityQueue(1);
        assertTrue(q.isEmpty());
        assertNull(q.peek());
        assertNull(q.poll());
    }

    @Test
    public void testConstructorGrowsPastInitialSize() {
        MyPriorityQueue q = new MyPriorityQueue(2);
        for (int i = 20; i > 0; i--) {
            q.offer((float) i);
        }
        assertEquals(1f, q.peek());
        assertFalse(q.isEmpty());
        assertEquals(1f, q.poll());
    }


    @Test
    public void testOffer() {
        MyPriorityQueue q = new MyPriorityQueue(5);
        assertTrue(q.offer(3f));
        assertEquals(3f, q.peek());
        assertTrue(q.offer(1f));
        assertEquals(1f, q.peek());
        assertTrue(q.offer(2f));
        assertEquals(1f, q.peek());
    }

    @Test
    public void testOfferNullThrows() {
        MyPriorityQueue q = new MyPriorityQueue(5);
        assertThrows(NullPointerException.class, () -> q.offer(null));
        assertTrue(q.isEmpty());
        q.offer(1f);
        assertThrows(NullPointerException.class, () -> q.offer(null));
        assertEquals(1f, q.peek());
    }


    @Test
    public void testPollAscendingOrder() {
        MyPriorityQueue q = new MyPriorityQueue(3);
        float[] values = {4.5f, -2f, 7f, 0f, 3.25f, -2f, 10f};
        for (float v : values) {
            q.offer(v);
        }
        assertEquals(-2f, q.poll());
        assertEquals(-2f, q.poll());
        assertEquals(0f, q.poll());
        assertEquals(3.25f, q.poll());
        assertEquals(4.5f, q.poll());
        assertEquals(7f, q.poll());
        assertEquals(10f, q.poll());
        assertNull(q.poll());
    }

    @Test
    public void testPollEmptyReturnsNull() {
        MyPriorityQueue q = new MyPriorityQueue(4);
        assertNull(q.poll());
        q.offer(1f);
        assertEquals(1f, q.poll());
        assertNull(q.poll());
    }

    @Test
    public void testClear() {
        MyPriorityQueue q = new MyPriorityQueue(4);
        q.offer(1f);
        q.offer(2f);
        q.clear();
        assertTrue(q.isEmpty());
        assertNull(q.peek());
        q.offer(5f);
        assertEquals(5f, q.peek());
    }

    // ---- peek ----

    @Test
    public void testPeek() {
        MyPriorityQueue q = new MyPriorityQueue(4);
        assertNull(q.peek());
        q.offer(9f);
        q.offer(4f);
        assertEquals(4f, q.peek());
        assertEquals(4f, q.peek());
        assertEquals(4f, q.poll());
        assertEquals(9f, q.peek());
    }

    @Test
    public void testIsEmpty() {
        MyPriorityQueue q = new MyPriorityQueue(1);
        assertTrue(q.isEmpty());
        q.offer(1f);
        assertFalse(q.isEmpty());
        q.poll();
        assertTrue(q.isEmpty());
    }
}
