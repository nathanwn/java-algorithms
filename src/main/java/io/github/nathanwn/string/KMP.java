package io.github.nathanwn.string;

public class KMP {
    public static int[] calcPrefixFunction(char[] s) {
        int n = s.length;
        int[] prefix = new int[n];
        for (int i = 1; i < n; i++) {
            int j = prefix[i - 1];
            while (j > 0 && s[i] != s[j]) {
                j = prefix[j - 1];
            }
            if (s[i] == s[j]) {
                j++;
            }
            prefix[i] = j;
        }
        return prefix;
    }

    public static int[] calcPrefixFunction(String s) {
        int n = s.length();
        int[] prefix = new int[n];
        for (int i = 1; i < n; i++) {
            int j = prefix[i - 1];
            while (j > 0 && s.charAt(i)!= s.charAt(j)) {
                j = prefix[j - 1];
            }
            if (s.charAt(i) == s.charAt(j)) {
                j++;
            }
            prefix[i] = j;
        }
        return prefix;
    }
}
