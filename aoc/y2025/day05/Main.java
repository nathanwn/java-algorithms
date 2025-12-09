import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        ArrayList<Range> ranges = new ArrayList<>();
        while (true) {
            String line = in.nextLine();
            if (line.length() == 0) break;
            String[] rangeParts = line.split("-");
            long first = Long.parseLong(rangeParts[0]);
            long last = Long.parseLong(rangeParts[1]);
            ranges.add(new Range(first, last));
        }
        ArrayList<Long> ids = new ArrayList<>();
        while (in.hasNext()) {
            ids.add(Long.parseLong(in.next()));
        }
        out.println("part 1: " + part1(ranges, ids));
        out.println("part 2: " + part2(ranges, ids));
        out.close();
    }

    static boolean isFresh(long id, ArrayList<Range> ranges) {
        for (Range range : ranges) {
            if (range.first <= id && id <= range.last) {
                return true;
            }
        }
        return false;
    }

    static long part1(ArrayList<Range> ranges, ArrayList<Long> ids) {
        long ans = 0;
        for (long id : ids) {
            if (isFresh(id, ranges)) {
                ans++;
            }
        }
        return ans;
    }

    static long part2(ArrayList<Range> ranges, ArrayList<Long> ids) {
        Range x = new Range(10, 14);
        Range y = new Range(12, 18);
        int n = ranges.size();
        DSU dsu = new DSU(n);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (ranges.get(i).overlaps(ranges.get(j))) {
                    dsu.merge(i, j);
                }
            }
        }
        ArrayList<ArrayList<Integer>> groups = dsu.getGroups();
        long ans = 0;
        for (ArrayList<Integer> group : groups) {
            Range range = mergeGroup(ranges, group);
            ans += range.last - range.first + 1;
        }
        return ans;
    }

    static Range mergeGroup(ArrayList<Range> allRanges, ArrayList<Integer> group) {
        int first = 0;
        int last = 0;
        Range res = allRanges.get(group.get(0));
        for (int j = 1; j < group.size(); j++) {
            Range cur = allRanges.get(group.get(j));
            res = res.merge(cur);
        }
        return res;
    }

    static class DSU {
        int n;
        int[] p;

        DSU(int n) {
            this.n = n;
            p = new int[n];
            for (int i = 0; i < n; i++) {
                p[i] = i;
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
            p[u] = v;
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

    static class Range {
        long first;
        long last;

        Range(long first, long last) {
            this.first = first;
            this.last = last;
        }

        boolean overlaps(Range other) {
            return (other.first <= first && first <= other.last)
                || (other.first <= last && last <= other.last)
                || (first <= other.first && other.first <= last)
                || (first <= other.last && other.last <= last);
        }

        Range merge(Range other) {
            return new Range(
                    Math.min(this.first, other.first),
                    Math.max(this.last, other.last)
            );
        }

        @Override
        public String toString() {
            return "(" + this.first + ", " + this.last + ")";
        }
    }
}
