public class Solution {
    public static void main(String[] args) {
        int[] A = {1, 2, 3, 4, 5};
        int k = 2;

        LinkedLists.ListNode head = LinkedLists.createLinkedList(A);
        LinkedLists.print(head);

        head = LinkedLists.rotateRight(head, k);
        LinkedLists.print(head);
    }
}