class LinkedList {
    Node head;
    private Node tail;

    void addNode(int data) {
        Node node = new Node(data);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
    }

    // https://www.interviewbit.com/problems/palindrome-list/
    boolean isPalindrome() {
        if (head == null || head.next == null)
            return true;

        boolean palindrome = false;
        Node slow = head, fast = head, slow_prev = null, mid;
        while (fast != null && fast.next != null) {
            slow_prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        if (fast == null)
            mid = slow_prev;
        else
            mid = slow;

        Node second_head = mid.next;
        mid.next = null;

        second_head = reverse(second_head);
        Node n1 = head, n2 = second_head;
        while (n1 != null && n2 != null) {
            if (n1.data != n2.data)
                break;
            n1 = n1.next;
            n2 = n2.next;
        }
        if (n1 == null || n2 == null)
            palindrome = true;
        return palindrome;
    }

    static void print(Node head) {
        if (head == null) {
            System.out.println("Empty list");
            return;
        }
        Node curr = head;
        while (curr != null) {
            System.out.print(curr.data);
            if (curr.next != null)
                System.out.print(" -> ");
            curr = curr.next;
        }
        System.out.println();
    }

    static Node reverse(Node head) {
        Node curr = head, prev = null, next;
        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        head = prev;
        return head;
    }

    // https://www.interviewbit.com/problems/remove-duplicates-from-sorted-list/
    static Node removeDuplicates(Node head) {
        if (head == null || head.next == null)
            return head;
        Node curr = head;
        while (curr != null) {
            Node temp = curr;
            while (temp != null && curr.data == temp.data) {
                temp = temp.next;
            }
            curr.next = temp;
            curr = temp;
        }
        return head;
    }

    // https://www.interviewbit.com/problems/remove-duplicates-from-sorted-list-ii/
    static Node removeAllDuplicates(Node head) {
        Node dummy = new Node(0);
        dummy.next = head;

        Node curr = head, prev = dummy;
        while (curr != null) {
            while (curr.next != null && prev.next.data == curr.next.data)
                curr = curr.next;
            if (prev.next == curr)
                prev = prev.next;
            else
                prev.next = curr.next;
            curr = curr.next;
        }
        head = dummy.next;
        return head;
    }

    // https://www.interviewbit.com/problems/merge-two-sorted-lists/
    static Node mergeSortedLinkedLists(Node head1, Node head2) {
        Node dummy = new Node(0);
        Node tail = dummy;
        while (true) {
            if (head1 == null) {
                tail.next = head2;
                break;
            }
            if (head2 == null) {
                tail.next = head1;
                break;
            }
            if (head1.data <= head2.data) {
                tail.next = head1;
                head1 = head1.next;
            } else {
                tail.next = head2;
                head2 = head2.next;
            }
            tail = tail.next;
        }
        return dummy.next;
    }

    // https://www.interviewbit.com/problems/remove-nth-node-from-list-end/
    static Node removeNthFromEnd(Node head, int n) {
        Node first = head, second = head;
        for (int i=0; i<n; i++) {
            if (second.next == null) {
                head = head.next;
                return head;
            }
            second = second.next;
        }

        while (second.next != null) {
            second = second.next;
            first = first.next;
        }

        first.next = first.next.next;
        return head;
    }

    // https://www.interviewbit.com/problems/rotate-list/
    static Node rotateRight(Node head, int k) {
        if (head == null)
            return null;
        int len = 1;
        Node tail = head;
        while (tail.next != null) {
            tail = tail.next;
            len ++;
        }
        k = k % len;
        if (k == 0)
            return head;
        k = len - k;
        Node tail2 = head;
        for (int i=1; i<k; i++)
            tail2 = tail2.next;
        Node head2 = tail2.next;
        tail.next = head;
        tail2.next = null;
        return head2;
    }

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            next = null;
        }
    }
}