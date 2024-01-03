public class Node<T> {
    private T value;
    private Node<T> prev;
    private Node<T> next;
    private boolean visited = false;

    public Node(T value) {
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

    public Node<T> next() {
        return this.next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public Node<T> prev() {
        return this.prev;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }
}
