public class Solution {
    public static void main(String[] args) {

        LinkedList l1 = new LinkedList();
        l1.addNode(1);
        l1.addNode(1);
        l1.addNode(1);
        l1.addNode(2);
        l1.addNode(3);
        l1.addNode(3);

        LinkedList.print(l1.head);
        System.out.println("removeDuplicates: ");
        LinkedList.print(LinkedList.removeAllDuplicates(l1.head));
    }
}