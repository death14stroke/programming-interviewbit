import java.util.List;

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
        // keep traversing and update length
        while (head != null) {
            len++;
            head = head.next;
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
        // pointers to two sublist one for each, zeroes and ones
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
    static ListNode partition(ListNode head, int B) {
        // if 0 or 1 nodes in the list
        if (head == null || head.next == null)
            return head;

        // dummy head nodes for two lists: one with less than B and other with the rest
        ListNode smallerDummy = new ListNode(-1), greaterDummy = new ListNode(-1);
        // head nodes for both lists
        ListNode smallerHead = smallerDummy, greaterHead = greaterDummy, curr = head;

        while (curr != null) {
            // append at the end of smaller than B list
            if (curr.val < B) {
                smallerHead.next = curr;
                smallerHead = smallerHead.next;
            }
            // append at the end of greater than or equal to B
            else {
                greaterHead.next = curr;
                greaterHead = greaterHead.next;
            }

            curr = curr.next;
        }

        // join the two list
        smallerHead.next = greaterDummy.next;
        // make sure the greater than or equal to B list has an end
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
            ListNode next = curr.next;
            // current element is already sorted - skip
            if (curr.val >= prev.val) {
                prev = curr;
            } else {
                ListNode temp = dummy;
                // search insert position for current node in the previous half of the list which is sorted
                while (temp.next != curr && temp.next.val <= curr.val)
                    temp = temp.next;

                // insert current node in the middle
                curr.next = temp.next;
                temp.next = curr;
                prev.next = next;
            }

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
        // find the mid-pointer (slow)
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
        ListNode dummy = new ListNode(-1), curr = dummy;

        // while both lists have nodes
        while (head1 != null && head2 != null) {
            // add first or second list node to end
            if (head1.val <= head2.val) {
                curr.next = head1;
                head1 = head1.next;
            } else {
                curr.next = head2;
                head2 = head2.next;
            }

            curr = curr.next;
        }

        // if first list has reached end
        if (head1 == null)
            curr.next = head2;
            // if second list has reached end
        else
            curr.next = head1;

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

    // https://www.interviewbit.com/problems/remove-nth-node-from-list-end/
    static ListNode removeNthFromEnd(ListNode head, int B) {
        // empty list
        if (head == null)
            return null;

        ListNode first = head, second = head;
        // move second pointer to nth node from start or till end
        for (int i = 0; i < B && second != null; i++)
            second = second.next;

        // if the list size is >= B remove first node
        if (second == null)
            return head.next;

        // iterate second till end so first reaches at (len - B)th node from beginning i.e. nth node from end
        while (second.next != null) {
            second = second.next;
            first = first.next;
        }

        // remove nth node from end
        first.next = first.next.next;

        return head;
    }

    // https://www.interviewbit.com/problems/k-reverse-linked-list/
    static ListNode reverseList(ListNode head, int B) {
        // dummy node for previous node to start as non-null
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode curr = head, prev = dummy;

        while (curr != null) {
            ListNode temp = curr, oldPrev = prev;
            // reverse B nodes from curr
            for (int i = 0; i < B; i++) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }

            // prev of the old head will now point to the new head
            oldPrev.next = prev;
            // old head will now point to the (B + 1)th node
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
        ListNode oddHead = head, odd = oddHead;
        head = head.next;

        // create even position nodes sublist. Insert new nodes at beginning
        ListNode even = head;
        head = head.next;
        // mark even list end as null
        even.next = null;

        // start from 3rd node
        boolean oddPos = true;
        // keep adding each node to odd and even positions list alternately
        while (head != null) {
            ListNode next = head.next;

            if (oddPos) {
                odd.next = head;
                odd = odd.next;
            } else {
                head.next = even;
                even = head;
            }

            oddPos = !oddPos;
            head = next;
        }

        // mark odd list end as null
        odd.next = null;
        // head of the resultant list
        head = oddHead;

        ListNode curr = oddHead;
        // start from 2nd entry in odd list
        odd = oddHead.next;
        // head already appended hence next node will be even
        oddPos = false;

        // merge both lists alternately
        while (odd != null || even != null) {
            if (oddPos) {
                curr.next = odd;
                odd = odd.next;
            } else {
                curr.next = even;
                //noinspection ConstantConditions
                even = even.next;
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
    static ListNode rotateRight(ListNode head, int B) {
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

        B = B % len;
        // no need to rotate
        if (B == 0)
            return head;
        // rotate len-B from left
        B = len - B;

        ListNode curr = head;
        // find new tail after rotation
        for (int i = 0; i < B - 1; i++)
            curr = curr.next;

        // link old tail to head
        tail.next = head;
        // head of the rotated list
        head = curr.next;
        // mark new tail
        curr.next = null;

        return head;
    }

    // https://www.interviewbit.com/problems/kth-node-from-middle/
    static int kthFromMiddle(ListNode head, int B) {
        // another approach is to find middle node and then use algo for kth node from end
        // calculate length of the linked list
        int n = length(head);
        // kth node from middle (n/2 + 1) towards beginning = (n/2) + 1 - B from the beginning
        B = (n / 2) + 1 - B;
        // if B is not valid
        if (B < 1)
            return -1;

        // move B - 1 positions from the beginning
        ListNode curr = head;
        for (int i = 1; i < B; i++)
            curr = curr.next;

        return curr.val;
    }

    // https://www.interviewbit.com/problems/reverse-alternate-k-nodes/
    static ListNode reverseAlternateK(ListNode head, int B) {
        // create dummy node to avoid null previous pointer
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode prev = dummy, curr = head;
        // flag to check if current B nodes should be reversed or not
        boolean reverse = true;

        while (curr != null) {
            // skip current B nodes
            if (!reverse) {
                for (int i = 0; i < B; i++) {
                    prev = curr;
                    curr = curr.next;
                }
            }
            // else reverse current B nodes
            else {
                ListNode oldPrev = prev, temp = curr;
                // reverse B nodes
                for (int i = 0; i < B; i++) {
                    ListNode next = curr.next;
                    curr.next = prev;
                    prev = curr;
                    curr = next;
                }

                // join the nodes before and after the reversed part
                oldPrev.next = prev;
                temp.next = curr;
                // update previous pointer
                prev = temp;
            }

            // flip the flag for next B nodes
            reverse = !reverse;
        }

        return dummy.next;
    }

    // https://www.interviewbit.com/problems/reverse-link-list-ii/
    static ListNode reverseBetween(ListNode head, int B, int C) {
        // no need to reverse
        if (B == C)
            return head;

        // create dummy node to avoid null previous pointer
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        // skip B positions
        ListNode prev = dummy;
        for (int i = 1; i < B; i++)
            prev = prev.next;

        ListNode curr = prev.next;
        ListNode oldPrev = prev, temp = curr;
        // reversing the sublist from B to C
        for (int i = B; i < C; i++) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        // link node before head of original sublist to point to the tail of the original sublist
        oldPrev.next = prev;
        // link head of original sublist to the (B+1)th node
        temp.next = curr;

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
        ListNode curr = dummy;

        int carry = 0;
        // till both numbers have the same number of digits
        while (head1 != null && head2 != null) {
            int sum = head1.val + head2.val + carry;
            carry = sum / 10;
            sum = sum % 10;

            curr.next = new ListNode(sum);
            curr = curr.next;
            head1 = head1.next;
            head2 = head2.next;
        }

        // if number 1 has more digits
        while (head1 != null) {
            int sum = head1.val + carry;
            carry = sum / 10;
            sum = sum % 10;

            curr.next = new ListNode(sum);
            curr = curr.next;
            head1 = head1.next;
        }

        // if number 2 has more digits
        while (head2 != null) {
            int sum = head2.val + carry;
            carry = sum / 10;
            sum = sum % 10;

            curr.next = new ListNode(sum);
            curr = curr.next;
            head2 = head2.next;
        }

        // if carry is not zero, create new node for carry
        if (carry != 0)
            curr.next = new ListNode(carry);

        return dummy.next;
    }

    // https://www.interviewbit.com/problems/list-cycle/
    static ListNode detectCycle(ListNode head) {
        // 0 or 1 node in the list
        if (head == null || head.next == null)
            return null;

        ListNode slow = head, fast = head;
        // move both pointers till they meet or fast pointer reaches null
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            // found the meeting point
            if (slow == fast)
                break;
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
        }
    }
}