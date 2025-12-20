import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        ArrayList<String> lines = new ArrayList<>();
        while (in.hasNext()) {
            lines.add(in.next());
        }
        int n = lines.size();
        int m = lines.get(0).length();
        char[][] g = new char[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                g[i][j] = lines.get(i).charAt(j);
            }
        }

        int startCol = 0;
        for (int j = 0; j < m; j++) {
            if (g[0][j] == 'S') {
                startCol = j;
                break;
            }
        }

        g[0][startCol] = '|';

        boolean[][] visited = new boolean[n][m];

        ArrayDeque<Cell> q = new ArrayDeque<>();
        q.addLast(new Cell(0, startCol));

        long[][] cnt = new long[n][m];
        cnt[0][startCol] = 1;

        int numSplits = 0;
        while (!q.isEmpty()) {
            Cell u = q.pollFirst();
            int r0 = u.r;
            int c0 = u.c;
            if (r0 == n - 1) continue;
            if (g[r0][c0] == '^') {
                numSplits++;
                int r1 = r0 + 1;
                int c1 = c0 - 1;
                int r2 = r0 + 1;
                int c2 = c0 + 1;
                cnt[r1][c1] += cnt[r0][c0];
                cnt[r2][c2] += cnt[r0][c0];
                if (!visited[r1][c1]) {
                    q.addLast(new Cell(r1, c1));
                    visited[r1][c1] = true;
                    g[r1][c1] = '|';
                }
                if (!visited[r2][c2]) {
                    q.addLast(new Cell(r2, c2));
                    visited[r2][c2] = true;
                    g[r2][c2] = '|';
                }
            } else if (g[r0][c0] == '|') {
                int r1 = r0 + 1;
                int c1 = c0;
                cnt[r1][c1] += cnt[r0][c0];
                if (g[r1][c1] == '.') g[r1][c1] = '|';
                if (!visited[r1][c1]) {
                    visited[r1][c1] = true;
                    q.addLast(new Cell(r1, c1));
                }
            } else {
                throw new RuntimeException("" + u.r + "," + u.c);
            }
        }
        long numPaths = 0;
        for (int j = 0; j < m; j++) {
            numPaths += cnt[n - 1][j];
        }

        out.println("part 1: " + numSplits);
        out.println("part 2: " + numPaths);
        out.close();
    }

    static class Cell {
        int r;
        int c;

        Cell(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
}
