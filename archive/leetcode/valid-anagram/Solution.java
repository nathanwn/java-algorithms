class Solution {
    public boolean isAnagram(String s, String t) {
        return Arrays.equals(calcFreq(s), calcFreq(t));
    }

    public int[] calcFreq(String s) {
        int[] freq = new int[26];
        for (int i = 0; i < s.length(); i++) {
            freq[s.charAt(i) - 'a']++;
        }
        return freq;
    }
}
