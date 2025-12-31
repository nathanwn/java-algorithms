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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        return addTwoNumbers(l1, l2, 0);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2, int carry) {
        if (l1 == null && l2 == null) {
            if (carry > 0) {
                return new ListNode(carry);
            } else {
                return null;
            }
        }
        int val = 0;
        if (l1 == null) {
            val = l2.val + carry;
        } else if (l2 == null) {
            val = l1.val + carry;
        } else {
            val = l1.val + l2.val + carry;
        }
        int newCarry = 0;
        if (val > 9) {
            val -= 10;
            newCarry = 1;
        }
        ListNode res = new ListNode(val);
        ListNode l1Next = (l1 == null) ? null : l1.next;
        ListNode l2Next = (l2 == null) ? null : l2.next;
        res.next = addTwoNumbers(l1Next, l2Next, newCarry);
        return res;
    }
}
