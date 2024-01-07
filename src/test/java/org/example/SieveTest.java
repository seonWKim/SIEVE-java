package org.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SieveTest {
    @Test
    void testNodeEviction() {
        SieveCache<Integer> cache = new SieveCache<>(3);
        cache.access(1);
        cache.access(2);
        cache.access(3);

        cache.access(4);

        assertFalse(cache.contains(1));
        assertTrue(cache.contains(2));
        assertTrue(cache.contains(3));
        assertTrue(cache.contains(4));
    }

    @Test
    void testNodeEviction2() {
        SieveCache<Integer> cache = new SieveCache<>(3);
        cache.access(1);
        cache.access(2);
        cache.access(3);

        // access 1 and 2, make 3 the target node to evict
        cache.access(1);
        cache.access(2);

        cache.access(4);

        assertTrue(cache.contains(1));
        assertTrue(cache.contains(2));
        assertFalse(cache.contains(3));
        assertTrue(cache.contains(4));
    }
}