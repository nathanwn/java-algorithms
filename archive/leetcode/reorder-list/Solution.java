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
    public void reorderList(ListNode head) {
        if (head == null) return;
        ListNode cur = head;
        int count = 0;
        while (cur.next != null) {
            cur = cur.next;
            count++;
        }
        ListNode half = head;
        // half will be the last node of the first half
        // first half always has at least the same number of elements
        // as the second half
        for (int i = 0; i < count / 2; i++) {
            half = half.next;
        }
        ListNode halfNext = half.next;
        half.next = null;
        ListNode second = reverse(halfNext);
        ListNode first = head;
        while (second != null) {
            ListNode firstNext = first.next;
            ListNode secondNext = second.next;
            first.next = second;
            second.next = firstNext;
            first = firstNext;
            second = secondNext;
        }
    }

    ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }
}
