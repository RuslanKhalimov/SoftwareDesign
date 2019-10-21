import com.sun.istack.internal.NotNull;

import java.util.Optional;

public abstract class LRUCache<T> {
    protected final int capacity;
    protected Node head = null, tail = null;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public void put(int key, @NotNull T value) {
        assert value != null : "value can't be null";
        doPut(key, value);
        assert head != null && tail != null : "value not inserted";
        assert head.value == value : "value not pushed";
        assert size() <= capacity : "too big size";
    }

    public Optional<T> get(int key) {
        int oldSize = size();
        Optional<T> res = doGet(key);
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

    protected abstract void doPut(int key, @NotNull T value);
    protected abstract Optional<T> doGet(int key);

    protected class Node {
        final int key;
        @NotNull final T value;
        Node prev, next;

        Node(int key, T value) {
            this.key = key;
            this.value = value;
        }
    }
}
