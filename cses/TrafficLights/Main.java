import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.Arrays;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    static class Solver {
        void solve(InputReader in, PrintWriter out) {
            int streetLength = in.nextInt();
            int n = in.nextInt();
            int[] lights = new int[n];
            for (int i = 0; i < n; i++) {
                lights[i] = in.nextInt();
            }
            RedBlackTree lengthCount = new RedBlackTree(2 * n + 10);
            int nilVal = 0;
            RedBlackTree pos = new RedBlackTree(n + 2);
            lengthCount.put(streetLength, 1);
            pos.put(0, nilVal);
            pos.put(streetLength, nilVal);
            StringBuilder outBuf = new StringBuilder();
            int curMaxLength = streetLength;
            for (int i = 0; i < lights.length; i++) {
                int light = lights[i];
                int lowNode = pos.getFloor(light);
                int low = pos.keys[lowNode];
                int highNode = pos.getCeiling(light);
                int high = pos.keys[highNode];
                pos.put(light, nilVal);
                int lowLength = light - low;
                int highLength = high - light;
                int totalLength = high - low;
                int totalLengthCount = lengthCount.get(totalLength);

                lengthCount.put(lowLength, lengthCount.getOrDefault(lowLength, 0) + 1);
                lengthCount.put(highLength, lengthCount.getOrDefault(highLength, 0) + 1);

                if (totalLengthCount == 1) {
                    lengthCount.remove(totalLength);
                    if (totalLength == curMaxLength) {
                        int maxLengthNode = lengthCount.getLast();
                        curMaxLength = lengthCount.keys[maxLengthNode];
                    }
                } else {
                    lengthCount.put(totalLength, totalLengthCount - 1);
                }
                outBuf.append(curMaxLength).append(" ");
            }
            out.println(outBuf);
        }
    }

    static class RedBlackTree {
        // ---- external data ----
        public int[] keys;
        public int[] values;

        // ---- internal structure ----
        private int[] parent;
        private int[] left;
        private int[] right;
        private boolean[] red;     // true=RED, false=BLACK
        private int[] freeNext;    // free-list links

        private int root = -1;
        private int size = 0;          // live nodes
        private int nextIndex = 0;     // next unused index at the "end"
        private int freeHead = -1;     // top of free-list
        private int capacity;

        // ---- construction ----
        public RedBlackTree() { this(16); }
        public RedBlackTree(int initialCapacity) {
            if (initialCapacity < 1) initialCapacity = 1;
            capacity = Integer.highestOneBit(initialCapacity - 1) << 1;
            allocArrays(capacity);
        }

        // ---- public API ----
        public int size() { return size; }
        public boolean isEmpty() { return size == 0; }

        /** Returns true if the key exists. */
        public boolean containsKey(int key) { return findNode(key) != -1; }

        /** Get value or throw if missing. Use getOrDefault if you prefer. */
        public int get(int key) {
            int i = findNode(key);
            if (i == -1) throw new IllegalArgumentException("Key not found: " + key);
            return values[i];
        }

        public int getOrDefault(int key, int defaultValue) {
            int i = findNode(key);
            return i == -1 ? defaultValue : values[i];
        }

        /**
         * Insert or overwrite. Returns true if this was a new key, false if overwritten.
         */
        public boolean put(int key, int value) {
            if (root == -1) {
                int z = newNode(key, value);
                root = z;
                red[z] = false; // root is black
                size++;
                return true;
            }
            int y = -1;
            int x = root;
            int cmp;
            while (x != -1) {
                y = x;
                if (key == keys[x]) {
                    values[x] = value; // overwrite
                    return false;
                }
                cmp = key < keys[x] ? -1 : 1;
                x = (cmp < 0) ? left[x] : right[x];
            }
            int z = newNode(key, value);
            parent[z] = y;
            if (key < keys[y]) left[y] = z; else right[y] = z;

            insertFixup(z);
            size++;
            return true;
        }

        /** Remove by key. Returns true if key existed. */
        public boolean remove(int key) {
            int z = findNode(key);
            if (z == -1) return false;
            deleteNode(z);
            size--;
            return true;
        }

        /** In-order visitor. */
        public void forEach(java.util.function.BiConsumer<Integer,Integer> action) {
            int cur = firstNode();
            while (cur != -1) {
                action.accept(keys[cur], values[cur]);
                cur = successor(cur);
            }
        }

        public int getLast() {
            int cur = root;
            while (right[cur] != -1) {
                cur = right[cur];
            }
            return cur;
        }

        public int getFloor(int key) {
            int cur = root;
            int ans = -1;
            while (cur != -1) {
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
            int ans = -1;
            while (cur != -1) {
                if (keys[cur] >= key) {
                    ans = cur;
                    cur = left[cur];
                } else {
                    cur = right[cur];
                }
            }
            return ans;
        }

        // ---- core internals ----

        private int findNode(int key) {
            int x = root;
            while (x != -1) {
                if (key == keys[x]) return x;
                x = key < keys[x] ? left[x] : right[x];
            }
            return -1;
        }

        private void insertFixup(int z) {
            // Standard CLRS with index-based nodes and -1 as NIL (black)
            while (isRed(parent[z])) {
                int p = parent[z];
                int g = parent[p];
                if (p == left[g]) {
                    int y = right[g]; // uncle
                    if (isRed(y)) {
                        // case 1
                        red[p] = false;
                        red[y] = false;
                        red[g] = true;
                        z = g;
                    } else {
                        if (z == right[p]) { // case 2
                            z = p;
                            rotateLeft(z);
                            p = parent[z];
                            g = parent[p];
                        }
                        // case 3
                        red[p] = false;
                        red[g] = true;
                        rotateRight(g);
                    }
                } else { // mirror
                    int y = left[g]; // uncle
                    if (isRed(y)) {
                        red[p] = false;
                        red[y] = false;
                        red[g] = true;
                        z = g;
                    } else {
                        if (z == left[p]) {
                            z = p;
                            rotateRight(z);
                            p = parent[z];
                            g = parent[p];
                        }
                        red[p] = false;
                        red[g] = true;
                        rotateLeft(g);
                    }
                }
                if (z == root) break;
            }
            red[root] = false;
        }

        private void deleteNode(int z) {
            int y = z;
            boolean yRed = red[y];
            int x;
            int xParent;

            if (left[z] == -1) {
                x = right[z];
                xParent = parent[z];
                transplant(z, right[z]);
            } else if (right[z] == -1) {
                x = left[z];
                xParent = parent[z];
                transplant(z, left[z]);
            } else {
                y = minimum(right[z]);
                yRed = red[y];
                x = right[y];
                if (parent[y] == z) {
                    xParent = y;
                    if (x != -1) parent[x] = y;
                } else {
                    xParent = parent[y];
                    transplant(y, right[y]);
                    right[y] = right[z];
                    if (right[y] != -1) parent[right[y]] = y;
                }
                transplant(z, y);
                left[y] = left[z];
                if (left[y] != -1) parent[left[y]] = y;
                red[y] = red[z];
            }

            // recycle z
            recycleIndex(z);

            if (!yRed) deleteFixup(x, xParent);
        }

        private void deleteFixup(int x, int xParent) {
            while (x != root && isBlack(x)) {
                if (x == left[xParent]) {
                    int w = right[xParent];
                    // case 1: w is red
                    if (isRed(w)) {
                        red[w] = false;
                        red[xParent] = true;
                        rotateLeft(xParent);
                        w = right[xParent];
                    }
                    // case 2: w's children black
                    if (isBlack(left[w]) && isBlack(right[w])) {
                        red[w] = true;
                        x = xParent;
                        xParent = parent[xParent];
                    } else {
                        // case 3: w's right is black, left is red
                        if (isBlack(right[w])) {
                            red[left[w]] = false;
                            red[w] = true;
                            rotateRight(w);
                            w = right[xParent];
                        }
                        // case 4: w's right red
                        red[w] = red[xParent];
                        red[xParent] = false;
                        if (right[w] != -1) red[right[w]] = false;
                        rotateLeft(xParent);
                        x = root;
                        break;
                    }
                } else {
                    int w = left[xParent];
                    if (isRed(w)) {
                        red[w] = false;
                        red[xParent] = true;
                        rotateRight(xParent);
                        w = left[xParent];
                    }
                    if (isBlack(left[w]) && isBlack(right[w])) {
                        red[w] = true;
                        x = xParent;
                        xParent = parent[xParent];
                    } else {
                        if (isBlack(left[w])) {
                            red[right[w]] = false;
                            red[w] = true;
                            rotateLeft(w);
                            w = left[xParent];
                        }
                        red[w] = red[xParent];
                        red[xParent] = false;
                        if (left[w] != -1) red[left[w]] = false;
                        rotateRight(xParent);
                        x = root;
                        break;
                    }
                }
            }
            if (x != -1) red[x] = false;
        }

        private void rotateLeft(int x) {
            int y = right[x];
            int beta = left[y];
            right[x] = beta;
            if (beta != -1) parent[beta] = x;

            parent[y] = parent[x];
            if (parent[x] == -1) root = y;
            else if (x == left[parent[x]]) left[parent[x]] = y;
            else right[parent[x]] = y;

            left[y] = x;
            parent[x] = y;
        }

        private void rotateRight(int x) {
            int y = left[x];
            int beta = right[y];
            left[x] = beta;
            if (beta != -1) parent[beta] = x;

            parent[y] = parent[x];
            if (parent[x] == -1) root = y;
            else if (x == right[parent[x]]) right[parent[x]] = y;
            else left[parent[x]] = y;

            right[y] = x;
            parent[x] = y;
        }

        // ---- helpers ----
        private boolean isRed(int i) { return i != -1 && red[i]; }
        private boolean isBlack(int i) { return i == -1 || !red[i]; }

        private int minimum(int x) {
            while (left[x] != -1) x = left[x];
            return x;
        }

        private int successor(int x) {
            if (right[x] != -1) {
                int y = right[x];
                while (left[y] != -1) y = left[y];
                return y;
            }
            int y = parent[x];
            while (y != -1 && x == right[y]) {
                x = y; y = parent[y];
            }
            return y;
        }

        private int firstNode() {
            if (root == -1) return -1;
            int x = root;
            while (left[x] != -1) x = left[x];
            return x;
        }

        private void transplant(int u, int v) {
            if (parent[u] == -1) root = v;
            else if (u == left[parent[u]]) left[parent[u]] = v;
            else right[parent[u]] = v;
            if (v != -1) parent[v] = parent[u];
        }

        private int newNode(int key, int value) {
            if (freeHead != -1) {
                int idx = freeHead;
                freeHead = freeNext[idx];
                initNode(idx, key, value);
                return idx;
            }
            if (nextIndex == capacity) grow();
            int idx = nextIndex++;
            initNode(idx, key, value);
            return idx;
        }

        private void recycleIndex(int idx) {
            // Clear minimal links to be tidy (not strictly required)
            parent[idx] = left[idx] = right[idx] = -1;
            red[idx] = false;
            freeNext[idx] = freeHead;
            freeHead = idx;
        }

        private void initNode(int idx, int key, int value) {
            keys[idx] = key;
            values[idx] = value;
            parent[idx] = left[idx] = right[idx] = -1;
            red[idx] = true; // new inserts start red
            freeNext[idx] = -1;
        }

        private void allocArrays(int n) {
            keys = new int[n];
            values = new int[n];
            parent = new int[n];
            left = new int[n];
            right = new int[n];
            red = new boolean[n];
            freeNext = new int[n];
            Arrays.fill(parent, -1);
            Arrays.fill(left, -1);
            Arrays.fill(right, -1);
            Arrays.fill(freeNext, -1);
        }

        private void grow() {
            int newCap = capacity << 1;
            keys = Arrays.copyOf(keys, newCap);
            values = Arrays.copyOf(values, newCap);
            parent = growAndFill(parent, newCap, -1);
            left   = growAndFill(left,   newCap, -1);
            right  = growAndFill(right,  newCap, -1);
            red    = Arrays.copyOf(red, newCap);
            freeNext = growAndFill(freeNext, newCap, -1);
            capacity = newCap;
        }

        private static int[] growAndFill(int[] a, int newLen, int fill) {
            int old = a.length;
            a = Arrays.copyOf(a, newLen);
            Arrays.fill(a, old, newLen, fill);
            return a;
        }
    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[65536];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int get() {
            if (numChars == -1) {
                throw new RuntimeException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new RuntimeException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        public int peek() {
            if (numChars == -1) {
                return -1;
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    return -1;
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar];
        }

        public boolean hasNext() {
            int c = peek();
            while (c != -1 && isWhitespace(c)) {
                get();
                c = peek();
            }
            return c != -1;
        }

        public String next() {
            int c = get();
            while (isWhitespace(c)) {
                c = get();
            }
            StringBuilder res = new StringBuilder();
            do {
                if (Character.isValidCodePoint(c)) {
                    res.appendCodePoint(c);
                }
                c = get();
            } while (!isWhitespace(c));
            return res.toString();
        }

        public int nextInt() {
            int c = get();
            while (isWhitespace(c)) {
                c = get();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = get();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new RuntimeException();
                }
                res *= 10;
                res += c - '0';
                c = get();
            } while (!isWhitespace(c));
            return res * sgn;
        }

        public long nextLong() {
            int c = get();
            while (isWhitespace(c)) {
                c = get();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = get();
            }
            long res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new RuntimeException();
                }
                res *= 10;
                res += c - '0';
                c = get();
            } while (!isWhitespace(c));
            return res * sgn;
        }

        public double readDouble() {
            int c = get();
            while (isWhitespace(c)) {
                c = get();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = get();
            }
            double res = 0;
            while (!isWhitespace(c) && c != '.') {
                if (c == 'e' || c == 'E') {
                    return res * Math.pow(10, nextInt());
                }
                if (c < '0' || c > '9') {
                    throw new RuntimeException();
                }
                res *= 10;
                res += c - '0';
                c = get();
            }
            if (c == '.') {
                c = get();
                double m = 1;
                while (!isWhitespace(c)) {
                    if (c == 'e' || c == 'E') {
                        return res * Math.pow(10, nextInt());
                    }
                    if (c < '0' || c > '9') {
                        throw new RuntimeException();
                    }
                    m /= 10;
                    res += (c - '0') * m;
                    c = get();
                }
            }
            return res * sgn;
        }

        public static boolean isWhitespace(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        Solver solver = new Solver();
        solver.solve(in, out);
        out.close();
    }
}
