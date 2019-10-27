import com.sun.istack.internal.NotNull;

import java.util.Optional;

public abstract class LRUCache<K, V> {
    protected final int capacity;
    protected Node head = null, tail = null;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public void put(@NotNull K key, @NotNull V value) {
        assert key != null : "key can't be null";
        assert value != null : "value can't be null";
        doPut(key, value);
        assert head != null && tail != null : "value not inserted";
        assert head.value == value : "value not pushed";
        assert size() <= capacity : "too big size";
    }

    public Optional<V> get(@NotNull K key) {
        assert key != null : "key can't be null";
        int oldSize = size();
        Optional<V> res = doGet(key);
        assert (!res.isPresent() || head.value == res.get()) : "value not pushed";
        assert oldSize == size() : "size changed";
        return res;
    }

    protected int size() {
        int res = 0;
        Node cur = head;
        while (cur != null) {
            cur = cur.next;
        }
        return res;
    }

    protected abstract void doPut(@NotNull K key, @NotNull V value);
    protected abstract Optional<V> doGet(@NotNull K key);

    protected class Node {
        @NotNull final K key;
        @NotNull final V value;
        Node prev, next;

        Node(@NotNull K key, @NotNull V value) {
            this.key = key;
            this.value = value;
        }
    }
}
