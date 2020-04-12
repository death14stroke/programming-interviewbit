public class Solution {
    public static void main(String[] args) {

        LinkedList l1 = new LinkedList();
        l1.addNode(1);
        l1.addNode(2);
        l1.addNode(3);
        l1.addNode(4);
        l1.addNode(5);

        LinkedList l2 = new LinkedList();
        l2.addNode(2);
        l2.addNode(3);
        l2.addNode(9);

        LinkedList.print(l1.head);
        //LinkedList.print(l2.head);

        LinkedList.print(LinkedList.reverseBetween(l1.head, 2, 4));
    }
}