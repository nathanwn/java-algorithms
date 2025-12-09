import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        ArrayList<Point> ps = new ArrayList<>();
        while (in.hasNext()) {
            Integer[] coords = Arrays.asList(in.next().split(","))
                .stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList())
                .toArray(new Integer[0]);
            ps.add(new Point(coords[0], coords[1], coords[2], ps.size()));
        }
        ArrayList<Segment> segments = new ArrayList<>();
        for (int i = 0; i < ps.size(); i++) {
            for (int j = i + 1; j < ps.size(); j++) {
                segments.add(new Segment(ps.get(i), ps.get(j)));
            }
        }
        Collections.sort(segments, (s1, s2) -> Long.compare(s1.abs2(), s2.abs2()));
        int n = ps.size();
        DSU dsu = new DSU(n);
        final int LIMIT = 1000;
        int cnt = 0;
        for (int i = 0; i < LIMIT; i++) {
            Segment s = segments.get(i);
            dsu.merge(s.pi.id, s.pj.id);
        }

        ArrayList<ArrayList<Integer>> groups = dsu.getGroups();
        Collections.sort(groups, (g1, g2) -> -Long.compare(g1.size(), g2.size()));
        long ans1 = 1;
        for (int i = 0; i < 3; i++) {
            ans1 *= groups.get(i).size();
        }

        Segment last = null;
        for (int i = LIMIT; i < segments.size(); i++) {
            Segment s = segments.get(i);
            if (dsu.merge(s.pi.id, s.pj.id)) {
                last = s;
            }
        }
        long ans2 = 1L * last.pi.x * last.pj.x;

        out.println("part 1: " + ans1);
        out.println("part 2: " + ans2);
        out.close();
    }

    static class Point {
        int x;
        int y;
        int z;
        int id;

        Point(int x, int y, int z, int id) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.id = id;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + "," + z + ")";
        }
    }

    static class Segment {
        Point pi;
        Point pj;

        Segment(Point pi, Point pj) {
            this.pi = pi;
            this.pj = pj;
        }

        long abs2() {
            int dx = pi.x - pj.x;
            int dy = pi.y - pj.y;
            int dz = pi.z - pj.z;
            return 1L * dx * dx + 1L * dy * dy + 1L * dz * dz;
        }

        @Override
        public String toString() {
            return pi + " " + pj;
        }

    }

    static class DSU {
        int n;
        int[] p;
        int[] d;

        DSU(int n) {
            this.n = n;
            p = new int[n];
            d = new int[n];
            for (int i = 0; i < n; i++) {
                p[i] = i;
                d[i] = 0;
            }
        }

        int find(int u) {
            while (u != p[u]) u = p[u];
            return u;
        }

        boolean merge(int u, int v) {
            u = find(u);
            v = find(v);
            if (u == v) return false;
            if (d[u] == d[v]) {
                p[u] = v;
                d[v]++;
            } else if (d[u] < d[v]) {
                p[u] = v;
            } else {
                p[v] = u;
            }
            return true;
        }

        ArrayList<ArrayList<Integer>> getGroups() {
            ArrayList<ArrayList<Integer>> allGroups = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                allGroups.add(new ArrayList<>());
            }
            for (int i = 0; i < n; i++) {
                int root = find(i);
                allGroups.get(root).add(i);
            }
            ArrayList<ArrayList<Integer>> groups = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (allGroups.get(i).size() > 0) {
                    groups.add(allGroups.get(i));
                }
            }
            return groups;
        }
    }
}
