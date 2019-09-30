package hw1;

import hw1.LRUCache;
import hw1.LinkedHashMapLRUCache;
import org.junit.*;

import java.util.Optional;
import java.util.Random;

public class LRUCacheTest {
    @Test
    public void testContracts() {
        LRUCache<Integer> cache = new LinkedHashMapLRUCache<>(10);

        for (int i = 0; i < 1000000; i++) {
            Random rand = new Random();
            if (rand.nextBoolean()) {
                cache.put(rand.nextInt() % 20, rand.nextInt());
            } else {
                cache.get(rand.nextInt() % 20);
            }
        }
    }

    @Test
    public void testEmptyCache() {
        LRUCache<Integer> cache = new LinkedHashMapLRUCache<>(1);
        Assert.assertFalse(cache.get(0).isPresent());
    }

    @Test
    public void testGet() {
        LRUCache<Integer> cache = new LinkedHashMapLRUCache<>(1);
        cache.put(0, 0);
        Assert.assertEquals(Optional.of(0), cache.get(0));
    }

    @Test
    public void testModify() {
        LRUCache<Integer> cache = new LinkedHashMapLRUCache<>(1);
        cache.put(0, 0);
        cache.put(0, 1);
        Assert.assertEquals(Optional.of(1), cache.get(0));
    }

    @Test
    public void testRemove() {
        LRUCache<Integer> cache = new LinkedHashMapLRUCache<>(2);

        cache.put(0, 0);  // [0]
        cache.put(1, 1);  // [1, 0]
        cache.put(2, 2);  // [2, 1]
        Assert.assertFalse(cache.get(0).isPresent());
    }

    @Test
    public void testPushByPut() {
        LRUCache<Integer> cache = new LinkedHashMapLRUCache<>(2);

        cache.put(0, 0);  // [0]
        cache.put(1, 1);  // [1, 0]
        cache.put(0, 0);  // [0, 1]
        cache.put(2, 2);  // [2, 0]
        Assert.assertFalse(cache.get(1).isPresent());
    }

    @Test
    public void testPushByGet() {
        LRUCache<Integer> cache = new LinkedHashMapLRUCache<>(2);

        cache.put(0, 0);  // [0]
        cache.put(1, 1);  // [1, 0]
        cache.get(0);  // [0, 1]
        cache.put(2, 2);  // [2, 0]
        Assert.assertFalse(cache.get(1).isPresent());
    }

}
