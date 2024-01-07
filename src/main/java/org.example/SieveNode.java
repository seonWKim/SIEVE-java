package org.example;

public class SieveNode<T> {
    private T value;
    private SieveNode<T> prev;
    private SieveNode<T> next;
    private boolean visited = false;

    public SieveNode(T value) {
        this.value = value;
        this.prev = null;
        this.next = null;
    }

    public T value() {
        return this.value;
    }

    public boolean visited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public SieveNode<T> next() {
        return this.next;
    }

    public void setNext(SieveNode<T> next) {
        this.next = next;
    }

    public SieveNode<T> prev() {
        return this.prev;
    }

    public void setPrev(SieveNode<T> prev) {
        this.prev = prev;
    }
}
