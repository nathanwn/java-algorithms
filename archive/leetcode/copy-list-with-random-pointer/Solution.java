/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/

class Solution {
    public Node copyRandomList(Node head) {
        if (head == null) return null;
        Node newHead = new Node(head.val);
        newHead.random = head.random;
        HashMap<Node, Node> oldToNew = new HashMap<>();
        oldToNew.put(head, newHead);
        Node cur = head;
        Node newCur = newHead;
        while (cur.next != null) {
            newCur.next = new Node(cur.next.val);
            cur = cur.next;
            newCur = newCur.next;
            newCur.random = cur.random;
            oldToNew.put(cur, newCur);
        }
        newCur = newHead;
        while (newCur != null) {
            newCur.random = oldToNew.get(newCur.random);
            newCur = newCur.next;
        }
        return newHead;
    }
}
