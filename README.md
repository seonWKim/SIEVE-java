# SIEVE
- SIEVE is a way to implement cache logic instead of LRU 
- Original Blog Post: https://cachemon.github.io/SIEVE-website/blog/2023/12/17/sieve-is-simpler-than-lru/#sieve-is-not-scan-resistant
- Algorithm described in blog  
```python
class Node:
    def __init__(self, value):
        self.value = value
        self.visited = False
        self.prev = None
        self.next = None

class SieveCache:
    def __init__(self, capacity):
        self.capacity = capacity
        self.cache = {}  # To store cache items as {value: node}
        self.head = None
        self.tail = None
        self.hand = None
        self.size = 0

    def _add_to_head(self, node):
        node.next = self.head
        node.prev = None
        if self.head:
            self.head.prev = node
        self.head = node
        if self.tail is None:
            self.tail = node

    def _remove_node(self, node):
        if node.prev:
            node.prev.next = node.next
        else:
            self.head = node.next
        if node.next:
            node.next.prev = node.prev
        else:
            self.tail = node.prev

    def _evict(self):
        obj = self.hand if self.hand else self.tail
        while obj and obj.visited:
            obj.visited = False
            obj = obj.prev if obj.prev else self.tail
        self.hand = obj.prev if obj.prev else None
        del self.cache[obj.value]
        self._remove_node(obj)
        self.size -= 1

    def access(self, x):
        if x in self.cache:  # Cache Hit
            node = self.cache[x]
            node.visited = True
        else:  # Cache Miss
            if self.size == self.capacity:  # Cache Full
                self._evict()  # Eviction
            new_node = Node(x)
            self._add_to_head(new_node)
            self.cache[x] = new_node
            self.size += 1
            new_node.visited = False  # Insertion

    def show_cache(self):
        current = self.head
        while current:
            print(f'{current.value} (Visited: {current.visited})', end=' -> ' if current.next else '\n')
            current = current.next
```