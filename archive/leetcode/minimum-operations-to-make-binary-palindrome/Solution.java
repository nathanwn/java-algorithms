class Solution {
    public int[] minOperations(int[] nums) {
        int[] ans = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            int prev = prevBinaryPalindrome(x);
            int next = nextBinaryPalindrome(x);
            ans[i] = Math.min(x - prev, next - x);
        }
        return ans;
    }

    int prevBinaryPalindrome(int x) {
        while (!isBinaryPalindrome(x)) {
            x--;
        }
        return x;
    }

    int nextBinaryPalindrome(int x) {
        while (!isBinaryPalindrome(x)) {
            x++;
        }
        return x;
    }

    boolean isBinaryPalindrome(int x) {
        int b = x;
        int nBits = 0;
        while (b > 0) {
            b >>= 1;
            nBits++;
        }
        for (int i = 0; i < nBits / 2; i++) {
            int leftBit = (x >> i) & 1;
            int rightBit = (x >> (nBits - i - 1)) & 1;
            if (leftBit != rightBit) {
                return false;
            }
        }
        return true;
    }
}
