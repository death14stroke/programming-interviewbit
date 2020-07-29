class LinkedLists {
    // https://www.interviewbit.com/problems/intersection-of-linked-lists/
    static ListNode getIntersectionNode(ListNode head1, ListNode head2) {
        // calculate length of both lists
        int m = length(head1), n = length(head2);
        int d = Math.abs(m - n);

        // skip ahead to the difference in the longer linked list
        if (m >= n) {
            for (int i = 0; i < d; i++)
                head1 = head1.next;
        } else {
            for (int i = 0; i < d; i++)
                head2 = head2.next;
        }

        // keep traversing till intersection point
        while (head1 != head2) {
            head1 = head1.next;
            head2 = head2.next;
        }

        return head1;
    }

    // util to get length of linked list
    private static int length(ListNode head) {
        int len = 0;
        ListNode curr = head;

        // keep traversing and update length
        while (curr != null) {
            len++;
            curr = curr.next;
        }

        return len;
    }

    // https://www.interviewbit.com/problems/reverse-linked-list/
    static ListNode reverse(ListNode head) {
        ListNode curr = head, prev = null;

        while (curr != null) {
            // save reference to next pointer
            ListNode next = curr.next;

            // reverse the link from curr to next to curr to prev
            curr.next = prev;

            // update prev and curr pointers
            prev = curr;
            curr = next;
        }

        // reset head as the tail node of original list
        head = prev;

        return head;
    }

    // https://www.interviewbit.com/problems/palindrome-list/
    static int isPalindrome(ListNode head) {
        // 0 or 1 nodes in linked list always palindrome
        if (head == null || head.next == null)
            return 1;

        ListNode slow = head, fast = head, slow_prev = null, mid;

        // tortoise and hare traversal
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

        // break linked list from the middle
        ListNode head2 = mid.next;
        mid.next = null;
        // reverse the second half
        head2 = reverse(head2);

        ListNode n1 = head, n2 = head2;
        // loop through first half and reversed second half checking similarity of two lists
        while (n1 != null && n2 != null) {
            if (n1.val != n2.val)
                return 0;

            n1 = n1.next;
            n2 = n2.next;
        }

        // if any of the halves reaches end, it is palindrome
        return 1;
    }

    // https://www.interviewbit.com/problems/remove-duplicates-from-sorted-list/
    static ListNode removeDuplicates(ListNode head) {
        ListNode curr = head;
        while (curr != null) {
            // move forward till a unique node is found
            while (curr.next != null && curr.val == curr.next.val)
                curr.next = curr.next.next;

            // point last unique node to the new unique node
            curr = curr.next;
        }

        return head;
    }

    // https://www.interviewbit.com/problems/remove-duplicates-from-sorted-list-ii/
    static ListNode removeAllDuplicates(ListNode head) {
        // add a dummy unique node to avoid null
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode curr = head, prev = dummy;
        while (curr != null) {
            // loop till a node doesn't match with previous unique data
            while (curr.next != null && prev.next.val == curr.next.val)
                curr = curr.next;

            // if no duplicate nodes in between move previous pointer
            if (prev.next == curr)
                prev = prev.next;
                // else link previous pointer to the next unique
            else
                prev.next = curr.next;

            curr = curr.next;
        }

        return dummy.next;
    }

    // https://www.interviewbit.com/problems/merge-two-sorted-lists/
    static ListNode mergeSortedLinkedLists(ListNode head1, ListNode head2) {
        // create new empty list with dummy node as head
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

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
            if (head1.val <= head2.val) {
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
    static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode first = head, second = head;

        // move second pointer to nth node from start
        for (int i = 0; i < n; i++) {
            // if the list size is less than or equal than n remove first node
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
        //noinspection ConstantConditions
        first.next = first.next.next;

        return head;
    }

    // https://www.interviewbit.com/problems/k-reverse-linked-list/
    static ListNode reverseList(ListNode head, int k) {
        // dummy node for previous node to start as non-null
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode curr = head, prev = dummy;

        while (curr != null) {
            ListNode temp = curr, oldPrev = prev;

            // reverse k nodes from curr
            for (int i = 0; i < k; i++) {
                ListNode next = curr.next;
                curr.next = prev;

                prev = curr;
                curr = next;
            }

            // prev of the old head will now point to the new head
            oldPrev.next = prev;
            // old head will now point to the (k + 1)th node
            temp.next = curr;

            // update previous pointer to old head (new tail)
            prev = temp;
        }

        return dummy.next;
    }

    // https://www.interviewbit.com/problems/swap-list-nodes-in-pairs/
    static ListNode swapPairs(ListNode head) {
        // create dummy node for non-null prev
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        // pair1 and pair2 are nodes to be swapped. prev is node before pair1 and next is node after pair2
        ListNode pair1 = head, prev = dummy, pair2, next;

        // looping two nodes at a time
        while (pair1 != null && pair1.next != null) {
            pair2 = pair1.next;
            next = pair2.next;

            // prev will be before the node after pair1 which is to be swapped
            prev.next = pair2;
            // pair2 will be now before pair1
            pair2.next = pair1;
            // pair1 will now be before the node after pair2 in the original list
            pair1.next = next;

            // moving to next pair. pair1 will now be at pair2's position hence prev will point to pair1
            // and pair1 will point to node next to pair2 originally
            prev = pair1;
            pair1 = next;
        }

        return dummy.next;
    }

    // https://www.interviewbit.com/problems/rotate-list/
    static ListNode rotateRight(ListNode head, int k) {
        if (head == null)
            return null;

        int len = 1;
        ListNode tail = head;
        // calculate length and find tail node
        while (tail.next != null) {
            tail = tail.next;
            len++;
        }

        k = k % len;
        // no need to rotate
        if (k == 0)
            return head;

        // rotate len-k from left
        k = len - k;
        // new tail
        ListNode tail2 = head;
        for (int i = 1; i < k; i++)
            tail2 = tail2.next;

        // new head
        ListNode head2 = tail2.next;
        // link old tail with head
        tail.next = head;
        tail2.next = null;

        return head2;
    }

    // https://www.interviewbit.com/problems/add-two-numbers-as-lists/
    static ListNode addTwoNumbers(ListNode head1, ListNode head2) {
        // if any list is null
        if (head1 == null)
            return head2;
        if (head2 == null)
            return head1;

        // dummy node to avoid null head in output list
        ListNode dummy = new ListNode(-1);
        ListNode head = dummy;

        int carry = 0;
        // till both numbers have same number of digits
        while (head1 != null && head2 != null) {
            int sum = head1.val + head2.val + carry;
            carry = sum / 10;
            sum = sum % 10;

            head.next = new ListNode(sum);
            head = head.next;

            head1 = head1.next;
            head2 = head2.next;
        }

        // if number 1 has more digits
        while (head1 != null) {
            int sum = head1.val + carry;
            carry = sum / 10;
            sum = sum % 10;

            head.next = new ListNode(sum);
            head = head.next;

            head1 = head1.next;
        }

        // if number 2 has more digits
        while (head2 != null) {
            int sum = head2.val + carry;
            carry = sum / 10;
            sum = sum % 10;

            head.next = new ListNode(sum);
            head = head.next;

            head2 = head2.next;
        }

        // if carry is not zero, create new node for carry
        if (carry != 0)
            head.next = new ListNode(carry);

        return dummy.next;
    }

    // https://www.interviewbit.com/problems/list-cycle/
    static ListNode detectCycle(ListNode head) {
        // zero or single node in the list
        if (head == null || head.next == null)
            return null;

        ListNode slow = head, fast = head;
        // move one step for loop to run
        slow = slow.next;
        fast = fast.next.next;

        // move both pointers till they meet or fast pointer reaches null
        while (fast != null && fast.next != null) {
            // found the meeting point
            if (slow == fast)
                break;

            slow = slow.next;
            fast = fast.next.next;
        }

        // pointers didn't meet as fast pointer reached null hence no loop
        if (slow != fast)
            return null;

        // reset slow pointer to head and keep fast pointer at the meeting point
        slow = head;

        // while both pointers don't meet again
        while (slow != fast) {
            slow = slow.next;
            //noinspection ConstantConditions
            fast = fast.next;
        }

        return slow;
    }

    // https://www.interviewbit.com/problems/partition-list/
    static ListNode partition(ListNode head, int x) {
        // dummy head nodes for two lists: one with less than x and other with the rest
        ListNode smallerDummy = new ListNode(-1), greaterDummy = new ListNode(-1);
        // head nodes for both lists
        ListNode smallerHead = smallerDummy, greaterHead = greaterDummy, curr = head;

        while (curr != null) {
            // append at the end of smaller than x list
            if (curr.val < x) {
                smallerHead.next = curr;
                smallerHead = smallerHead.next;
            }
            // append at the end of greater than or equal to x
            else {
                greaterHead.next = curr;
                greaterHead = greaterHead.next;
            }

            curr = curr.next;
        }

        // make sure the greater than or equal to x list has an end
        greaterHead.next = null;
        // join the two list
        smallerHead.next = greaterDummy.next;

        return smallerDummy.next;
    }

    // https://www.interviewbit.com/problems/insertion-sort-list/
    static ListNode insertionSort(ListNode head) {
        // if 0 or 1 node in linked list
        if (head == null || head.next == null)
            return head;

        // create dummy node to avoid null in previous node pointers
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode curr = head.next, prev = head;
        // check for each node starting from pos 1 if it should be inserted or not
        while (curr != null) {
            ListNode j = dummy.next, tempPrev = dummy;

            // look for position to insert current node in the previous half of the list which is sorted
            while (j != null && j.val < curr.val) {
                tempPrev = j;
                j = j.next;
            }

            ListNode next = curr.next;
            // if found a position for current node to be inserted
            if (j != curr) {
                tempPrev.next = curr;
                curr.next = j;
                prev.next = next;
            }
            // else don't insert and update prev pointer
            // (in above case prev pointer will remain same)
            else
                prev = curr;

            // update current pointer
            curr = next;
        }

        return dummy.next;
    }

    // https://www.interviewbit.com/problems/sort-list/
    static ListNode sortList(ListNode head) {
        return mergeSort(head);
    }

    // util to perform mergesort on linked list
    private static ListNode mergeSort(ListNode head) {
        // if 0 or 1 nodes in linked list
        if (head == null || head.next == null)
            return head;

        ListNode slow = head, fast = head;
        // find the mid pointer (slow)
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // break the list from the middle
        ListNode head2 = slow.next;
        slow.next = null;

        // merge the two halves after recursively sorting them using mergeSort
        return mergeSortedLinkedLists(mergeSort(head), mergeSort(head2));
    }

    // https://www.interviewbit.com/problems/reverse-link-list-ii/
    static ListNode reverseBetween(ListNode head, int m, int n) {
        // no need to reverse
        if (m == n)
            return head;

        // create dummy node to avoid null previous pointer
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        // skip m positions
        ListNode prev = dummy;
        for (int i = 1; i < m; i++)
            prev = prev.next;

        // node before the head of sublist to reverse
        ListNode reversePrev = prev;

        prev = prev.next;
        // head of sublist to reverse
        ListNode head2 = prev;

        ListNode curr = head2.next;
        // reversing the sublist from m to n
        for (int i = m; i < n; i++) {
            ListNode next = curr.next;
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
    static ListNode reorderList(ListNode head) {
        // if list has zero or one elements no reordering needed
        if (head == null || head.next == null)
            return head;

        // find middle of the list
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // split list into two halves and reverse the second half
        ListNode head2 = slow.next;
        slow.next = null;
        head2 = reverse(head2);

        // alternate merge of two sublist
        ListNode n1 = head, n2 = head2;
        while (n1 != null && n2 != null) {
            // add node of sublist 1
            ListNode next1 = n1.next;
            n1.next = n2;

            // add node of sublist 2
            ListNode next2 = n2.next;
            n2.next = next1;

            // move forward in both sublists
            n1 = next1;
            n2 = next2;
        }

        return head;
    }

    // https://www.interviewbit.com/problems/reverse-alternate-k-nodes/
    static ListNode reverseAlternateK(ListNode head, int k) {
        // create dummy node to avoid null previous pointer
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode prev = dummy, curr = head;
        // process 2k nodes at a time
        while (curr != null) {
            ListNode tempPrev = prev, temp = curr;

            // reverse the first set of k nodes
            for (int i = 0; i < k; i++) {
                ListNode next = curr.next;
                curr.next = prev;

                prev = curr;
                curr = next;
            }

            // join the nodes before and after the reversed part
            tempPrev.next = prev;
            temp.next = curr;

            // update previous pointer
            prev = temp;

            // skip next k nodes if present
            if (curr != null) {
                for (int i = 0; i < k; i++) {
                    prev = curr;
                    curr = curr.next;
                }
            }
        }

        return dummy.next;
    }

    // https://www.interviewbit.com/problems/kth-node-from-middle/
    static int kthFromMiddle(ListNode head, int k) {
        ListNode curr = head;
        int n = 0;
        // calculate length of the linked list
        while (curr != null) {
            curr = curr.next;
            n++;
        }

        // kth node from middle (n/2 + 1) towards beginning = (n/2) + 1 - k from the beginning
        k = (n / 2) + 1 - k;
        // if k is not valid
        if (k < 1)
            return -1;

        // move k positions from the beginning
        curr = head;
        while (k > 1) {
            curr = curr.next;
            k--;
        }

        return curr.val;
    }

    // https://www.interviewbit.com/problems/sort-binary-linked-list/
    static ListNode sortBinaryList(ListNode head) {
        // if 0 or 1 nodes in the list
        if (head == null || head.next == null)
            return head;

        // dummy nodes to avoid null pointers
        ListNode dummy0 = new ListNode(-1), dummy1 = new ListNode(-1);
        // pointers to two sublists one for each zeroes and ones
        ListNode zero = dummy0, one = dummy1, curr = head;

        // traverse the list
        while (curr != null) {
            // if node is 0, link it to the zeroes list
            if (curr.val == 0) {
                zero.next = curr;
                zero = zero.next;
            }
            // else link it to the ones list
            else {
                one.next = curr;
                one = one.next;
            }

            curr = curr.next;
        }

        // connect zeroes list to the ones list
        zero.next = dummy1.next;
        // make sure ones list has an end
        one.next = null;

        return dummy0.next;
    }

    // https://www.interviewbit.com/problems/even-reverse/
    static ListNode evenReverse(ListNode head) {
        // if 0 or 1 node in the list
        if (head == null || head.next == null)
            return head;

        // create odd position nodes sublist
        ListNode odd = head, tempOdd = odd;
        head = head.next;

        // create even position nodes sublist
        ListNode even = head, tempEven = even;
        head = head.next;

        boolean oddPos = true;
        // keep adding each node to odd and even positions list alternately
        while (head != null) {
            if (oddPos) {
                tempOdd.next = head;
                tempOdd = tempOdd.next;
            } else {
                tempEven.next = head;
                tempEven = tempEven.next;
            }

            head = head.next;
            oddPos = !oddPos;
        }

        // make sure both sublists have an end
        tempOdd.next = null;
        tempEven.next = null;

        // reverse the even positions sublist
        even = reverse(even);

        // create dummy node for resultant list by merging odd and reversed even positions sublists
        ListNode dummy = new ListNode(-1);
        head = dummy;
        oddPos = true;

        // merge both lists alternately
        while (odd != null || even != null) {
            if (oddPos) {
                head.next = odd;
                odd = odd.next;
            } else {
                head.next = even;
                even = even.next;
            }

            head = head.next;
            oddPos = !oddPos;
        }

        return dummy.next;
    }

    ListNode head;
    ListNode tail;

    void addNode(int data) {
        ListNode node = new ListNode(data);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
    }

    static void print(ListNode head) {
        if (head == null) {
            System.out.println("Empty list");
            return;
        }
        ListNode curr = head;
        while (curr != null) {
            System.out.print(curr.val);
            if (curr.next != null)
                System.out.print(" -> ");
            curr = curr.next;
        }
        System.out.println();
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
            next = null;
        }
    }
}