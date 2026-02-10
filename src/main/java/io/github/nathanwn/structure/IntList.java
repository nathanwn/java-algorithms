package io.github.nathanwn.structure;

import java.util.Arrays;

public class IntList {
    int cap;
    int n;
    int[] a;

    public IntList() {
        this(16);
    }

    public IntList(int cap) {
        this.cap = cap;
        this.n = 0;
        this.a = new int[cap];
    }

    public int size() {
        return n;
    }

    public int get(int i) {
        return a[i];
    }

    public void add(int x) {
        if (n == cap) {
            cap <<= 1;
            a = Arrays.copyOf(a, cap);
        }
        a[n] = x;
        n++;
    }

    public int pop() {
        int x = a[n - 1];
        n--;
        return x;
    }

    public boolean isEmpty() {
        return n == 0;
    }
}
