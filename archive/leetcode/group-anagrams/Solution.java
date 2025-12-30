class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<List<Integer>, List<String>> m = new HashMap<>();
        for (String s : strs) {
            List<Integer> f = calcFreq(s);
            if (!m.containsKey(f)) {
                m.put(f, new ArrayList<>());
            }
            m.get(f).add(s);
        }
        List<List<String>> res = new ArrayList<>();
        for (Map.Entry<List<Integer>, List<String>> e : m.entrySet()) {
            res.add(e.getValue());
        }
        return res;
    }

    public List<Integer> calcFreq(String s) {
        ArrayList<Integer> freq = new ArrayList<>(26);
        for (int i = 0; i < 26; i++) {
            freq.add(0);
        }
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'a';
            freq.set(c, freq.get(c) + 1);
        }
        return freq;
    }
}
