import com.sun.istack.internal.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LinkedHashMapLRUCache<T> extends LRUCache<T> {
    private Map<Integer, Node> keyToNode = new HashMap<>();

    public LinkedHashMapLRUCache(int capacity) {
        super(capacity);
    }

    protected void doPut(int key, @NotNull  T value) {
        removeNode(key);
        addNode(key, value);
    }

    protected Optional<T> doGet(int key) {
        if (!keyToNode.containsKey(key)) {
            return Optional.empty();
        }
        T res = keyToNode.get(key).value;
        removeNode(key);
        addNode(key, res);
        return Optional.of(res);
    }

    @Override
    protected int size() {
        return keyToNode.size();
    }

    private void removeNode(int key) {
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

    private void addNode(int key, @NotNull T value) {
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
