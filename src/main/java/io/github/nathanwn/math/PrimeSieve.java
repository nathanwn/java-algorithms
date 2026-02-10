package io.github.nathanwn.math;

import io.github.nathanwn.structure.IntList;

import java.util.Arrays;

public class PrimeSieve {
    int max;
    public boolean[] isPrime;
    public IntList primes;

    public PrimeSieve(int max) {
        this.max = max;
        this.isPrime = new boolean[max + 1];
        this.primes = new IntList();
        Arrays.fill(isPrime, true);
        for (int i = 2; i * i <= max; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= max; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        for (int i = 2; i <= max; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }
    }
}
