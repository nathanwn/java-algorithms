class Solution {
    public String foreignDictionary(String[] words) {
        final int ALPHA = 26;
        int[] ids = new int[ALPHA];
        HashMap<Character, Integer> charToId = new HashMap<>();
        char[] idToChar = new char[ALPHA];
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (!charToId.containsKey(c)) {
                    int id = charToId.size();
                    charToId.put(c, id);
                    idToChar[id] = c;
                    adj.add(new ArrayList<>());
                }
            }
        }
        for (int i = 1; i < words.length; i++) {
            String s = words[i - 1];
            String t = words[i];
            // Special case where given word list is invalid
            if (s.length() > t.length()) {
                String ss = s.substring(0, t.length());
                if (ss.equals(t)) {
                    return "";
                }
            }
            int minLength = Math.min(s.length(), t.length());
            for (int j = 0; j < minLength; j++) {
                if (s.charAt(j) != t.charAt(j)) {
                    int u = charToId.get(s.charAt(j));
                    int v = charToId.get(t.charAt(j));
                    adj.get(u).add(v);
                    break;
                }
            }
        }
        Solver solver = new Solver(adj);
        List<Integer> order = solver.sort();
        StringBuilder alphabet = new StringBuilder();
        for (int u : order) {
            char c = idToChar[u];
            alphabet.append(c);
        }
        return alphabet.toString();
    }
}

class Solver {
    int n;
    ArrayList<ArrayList<Integer>> adj;
    int[] mark;
    ArrayList<Integer> order;
    boolean invalid;

    Solver(ArrayList<ArrayList<Integer>> adj) {
        n = adj.size();
        mark = new int[n];
        this.adj = adj;
        this.order = new ArrayList<>(n);
    }

    List<Integer> sort() {
        for (int u = 0; u < n; u++) {
            if (mark[u] == 0) {
                dfs(u);
            }
            if (invalid) return new ArrayList<>();
        }
        Collections.reverse(order);
        return order;
    }

    void dfs(int u) {
        mark[u] = 1;
        for (int v : adj.get(u)) {
            if (mark[v] == 1) {  // back-edge
                invalid = true;
                return;
            } else if (mark[v] == 0) {
                dfs(v);
            }
        }
        mark[u] = 2;
        order.add(u);
    }
}
