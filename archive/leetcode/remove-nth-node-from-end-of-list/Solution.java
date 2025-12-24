/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) return null;
        int count = 0;
        {
            ListNode cur = head;
            while (cur != null) {
                cur = cur.next;
                count++;
            }
        }
        {
            int pos = count - n;
            ListNode prev = null;
            ListNode cur = head;
            for (int i = 0; i < pos; i++) {
                prev = cur;
                cur = cur.next;
            }
            if (cur == head) {
                head = head.next;
            } else {
                prev.next = cur.next;
            }
        }
        return head;
    }
}
