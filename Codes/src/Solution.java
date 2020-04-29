public class Solution {
    public static void main(String[] args) {
        int[] a = {444, 994, 508, 72, 125, 299, 181, 238, 354, 223, 691, 249, 838, 890, 758, 675, 424, 199, 201, 788, 609, 582, 979, 259, 901, 371, 766, 759, 983, 728, 220, 16, 158, 822, 515, 488, 846, 321, 908, 469, 84, 460, 961, 285, 417, 142, 952, 626, 916, 247, 116, 975, 202, 734, 128, 312, 499, 274, 213, 208, 472, 265, 315, 335, 205, 784, 708, 681, 160, 448, 365, 165, 190, 693, 606, 226, 351, 241, 526, 311, 164, 98, 422, 363, 103, 747, 507, 669, 153, 856, 701, 319, 695, 52};

        Array.printArray(a);
        System.out.println("Next permutation = ");
        Array.printArray(Array.nextPermutation(a));
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