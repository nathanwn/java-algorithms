class Solution {
    public int longestIncreasingPath(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        Solver solver = new Solver(matrix);
        return solver.solve();
    }
}

class Solver {
    static final int[] dr = {-1, 0, 0, 1};
    static final int[] dc = {0, -1, 1, 0};
    int n;
    int m;
    int[][] matrix;
    int[][] mark;
    int[][] dp;
    int ans;

    Solver(int[][] matrix) {
        n = matrix.length;
        m = matrix[0].length;
        this.matrix = matrix;
        mark = new int[n][m];
        dp = new int[n][m];
    }

    int solve() {
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (mark[r][c] == 0) {
                    dfs(r, c);
                }
            }
        }
        return ans + 1;
    }

    void dfs(int r, int c) {
        mark[r][c] = 1;
        for (int k = 0; k < dr.length; k++) {
            int nr = r + dr[k];
            int nc = c + dc[k];
            if (!(0 <= nr && nr < n)) continue;
            if (!(0 <= nc && nc < m)) continue;
            if (matrix[r][c] < matrix[nr][nc]) {
                if (mark[nr][nc] == 0) {
                    dfs(nr, nc);
                }
                dp[r][c] = Math.max(dp[r][c], dp[nr][nc] + 1);
                ans = Math.max(ans, dp[r][c]);
            }
        }
        mark[r][c] = 2;
    }
}
