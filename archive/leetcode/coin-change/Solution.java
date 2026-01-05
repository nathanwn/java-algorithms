class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] minCoins = new int[amount + 1];
        Arrays.fill(minCoins, Integer.MAX_VALUE);
        minCoins[0] = 0;
        for (int money = 1; money <= amount; money++) {
            for (int coin : coins) {
                int prevMoney = money - coin;
                if (prevMoney < 0) continue;
                if (minCoins[prevMoney] == Integer.MAX_VALUE) continue;
                minCoins[money] = Math.min(minCoins[money], minCoins[prevMoney] + 1);
            }
        }
        int ans = minCoins[amount];
        if (ans == Integer.MAX_VALUE) ans = -1;
        return ans;
    }
}
