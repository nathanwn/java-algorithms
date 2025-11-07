// Test problems:
// - https://cses.fi/problemset/task/1163
class IntList {
    int cap;
    int n;
    int[] a;

    IntList() {
        this(16);
    }

    IntList(int cap) {
        this.cap = cap;
        this.n = 0;
        this.a = new int[cap];
    }

    int get(int i) {
        return a[i];
    }

    void push(int x) {
        if (n == cap) {
            cap <<= 1;
            a = Arrays.copyOf(a, cap);
        }
        a[n] = x;
        n++;
    }

    int pop() {
        int x = a[n - 1];
        n--;
        return x;
    }

    boolean isEmpty() {
        return n == 0;
    }
}
