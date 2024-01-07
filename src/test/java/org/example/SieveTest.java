package org.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    @Test
    void parallelExecutionStressTest() throws Exception {
        SieveCache<Integer> cache = new SieveCache<>(100);
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Callable<Void>> callables = List.of(testCallable(cache), testCallable(cache), testCallable(cache),
                                                 testCallable(cache), testCallable(cache));
        List<Future<Void>> futures = executor.invokeAll(callables);
        for (Future<Void> future : futures) {
            future.get();
        }
    }

    private Callable<Void> testCallable(SieveCache<Integer> cache) {
        return () -> {
            for (int i = 0; i < 1000; i++) {
                cache.access(i);
                System.out.println(Thread.currentThread().getName());
            }
            return null;
        };
    }
}