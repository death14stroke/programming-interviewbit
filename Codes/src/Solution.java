public class Solution {
    public static void main(String[] args) {
        LinkedList l = new LinkedList();
        l.addNode(1);
        l.addNode(2);
        l.addNode(3);
        l.addNode(4);

        LinkedList.print(l.head);
        LinkedList.print(LinkedList.evenReverse(l.head));
    }
}