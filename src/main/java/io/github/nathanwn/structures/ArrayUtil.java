package io.github.nathanwn.structures;

import java.util.Arrays;
import java.util.Random;

public class ArrayUtil {
    public static int unique(int[] a) {
        if (a.length == 0) return 0;
        int j = 1;
        for (int i = 1; i < a.length; i++) {
            if (a[i] != a[i - 1]) {
                a[j] = a[i];
                j++;
            }
        }
        return j;
    }

    public static void sort(int[] a, Random random) {
        shuffle(a, random);
        Arrays.sort(a);
    }

    static void shuffle(int[] a, Random random) {
        // Fisher-Yates shuffle
        for (int i = a.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

    static int search(int[] a, int n, int x) {
        int left = 0;
        int right = n - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (x == a[mid]) {
                return mid;
            } else if (x < a[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        throw new RuntimeException();
    }
}
