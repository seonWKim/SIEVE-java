package org.example;

import java.util.Map;

public class SieveCache<T> {
    private final long capacity;
    private Map<T, Node<T>> cache;
    private Node<T> head;
    private Node<T> tail;
    private Node<T> hand;
    private long size = 0;

    public SieveCache(long capacity) {
        this.capacity = capacity;
    }

    private void addToHead(Node<T> node) {
        node.setNext(hand);
        node.setPrev(null);
        if (head != null) {
            head.setPrev(node);
        }
        head = node;
        if (tail == null) {
            tail = node;
        }
    }

    // This method ensures that the node exists the list of nodes
    private void remove(Node<T> node) {
        if (node.prev() != null) {
            node.prev().setNext(node.next());
        } else {
            head = node.next();
        }

        if (node.next() != null) {
            node.next().setPrev(node.prev());
        } else {
            tail = node.prev();
        }
    }

    public void evict() {
        Node<T> obj = hand != null ? hand : tail;

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

            Node<T> newNode = new Node<T>(value);
            addToHead(newNode);
            cache.put(value, newNode);
            size++;
        }
    }

    public void showCache() {
        Node<T> obj = head;
        while (obj != null) {
            System.out.print(obj.value() + " " + "(Visited: " + obj.visited() + ") ");
            obj = obj.next();
        }
    }
}
