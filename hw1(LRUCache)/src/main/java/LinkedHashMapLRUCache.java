import com.sun.istack.internal.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LinkedHashMapLRUCache<K, V> extends LRUCache<K, V> {
    private Map<K, Node> keyToNode = new HashMap<>();

    public LinkedHashMapLRUCache(int capacity) {
        super(capacity);
    }

    protected void doPut(@NotNull K key, @NotNull V value) {
        removeNode(key);
        addNode(key, value);
    }

    protected Optional<V> doGet(@NotNull K key) {
        if (!keyToNode.containsKey(key)) {
            return Optional.empty();
        }
        V res = keyToNode.get(key).value;
        removeNode(key);
        addNode(key, res);
        return Optional.of(res);
    }

    @Override
    protected int size() {
        return keyToNode.size();
    }

    private void removeNode(@NotNull K key) {
        if (!keyToNode.containsKey(key)) {
            return;
        }
        Node current = keyToNode.get(key);
        if (current.next != null) {
            current.next.prev = current.prev;
        } else {  // current == tail
            tail = current.prev;
        }
        if (current.prev != null) {
            current.prev.next = current.next;
        } else {  // current == head
            head = current.next;
        }
        keyToNode.remove(key);
    }

    private void addNode(@NotNull K key, @NotNull V value) {
        Node newNode = new Node(key, value);
        newNode.next = head;
        if (head != null) {
            head.prev = newNode;
        }
        head = newNode;
        if (tail == null) {
            tail = newNode;
        }

        keyToNode.put(key, newNode);
        if (size() > capacity) {
            removeNode(tail.key);
        }
    }
}
