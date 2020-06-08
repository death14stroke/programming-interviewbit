import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) {
        Integer[] a1 = {39, 99, 70, 24, 49, 13, 86, 43, 88, 74, 45, 92, 72, 71, 90, 32, 19, 76, 84, 46, 63, 15, 87, 1, 39, 58, 17, 65, 99, 43, 83, 29, 64, 67, 100, 14, 17, 100, 81, 26, 45, 40, 95, 94, 86, 2, 89, 57, 52, 91, 45};
        Integer[] b1 = {1221, 360, 459, 651, 958, 584, 345, 181, 536, 116, 1310, 403, 669, 1044, 1281, 711, 222, 280, 1255, 257, 811, 409, 698, 74, 838};

        double val = Math.pow(2, 100000);
        System.out.println("2^1e5="+val);

        ArrayList<Integer> a = Array.toIntArrayList(a1), b = Array.toIntArrayList(b1);
        Array.printArrayList(a);
        Array.printArrayList(b);
        ArrayList<Integer> out = Array.solve(a, b);
        Array.printArrayList(out);
    }

    private void runLinkedListCodes() {
        int[] values = {339, 571, 654, 888, 429, 636, 30, 958, 627, 310, 709, 544, 798, 546, 442, 585, 373, 801, 423,
                660, 123, 694, 107, 437, 44, 303, 72, 284, 796, 983, 653, 28, 466, 363, 840, 29, 298, 86, 21, 637, 170,
                70, 505, 334, 822, 73, 164, 741, 894, 420, 999, 670, 88, 987, 679, 690, 816, 568, 525, 462, 463, 433,
                991, 752, 405, 98, 364, 422, 162, 312, 924, 762, 321, 728, 977, 138, 973, 496, 873, 380, 672, 14};

        LinkedList l1 = new LinkedList();
        for (int val : values) {
            l1.addNode(val);
        }

        l1.addNode(1);
        l1.addNode(4);
        l1.addNode(3);
        l1.addNode(2);
        l1.addNode(5);
        l1.addNode(2);


        //l1.tail.next = l1.head.next.next;
        //l1.addNode(4);
        //l1.addNode(5);
        //l1.addNode(6);

        LinkedList l2 = new LinkedList();
        l2.addNode(0);
        l2.addNode(1);
        l2.head.next.next = l1.head.next.next.next;
        //l2.addNode(4);

        LinkedList.print(l1.head);
        LinkedList.print(l2.head);

        //LinkedList.Node loopStart = LinkedList.detectCycle(l1.head);
        //System.out.println("The loop begins at: "+ (loopStart != null ? loopStart.data : "null"));
        System.out.println("Intersection node = " + LinkedList.getIntersectionNode(l1.head, l2.head).data);
    }
}