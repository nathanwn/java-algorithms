class Solution {
    public int longestIncreasingPath(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] cells = new int[n * m][];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                cells[i * m + j] = new int[]{i, j};
            }
        }
        Arrays.sort(cells, (int[] xc, int[] yc) -> {
            int x = matrix[xc[0]][xc[1]];
            int y = matrix[yc[0]][yc[1]];
            return Integer.compare(x, y);
        });
        int[][] dp = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], 1);
        }
        final int[] di = {-1, 0, 0, 1};
        final int[] dj = {0, -1, 1, 0};
        for (int[] cell : cells) {
            int i = cell[0];
            int j = cell[1];
            for (int d = 0; d < 4; d++) {
                int ni = di[d] + i;
                int nj = dj[d] + j;
                if (!(0 <= ni && ni < n)) continue;
                if (!(0 <= nj && nj < m)) continue;
                if (matrix[i][j] >= matrix[ni][nj]) continue;
                dp[ni][nj] = Math.max(dp[ni][nj], dp[i][j] + 1);
            }
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ans = Math.max(ans, dp[i][j]);
            }
        }
        return ans;
    }
}
