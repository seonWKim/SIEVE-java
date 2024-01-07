package org.example;

import java.util.HashMap;
import java.util.Map;

public class SieveCache<T> {
    private final int capacity;
    private Map<T, SieveNode<T>> cache;
    private SieveNode<T> head;
    private SieveNode<T> tail;
    private SieveNode<T> hand;
    private long size = 0;

    public SieveCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
    }

    private void addToHead(SieveNode<T> sieveNode) {
        sieveNode.setNext(hand);
        sieveNode.setPrev(null);
        if (head != null) {
            head.setPrev(sieveNode);
        }
        head = sieveNode;
        if (tail == null) {
            tail = sieveNode;
        }
    }

    // This method ensures that the node exists the list of nodes
    private void remove(SieveNode<T> sieveNode) {
        if (sieveNode.prev() != null) {
            sieveNode.prev().setNext(sieveNode.next());
        } else {
            head = sieveNode.next();
        }

        if (sieveNode.next() != null) {
            sieveNode.next().setPrev(sieveNode.prev());
        } else {
            tail = sieveNode.prev();
        }
    }

    public void evict() {
        SieveNode<T> obj = hand != null ? hand : tail;

        while (obj != null && obj.visited()) {
            obj.setVisited(false);
            obj = obj.prev() != null ? obj.prev() : tail;
        }

        assert obj != null;
        hand = obj.prev() != null ? obj.prev() : null;
        cache.remove(obj.value());
        remove(obj);
        size--;
    }

    public void access(T value) {
        if (cache.containsKey(value)) {
            cache.get(value).setVisited(true);
        } else {
            if (size == capacity) {
                evict();
            }

            SieveNode<T> newSieveNode = new SieveNode<>(value);
            addToHead(newSieveNode);
            cache.put(value, newSieveNode);
            size++;
        }
    }

    public void showCache() {
        SieveNode<T> obj = head;
        while (obj != null) {
            System.out.print(obj.value() + " " + "(Visited: " + obj.visited() + ") ");
            obj = obj.next();
        }
    }

    // Visible for testing
    protected boolean contains(T value) {
        return cache.containsKey(value);
    }
}
