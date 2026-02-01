class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        // dp[i]: whether the prefix [0, i) can be formed from wordDict 
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for (int i = 0; i < n; i++) {
            for (String word : wordDict) {
                boolean endsWithWord = true;
                if (i + 1 - word.length() < 0 || !dp[i + 1 - word.length()]) {
                    continue;
                }
                for (int k = 0; k < word.length(); k++) {
                    int j = i + 1 - word.length() + k;
                    if (s.charAt(j) != word.charAt(k)) {
                        endsWithWord = false;
                        break;
                    }
                }
                if (endsWithWord) {
                    dp[i + 1] = true;
                    break;
                }
            }
        }
        return dp[n];
    }
}
