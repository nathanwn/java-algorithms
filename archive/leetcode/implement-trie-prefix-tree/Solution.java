class Trie {
    static class Node {
        int c;
        Node[] children;
        boolean isEndOfWord;

        Node(int c) {
            this();
            this.c = c;
        }

        Node() {
            this.children = new Node[26];
            this.isEndOfWord = false;
        }
    }

    Node root;

    public Trie() {
        root = new Node();
    }

    public void insert(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            int c = word.charAt(i) - 'a';
            if (node.children[c] == null) {
                node.children[c] = new Node(c);
            }
            node = node.children[c];
        }
        node.isEndOfWord = true;
    }

    public boolean search(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            int c = word.charAt(i) - 'a';
            if (node.children[c] == null) {
                return false;
            }
            node = node.children[c];
        }
        return node.isEndOfWord;
    }

    public boolean startsWith(String prefix) {
        Node node = root;
        for (int i = 0; i < prefix.length(); i++) {
            int c = prefix.charAt(i) - 'a';
            if (node.children[c] == null) {
                return false;
            }
            node = node.children[c];
        }
        return true;
    }
}
