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
		// even number of nodes in the list
        if (fast == null)
            mid = slow_prev;
		// odd number of nodes in the list
        else
            mid = slow;

        Node second_head = mid.next;
        mid.next = null;

        second_head = reverse(second_head);
        Node n1 = head, n2 = second_head;
		
		// loop through first half and reversed second half checking similarity of two lists
        while (n1 != null && n2 != null) {
            if (n1.data != n2.data)
                break;
            n1 = n1.next;
            n2 = n2.next;
        }
		
		// if any of the halves reaches end
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
		// length of list is 0 or 1
        if (head == null || head.next == null)
            return head;
        Node curr = head;
        while (curr != null) {
            Node temp = curr;
			// move forward till a unique node is found
            while (temp != null && curr.data == temp.data) {
                temp = temp.next;
            }
			// point last unique node to the new unique node
            curr.next = temp;
            curr = temp;
        }
        return head;
    }

    // https://www.interviewbit.com/problems/remove-duplicates-from-sorted-list-ii/
    static Node removeAllDuplicates(Node head) {
		// add a dummy unique node to avoid null
        Node dummy = new Node(0);
        dummy.next = head;

        Node curr = head, prev = dummy;
        while (curr != null) {
			// loop till a node doesn't match with previous unique data
            while (curr.next != null && prev.next.data == curr.next.data)
                curr = curr.next;
			// if no duplicate nodes in between move previous pointer
            if (prev.next == curr)
                prev = prev.next;
			// else link previous pointer to the next unique
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
			// if first list has reached end
            if (head1 == null) {
                tail.next = head2;
                break;
            }
			// if second list has reached end
            if (head2 == null) {
                tail.next = head1;
                break;
            }
			// add first or second list node to end
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
		// move second to nth node from start
        for (int i=0; i<n; i++) {
			// if the list size is smaller than n remove first node
            if (second.next == null) {
                head = head.next;
                return head;
            }
            second = second.next;
        }

		// iterate second till end so first reaches at (len - n)th node from beginning i.e. nth node from end
        while (second.next != null) {
            second = second.next;
            first = first.next;
        }

		// remove nth node from end
        first.next = first.next.next;
        return head;
    }

    // https://www.interviewbit.com/problems/rotate-list/
    static Node rotateRight(Node head, int k) {
        if (head == null)
            return null;
        int len = 1;
        Node tail = head;
		// calculate length and find tail node
        while (tail.next != null) {
            tail = tail.next;
            len ++;
        }
        k = k % len;
		// no need to rotate
        if (k == 0)
            return head;
		// rotate len-k from left
        k = len - k;
		// new tail
        Node tail2 = head;
        for (int i=1; i<k; i++)
            tail2 = tail2.next;
		// new head
        Node head2 = tail2.next;
		// link old tail with head
        tail.next = head;
        tail2.next = null;
        return head2;
    }

    // https://www.interviewbit.com/problems/reverse-link-list-ii/
    static Node reverseBetween(Node head, int m, int n) {
        if (m == n)
            return head;
        Node dummy = new Node(-1);
        dummy.next = head;

        Node prev = dummy;
        for (int i=1; i<m; i++) {
            prev = prev.next;
        }
		// node before the head of sublist to reverse
        Node reversePrev = prev;

        prev = prev.next;
		// head of sublist to reverse
        Node head2 = prev;

        Node curr = head2.next, next;
		// reversing the sublist from m to n
        for (int i=m; i<n; i++) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
		// link head of original sublist to the (m+1)th node
        head2.next = curr;
		// link node before head of original sublist to point to the tail of the original sublist
        reversePrev.next = prev;
        return dummy.next;
    }

    // https://www.interviewbit.com/problems/reorder-list/
    static Node reorderList(Node head) {
        // if list has zero or one elements no reordering needed
        if (head == null || head.next == null)
            return head;

        // find middle of the list
        Node slow = head, fast = head, mid, slow_prev = null;
        while (fast != null && fast.next != null) {
            slow_prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        // if list size is even
        if (fast == null)
            mid = slow_prev;
        // else if list size is odd
        else
            mid = slow;

        // split list into two halves and reverse the second half
        Node head2 = mid.next;
        mid.next = null;
        head2 = reverse(head2);

        // alternate merge of two sublist
        Node n1 = head, n2 = head2, next1, next2;
        while (n1 != null && n2 != null) {
            // add node of sublist 1
            next1 = n1.next;
            n1.next = n2;

            // add node of sublist 2
            next2 = n2.next;
            n2.next = next1;

            // move forward in both sublists
            n1 = next1;
            n2 = next2;
        }
        return head;
    }

    // https://www.interviewbit.com/problems/add-two-numbers-as-lists/
    static Node addTwoNumbers(Node head1, Node head2) {
        // if any list is null
        if (head1 == null)
            return head2;
        if (head2 == null)
            return head1;

        // dummy node to avoid null
        Node dummy = new Node(-1);
        Node head = dummy;
        int carry = 0;

        // till both numbers have same number of digits
        while (head1 != null && head2 != null) {
            int sum = head1.data + head2.data + carry;
            carry = sum / 10;
            sum = sum % 10;
            head.next = new Node(sum);
            head = head.next;
            head1 = head1.next;
            head2 = head2.next;
        }

        // if number 1 has more digits
        while (head1 != null) {
            int sum = head1.data + carry;
            carry = sum / 10;
            sum = sum % 10;
            head.next = new Node(sum);
            head = head.next;
            head1 = head1.next;
        }

        // if number 2 has more digits
        while (head2 != null) {
            int sum = head2.data + carry;
            carry = sum / 10;
            sum = sum % 10;
            head.next = new Node(sum);
            head = head.next;
            head2 = head2.next;
        }

        // if carry is not zero, create new node for carry
        if (carry != 0) {
            head.next = new Node(carry);
        }
        return dummy.next;
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