/*
Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}
*/

class Solution {
    public Node cloneGraph(Node node) {
        if (node == null) return null;
        HashMap<Node, Node> clones = new HashMap<>();
        ArrayDeque<Node> q = new ArrayDeque<>();
        q.addLast(node);
        clones.put(node, new Node(node.val));
        while (!q.isEmpty()) {
            Node cur = q.removeLast();
            Node clonedCur = clones.get(cur);
            for (Node neighbor : cur.neighbors) {
                Node clonedNeighbor = clones.getOrDefault(neighbor, null);
                if (clonedNeighbor == null) {
                    clonedNeighbor = new Node(neighbor.val);
                    clones.put(neighbor, clonedNeighbor);
                    q.addLast(neighbor);
                }
                clonedCur.neighbors.add(clonedNeighbor);
            }
        }  
        return clones.get(node);
    }
}
