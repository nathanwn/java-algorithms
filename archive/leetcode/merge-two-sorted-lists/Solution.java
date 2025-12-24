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
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode head = null;
        ListNode cur = null;
        while (list1 != null && list2 != null) {
            ListNode newNode = null;
            if (list1.val < list2.val) {
                newNode = list1;
                list1 = list1.next;
            } else {
                newNode = list2;
                list2 = list2.next;
            }
            if (head == null) {
                head = newNode;
                cur = head;
            } else {
                cur.next = newNode;
                cur = cur.next;
            }
        }
        if (list1 != null) {
            if (head == null) {
                head = list1;
            } else {
                cur.next = list1;
            }
        }
        if (list2 != null) {
            if (head == null) {
                head = list2;
            } else {
                cur.next = list2;
            }
        }
        return head;
    }
}
