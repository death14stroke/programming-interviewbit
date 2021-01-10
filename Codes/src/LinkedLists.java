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

        // prev will point to new head
        return prev;
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

    // https://www.interviewbit.com/problems/partition-list/
    static ListNode partition(ListNode head, int x) {
        // if 0 or 1 nodes in the list
        if (head == null || head.next == null)
            return head;

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

        // join the two list
        smallerHead.next = greaterDummy.next;
        // make sure the greater than or equal to x list has an end
        greaterHead.next = null;

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

        ListNode slow = head, fast = head.next;
        // find the mid pointer (slow)
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // break the list from the middle
        ListNode head2 = slow.next;
        slow.next = null;

        // merge the two halves after recursively sorting them using mergeSort
        return mergeSortedLinkedLists(mergeSort(head), mergeSort(head2));
    }

    // https://www.interviewbit.com/problems/merge-two-sorted-lists/
    static ListNode mergeSortedLinkedLists(ListNode head1, ListNode head2) {
        // create new empty list with dummy node as head
        ListNode dummy = new ListNode(-1);
        ListNode tail = dummy;

        // while both lists have nodes
        while (head1 != null && head2 != null) {
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

        // if first list has reached end
        if (head1 == null)
            tail.next = head2;
            // if second list has reached end
        else
            tail.next = head1;

        return dummy.next;
    }

    // https://www.interviewbit.com/problems/palindrome-list/
    static int isPalindrome(ListNode head) {
        // 0 or 1 nodes in linked list always palindrome
        if (head == null || head.next == null)
            return 1;

        ListNode slow = head, fast = head.next;
        // tortoise and hare traversal
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // break linked list from the middle
        ListNode head2 = slow.next;
        slow.next = null;
        // reverse the second half
        head2 = reverse(head2);

        // loop through first half and reversed second half checking similarity of two lists
        while (head != null && head2 != null) {
            if (head.val != head2.val)
                return 0;

            head = head.next;
            head2 = head2.next;
        }

        // if any of the halves reaches end, it is palindrome
        return 1;
    }

    // https://www.interviewbit.com/problems/remove-duplicates-from-sorted-list/
    static ListNode removeDuplicates(ListNode head) {
        ListNode curr = head;
        while (curr != null) {
            // skip references to duplicate nodes till a unique node is found
            while (curr.next != null && curr.val == curr.next.val)
                curr.next = curr.next.next;

            // update current pointer
            curr = curr.next;
        }

        return head;
    }

    // https://www.interviewbit.com/problems/remove-duplicates-from-sorted-list-ii/
    static ListNode removeAllDuplicates(ListNode head) {
        // add a dummy unique node to avoid null
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode curr = head, prev = dummy;
        while (curr != null) {
            // loop till a node doesn't match with previous data
            while (curr.next != null && curr.val == curr.next.val)
                curr = curr.next;

            // if no duplicate nodes in between move previous pointer
            if (prev.next == curr)
                prev = curr;
                // else link previous pointer to the next unique
            else
                prev.next = curr.next;

            curr = curr.next;
        }

        return dummy.next;
    }

    // https://www.interviewbit.com/problems/remove-nth-node-from-list-end/
    static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode first = head, second = head;
        // move second pointer to nth node from start
        for (int i = 0; i < n; i++) {
            // if the list size is less than or equal than n remove first node
            if (second.next == null)
                return head.next;

            second = second.next;
        }

        // iterate second till end so first reaches at (len - n)th node from beginning i.e. nth node from end
        while (second.next != null) {
            second = second.next;
            first = first.next;
        }

        // remove nth node from end
        if (first.next != null)
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

    // https://www.interviewbit.com/problems/even-reverse/
    static ListNode evenReverse(ListNode head) {
        // if 0 or 1 node in the list
        if (head == null || head.next == null)
            return head;

        // create odd position nodes sublist. Insert new nodes at end
        ListNode oddHead = head, tempOdd = oddHead;
        head = head.next;

        // create even position nodes sublist. Insert new nodes at beginning
        ListNode evenHead = head;
        head = head.next;
        // mark even list end as null
        evenHead.next = null;

        // start from 3rd node
        boolean oddPos = true;
        // keep adding each node to odd and even positions list alternately
        while (head != null) {
            ListNode next = head.next;

            if (oddPos) {
                tempOdd.next = head;
                tempOdd = tempOdd.next;
            } else {
                head.next = evenHead;
                evenHead = head;
            }

            oddPos = !oddPos;
            head = next;
        }

        // mark odd list end as null
        tempOdd.next = null;

        // head of the resultant list
        head = oddHead;

        ListNode curr = oddHead;
        // start from 2nd entry in odd list
        oddHead = oddHead.next;
        // head already appended hence next node will be even
        oddPos = false;

        // merge both lists alternately
        while (oddHead != null || evenHead != null) {
            if (oddPos) {
                curr.next = oddHead;
                oddHead = oddHead.next;
            } else {
                curr.next = evenHead;
                evenHead = evenHead.next;
            }

            oddPos = !oddPos;
            curr = curr.next;
        }

        return head;
    }

    // https://www.interviewbit.com/problems/swap-list-nodes-in-pairs/
    static ListNode swapPairs(ListNode head) {
        // create dummy node for non-null prev
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode curr = head, prev = dummy;
        // looping two nodes at a time
        while (curr != null && curr.next != null) {
            ListNode next = curr.next, next2 = next.next;

            // 1st node of pair will point to 3rd node
            curr.next = next2;
            // previous will point to 2nd node of pair
            prev.next = next;
            // 2nd node will point to 1st
            next.next = curr;

            // update previous and current pointers
            prev = curr;
            curr = next2;
        }

        return dummy.next;
    }

    // https://www.interviewbit.com/problems/rotate-list/
    static ListNode rotateRight(ListNode head, int k) {
        // 0 or 1 nodes in linked-list
        if (head == null || head.next == null)
            return head;

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

        ListNode curr = head;
        // find new tail after rotation
        for (int i = 0; i < k - 1; i++)
            curr = curr.next;

        // link old tail to head
        tail.next = head;
        // head of the rotated list
        ListNode head2 = curr.next;
        // mark new tail
        curr.next = null;

        return head2;
    }

    // https://www.interviewbit.com/problems/kth-node-from-middle/
    static int kthFromMiddle(ListNode head, int k) {
        // calculate length of the linked list
        int n = length(head);

        // kth node from middle (n/2 + 1) towards beginning = (n/2) + 1 - k from the beginning
        k = (n / 2) + 1 - k;
        // if k is not valid
        if (k < 1)
            return -1;

        // move k positions from the beginning
        ListNode curr = head;
        for (int i = 1; i < k; i++)
            curr = curr.next;

        return curr.val;
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
        // if list has 0 or 1 elements
        if (head == null || head.next == null)
            return head;

        // find middle of the list
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // split list into two halves and reverse the second half
        ListNode head2 = slow.next;
        slow.next = null;
        head2 = reverse(head2);

        // alternate merge of two sublist
        // already added from list 1 so start adding from list 2
        ListNode curr = head, head1 = head.next;
        while (head1 != null && head2 != null) {
            // add node of sublist 2
            curr.next = head2;
            // move forward
            head2 = head2.next;
            curr = curr.next;

            // add node of sublist 1
            curr.next = head1;
            // move forward
            head1 = head1.next;
            curr = curr.next;
        }

        // if even list, list-2 last node will be pending
        curr.next = head2;

        return head;
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
    @SuppressWarnings("ConstantConditions")
    static ListNode detectCycle(ListNode head) {
        // 0 or 1 node in the list
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
            fast = fast.next;
        }

        return slow;
    }

    // util methods for testing linked-lists
    static ListNode createLinkedList(int[] A) {
        int n = A.length;
        if (n == 0)
            return null;

        ListNode head = new ListNode(A[0]);
        ListNode curr = head;

        for (int i = 1; i < n; i++) {
            curr.next = new ListNode(A[i]);
            curr = curr.next;
        }

        return head;
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