package collections.map;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * ConcurrentHashMap deep-dive:
 *
 * 1) What it is:
 *    - Modern concurrent map for multi-threaded access.
 *    - Designed for high concurrency with better performance than Hashtable.
 *
 * 2) Thread behavior:
 *    - Multiple threads can read/write safely.
 *    - Uses fine-grained locking/CAS-style internals (implementation detail).
 *    - No full map lock for every operation (better scalability).
 *
 * 3) Null support:
 *    - Does NOT allow null key or null value.
 *
 * 4) Extra atomic methods:
 *    - putIfAbsent, computeIfAbsent, compute, merge, replace, etc.
 *    - Useful for race-free updates.
 */
public class ConcurrentHashMapDemo {

    public static void demo() {
        System.out.println("\n--- CONCURRENTHASHMAP DEMO ---");

        ConcurrentHashMap<String, Integer> wordCount = new ConcurrentHashMap<>();

        // Atomic "initialize if absent" pattern.
        wordCount.putIfAbsent("java", 0);
        wordCount.putIfAbsent("map", 0);

        // Simple concurrent counting example.
        // We run multiple threads and safely increment map values.
        int threadCount = 4;
        CountDownLatch latch = new CountDownLatch(threadCount);

        Runnable task = () -> {
            try {
                for (int i = 0; i < 1000; i++) {
                    // compute(key, remappingFunction) is atomic per key.
                    wordCount.compute("java", (key, oldValue) -> oldValue == null ? 1 : oldValue + 1);
                    wordCount.compute("map", (key, oldValue) -> oldValue == null ? 1 : oldValue + 1);
                }
            } finally {
                latch.countDown();
            }
        };

        for (int i = 0; i < threadCount; i++) {
            new Thread(task, "worker-" + i).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted while waiting.");
        }

        System.out.println("Final counts (expected java=4000, map=4000): " + wordCount);

        // Iteration is weakly consistent:
        // no ConcurrentModificationException for typical concurrent changes,
        // but iterator may not reflect every instant update.
        System.out.println("Iterating safely:");
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        // Null is not allowed.
        try {
            wordCount.put(null, 10);
        } catch (NullPointerException e) {
            System.out.println("Null key is not allowed in ConcurrentHashMap.");
        }
    }

    public static void main(String[] args) {
        demo();
    }
}
