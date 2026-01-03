class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        int maxRate = 0;
        for (int i = 0; i < piles.length; i++) {
            maxRate = Math.max(maxRate, piles[i]);
        }
        int left = 1;
        int right = maxRate;
        int bestRate = -1;
        while (left <= right) {
            int rate = left + (right - left) / 2;
            long hours = 0;
            for (int i = 0; i < piles.length; i++) {
                hours += (piles[i] + rate - 1) / rate;
            }
            if (hours <= h) {
                bestRate = rate;
                right = rate - 1;
            } else {
                left = rate + 1;
            }
        }
        if (bestRate == -1) throw new AssertionError();
        return bestRate;
    }
}
