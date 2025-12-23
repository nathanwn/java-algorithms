class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n == 0) return 0;
        int bestBuy = prices[0];
        int bestProfit = 0;
        for (int i = 1; i < n; i++) {
            int sell = prices[i];
            int profit = sell - bestBuy;
            bestProfit = Math.max(bestProfit, profit);
            bestBuy = Math.min(bestBuy, prices[i]);
        }
        return bestProfit;
    }
}
