/*
 * Name: Jay Wu
 * PID: A19135498
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Starter tests for dHeap.
 * Add tests for every public constructor and method as specified in Part 2.
 * Include both min-heaps and max-heaps, different branching factors,
 * resizing, duplicate values, and all specified exceptions.
 * Style is not graded for this file.
 */
public class dHeapTest {
    private dHeap maxHeap;
    private dHeap minHeap;

    @BeforeEach
    public void setUp() {
        maxHeap = new dHeap();
        minHeap = new dHeap(4, 10, false);
    }

    @Test
    public void testInitialSize() {
        assertEquals(0, maxHeap.size());
        assertEquals(0, minHeap.size());
    }

    @Test
    public void testAddAndElement() {
        Float value = 2.5f;
        maxHeap.add(value);

        assertEquals(1, maxHeap.size());
        assertEquals(value, maxHeap.element());
    }


    private static final float[] VALUES = {5f, 3f, 9f, 1f, 7f, 2f, 8f, 6f, 4f, 0f};

    private dHeap filled(int d, int cap, boolean isMax) {
        dHeap h = new dHeap(d, cap, isMax);
        for (float v : VALUES) {
            h.add(v);
        }
        return h;
    }

    @Test
    public void testDefaultConstructorIsBinaryMax() {
        dHeap h = new dHeap();
        assertEquals(0, h.size());
        h.add(1f);
        h.add(5f);
        h.add(3f);
        assertEquals(5f, h.element());
        assertEquals(3, h.size());
    }

    @Test
    public void testDefaultConstructorGrowsPastTen() {
        dHeap h = new dHeap();
        for (int i = 0; i < 25; i++) {
            h.add((float) i);
        }
        assertEquals(25, h.size());
        assertEquals(24f, h.remove());
        assertEquals(23f, h.element());
    }

    @Test
    public void testSizeConstructorIsBinaryMax() {
        dHeap h = new dHeap(3);
        assertEquals(0, h.size());
        h.add(2f);
        h.add(10f);
        h.add(-4f);
        h.add(6f);
        assertEquals(10f, h.element());
        assertEquals(4, h.size());
    }

    @Test
    public void testSizeConstructorCapacityOne() {
        dHeap h = new dHeap(1);
        h.add(1f);
        h.add(2f);
        h.add(3f);
        assertEquals(3f, h.remove());
        assertEquals(2f, h.remove());
        assertEquals(1f, h.remove());
    }

    @Test
    public void testFullConstructorMinAndMax() {
        assertEquals(0f, filled(3, 4, false).element());
        assertEquals(9f, filled(3, 4, true).element());
        assertEquals(0, new dHeap(4, 1, false).size());
    }

    @Test
    public void testFullConstructorThrows() {
        assertThrows(IllegalArgumentException.class, () -> new dHeap(0, 5, true));
        assertThrows(IllegalArgumentException.class, () -> new dHeap(-1, 5, false));
        assertThrows(IllegalArgumentException.class, () -> new dHeap(-100, 1, true));
    }

    @Test
    public void testFullConstructorDOne() {
        dHeap h = filled(1, 2, false);
        assertEquals(0f, h.remove());
        assertEquals(1f, h.remove());
        assertEquals(2f, h.remove());
    }

    @Test
    public void testSize() {
        dHeap h = new dHeap(3, 2, false);
        assertEquals(0, h.size());
        h.add(1f);
        h.add(2f);
        h.add(3f);
        assertEquals(3, h.size());
        h.remove();
        assertEquals(2, h.size());
        h.clear();
        assertEquals(0, h.size());
    }


    @Test
    public void testAddMinHeapVariousD() {
        for (int d = 2; d <= 4; d++) {
            dHeap h = new dHeap(d, 3, false);
            h.add(5f);
            assertEquals(5f, h.element());
            h.add(2f);
            assertEquals(2f, h.element());
            h.add(8f);
            assertEquals(2f, h.element());
            h.add(-1f);
            assertEquals(-1f, h.element());
        }
    }

    @Test
    public void testAddMaxHeapVariousD() {
        for (int d = 2; d <= 4; d++) {
            dHeap h = new dHeap(d, 3, true);
            h.add(5f);
            assertEquals(5f, h.element());
            h.add(2f);
            assertEquals(5f, h.element());
            h.add(8f);
            assertEquals(8f, h.element());
            h.add(8f);
            assertEquals(4, h.size());
        }
    }

    @Test
    public void testAddNullThrows() {
        assertThrows(NullPointerException.class, () -> new dHeap().add(null));
        assertThrows(NullPointerException.class, () -> new dHeap(3, 5, false).add(null));
        dHeap h = filled(4, 10, true);
        assertThrows(NullPointerException.class, () -> h.add(null));
        assertEquals(VALUES.length, h.size());
    }


    @Test
    public void testRemoveMinHeapSortedOrder() {
        for (int d = 2; d <= 4; d++) {
            dHeap h = filled(d, 2, false);
            for (int i = 0; i <= 9; i++) {
                assertEquals((float) i, h.remove());
            }
            assertEquals(0, h.size());
        }
    }

    @Test
    public void testRemoveMaxHeapSortedOrder() {
        for (int d = 2; d <= 4; d++) {
            dHeap h = filled(d, 2, true);
            for (int i = 9; i >= 0; i--) {
                assertEquals((float) i, h.remove());
            }
            assertEquals(0, h.size());
        }
    }

    @Test
    public void testRemoveWithDuplicates() {
        dHeap h = new dHeap(3, 5, false);
        h.add(2f);
        h.add(2f);
        h.add(1f);
        h.add(2f);
        assertEquals(1f, h.remove());
        assertEquals(2f, h.remove());
        assertEquals(2f, h.remove());
        assertEquals(2f, h.remove());
    }

    @Test
    public void testRemoveEmptyThrows() {
        assertThrows(NoSuchElementException.class, () -> new dHeap().remove());
        dHeap h = new dHeap(4, 3, false);
        h.add(1f);
        h.remove();
        assertThrows(NoSuchElementException.class, h::remove);
        dHeap cleared = filled(2, 10, true);
        cleared.clear();
        assertThrows(NoSuchElementException.class, cleared::remove);
    }


    @Test
    public void testClear() {
        dHeap h = filled(3, 10, false);
        h.clear();
        assertEquals(0, h.size());
        assertThrows(NoSuchElementException.class, h::element);
        h.add(42f);
        assertEquals(42f, h.element());
        assertEquals(1, h.size());
    }


    @Test
    public void testElementDoesNotRemove() {
        dHeap max = filled(2, 10, true);
        assertEquals(9f, max.element());
        assertEquals(9f, max.element());
        assertEquals(VALUES.length, max.size());
        dHeap min = filled(4, 10, false);
        assertEquals(0f, min.element());
    }

    @Test
    public void testElementEmptyThrows() {
        assertThrows(NoSuchElementException.class, () -> new dHeap().element());
        assertThrows(NoSuchElementException.class, () -> new dHeap(3, 1, false).element());
        dHeap h = new dHeap(2);
        h.add(1f);
        h.remove();
        assertThrows(NoSuchElementException.class, h::element);
    }
}