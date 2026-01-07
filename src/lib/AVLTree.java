class AVLTree {
    private final int nil = 0;
    private int root;
    private int capacity;
    private int size;
    private int[] parent;
    private int[] left;
    private int[] right;
    private int[] height;

    // Actual data
    public int[] keys;
    public int[] values;

    public AVLTree(int capacity) {
        this.capacity = capacity;
        this.root = nil;
        this.size = 0;
        int allocSize = capacity + 1;
        this.keys = new int[allocSize];
        this.values = new int[allocSize];
        this.parent = new int[allocSize];
        this.left = new int[allocSize];
        this.right = new int[allocSize];
        this.height = new int[allocSize];
        height[0] = -1;
    }

    public int get(int key) {
        int node = search(key);
        if (node == nil) {
            throw new RuntimeException("Key not found: " + key);
        }
        return values[node];
    }

    public int getOrDefault(int key, int defaultValue) {
        int node = search(key);
        if (node == nil) {
            return defaultValue;
        }
        return values[node];
    }

    public int search(int key) {
        int node = root;

        while (node != nil) {
            if (key < keys[node]) {
                node = left[node];
            } else if (key > keys[node]) {
                node = right[node];
            } else {
                return node;
            }
        }

        return nil;
    }

    public void put(int key) {
        put(key, 0);
    }

    public void put(int key, int value) {
        int node = search(key);
        if (node != nil) {
            keys[node] = key;
            values[node] = value;
        } else {
            addNode(key, value);
        }
    }

    public void remove(int key) {
        int removedNode = search(key);
        if (removedNode == nil) {
            throw new RuntimeException("Key not found: " + key);
        }
        removeNode(removedNode);
    }

    public void removeNode(int node) {
        int parent = removeBstNode(node);
        // If really need to, may consider removing this???
        rebalance(parent);
    }

    private int removeBstNode(int removedNode) {
        if (removedNode == nil) return nil;
        int removedNodeParent = parent[removedNode];

        // Case 1: removedNode has only one child
        if (left[removedNode] == nil || right[removedNode] == nil) {
            if (left[removedNode] != nil) {
                if (removedNodeParent == nil) {
                    root = left[removedNode];
                } else if (removedNode == left[removedNodeParent]) {
                    left[removedNodeParent] = left[removedNode];
                } else {
                    right[removedNodeParent] = left[removedNode];
                }

                parent[left[removedNode]] = removedNodeParent;
            } else {
                if (parent[removedNode] == nil) {
                    root = right[removedNode];
                } else if (removedNode == left[removedNodeParent]) {
                    left[removedNodeParent] = right[removedNode];
                } else {
                    right[removedNodeParent] = right[removedNode];
                }

                parent[right[removedNode]] = removedNodeParent;
            }

            deleteNode(removedNode);
            return removedNodeParent;
        }

        // Case 2: removedNode has both children
        int nextLarger = getMinNode(right[removedNode]);
        int nextLargerParent = parent[nextLarger];

        if (nextLargerParent != removedNode) {
            left[nextLargerParent] = right[nextLarger];
            parent[right[nextLarger]] = nextLargerParent;
            swapKeys(removedNode, nextLarger);
            deleteNode(nextLarger);
            return nextLargerParent;
        }

        swapKeys(removedNode, nextLarger);
        right[removedNode] = right[nextLarger];
        parent[right[nextLarger]] = removedNode;
        deleteNode(nextLarger);

        return removedNode;
    }

    private void deleteNode(int node) {
        this.left[node] = nil;
        this.right[node] = nil;
        this.parent[node] = nil;
        this.keys[node] = 0;
        this.values[node] = 0;
    }

    private void swapKeys(int node1, int node2) {
        int tmpKey = keys[node1];
        keys[node1] = keys[node2];
        keys[node2] = tmpKey;

        int tmpValue = values[node1];
        values[node1] = values[node2];
        values[node2] = tmpValue;
    }

    public int getLast() {
        return getMaxNode(root);
    }

    public int getFloor(int key) {
        int cur = root;
        int ans = nil;
        while (cur != nil) {
            if (keys[cur] <= key) {
                ans = cur;
                cur = right[cur];
            } else {
                cur = left[cur];
            }
        }
        return ans;
    }

    public int getCeiling(int key) {
        int cur = root;
        int ans = nil;
        while (cur != nil) {
            if (keys[cur] >= key) {
                ans = cur;
                cur = left[cur];
            } else {
                cur = right[cur];
            }
        }
        return ans;
    }

    private int getMinNode(int node) {
        int cur = node;
        while (left[cur] != nil) {
            cur = left[cur];
        }
        return cur;
    }

    private int getMaxNode(int node) {
        int cur = node;
        while (right[cur] != nil) {
            cur = right[cur];
        }
        return cur;
    }

    private int addNode(int key, int value) {
        size++;
        int node = size;
        this.keys[node] = key;
        this.values[node] = value;
        this.left[node] = nil;
        this.right[node] = nil;
        this.parent[node] = nil;

        int parent = nil;
        int cur = root;

        while (cur != nil) {
            parent = cur;

            if (keys[node] < keys[cur]) {
                cur = left[cur];
            } else {
                cur = right[cur];
            }
        }

        this.parent[node] = parent;

        if (parent == nil) {
            root = node;
        } else {
            if (keys[node] < keys[parent]) {
                left[parent] = node;
            } else {
                right[parent] = node;
            }
        }

        rebalance(node);
        return node;
    }

    private void updateHeight(int node) {
        height[node] = Math.max(height[left[node]], height[right[node]]) + 1;
    }

    private void leftRotate(int x) {
        int y = right[x];
        int p = parent[x];
        int beta = left[y];

        right[x] = beta;

        if (beta != nil) {
            parent[beta] = x;
        }

        parent[y] = p;

        if (p == nil) {
            root = y;
        } else if (x == left[p]) {
            left[p] = y;
        } else {
            right[p] = y;
        }

        parent[x] = y;
        left[y] = x;

        updateHeight(x);
        updateHeight(y);
    }

    private void rightRotate(int y) {
        int x = left[y];
        int p = parent[y];
        int beta = right[x];

        left[y] = beta;

        if (beta != nil) {
            parent[beta] = y;
        }

        parent[x] = p;

        if (p == nil) {
            root = x;
        } else if (y == left[p]) {
            left[p] = x;
        } else {
            right[p] = x;
        }

        parent[y] = x;
        right[x] = y;

        updateHeight(x);
        updateHeight(y);
    }

    void rebalance(int node) {
        while (node != nil) {
            updateHeight(node);

            if (height[left[node]] >= height[right[node]] + 2) {
                if (height[left[left[node]]] >= height[right[left[node]]]) {
                    rightRotate(node);
                } else {
                    leftRotate(left[node]);
                    rightRotate(node);
                }
            } else if (height[right[node]] >= height[left[node]] + 2) {
                if (height[right[right[node]]] >= height[left[right[node]]]) {
                    leftRotate(node);
                } else {
                    rightRotate(right[node]);
                    leftRotate(node);
                }
            }

            node = parent[node];
        }
    }
}
