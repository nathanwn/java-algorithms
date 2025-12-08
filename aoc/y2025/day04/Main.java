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
        char[][] g = new char[n][];
        for (int i = 0; i < n; i++) {
            g[i] = lines.get(i).toCharArray();
        }
        out.println("part 1: " + part1(g, n, m));
        out.println("part 2: " + part2(g, n, m));
        out.close();
    }

    static int[] dr = {-1, -1, -1,  0, 0,  1, 1, 1};
    static int[] dc = {-1,  0,  1, -1, 1, -1, 0, 1};

    static boolean isGoodCell(char[][] g, int n, int m, int r, int c) {
        int numNeighbors = 0;
        for (int k = 0; k < dr.length; k++) {
            int nr = r + dr[k];
            int nc = c + dc[k];
            if (nr < 0 || nr >= n || nc < 0 || nc >= m) continue;
            if (g[nr][nc] == '@') {
                numNeighbors++;
            }
        }
        return numNeighbors < 4;
    }

    static ArrayList<Cell> getGoodCells(char[][] g, int n, int m) {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (g[r][c] != '@') continue;
                if (isGoodCell(g, n, m, r, c)) {
                    cells.add(new Cell(r, c));
                }
            }
        }
        return cells;
    }

    static int part1(char[][] g, int n, int m) {
        return getGoodCells(g, n, m).size();
    }

    static int part2(char[][] g, int n, int m) {
        char[][] grid = new char[n][];
        for (int i = 0; i < n; i++) {
            grid[i] = Arrays.copyOf(g[i], g[i].length);
        }
        int ans = 0;
        while (true) {
            ArrayList<Cell> goodCells = getGoodCells(grid, n, m);
            int numGoodCells = goodCells.size();
            if (numGoodCells == 0) break;
            ans += numGoodCells;
            for (Cell cell : goodCells) {
                grid[cell.r][cell.c] = '.';
            }
        }
        return ans;
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
