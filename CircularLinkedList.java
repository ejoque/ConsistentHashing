/**
 * A bare bones circular linked list to be used in consistent hashing, so it is based on hash index of each node. 
 * @author Elizabeth Joque
 * @version 12/8/2025
 */
public class CircularLinkedList<E> {
    private Node<E> head;
    private Node<E> tail;

    public CircularLinkedList() {
        this.head = null;
        this.tail = null;
    }

    public void insertNode(int hashIndex, E item) {
        Node<E> new_node = new Node<E>(item, hashIndex);
        if (head == null) {
            // list is empty! 
            head = new_node;
            tail = new_node;
            head.next = head;
            head.previous = head;
        }
        else if (hashIndex < head.hashIndex) {
            // insert at the front, will become the new head
            Node<E> old_head = head;
            new_node.next = head;
            new_node.previous = tail;
            old_head.previous = new_node;
            tail.next = new_node;
            head = new_node;
        }
        else if (hashIndex >= tail.hashIndex) {
            // insert at the end!! we get a new tail.
            Node<E> old_tail = tail;
            old_tail.next = new_node;
            new_node.previous = tail;
            new_node.next = head;
            tail = new_node;
            head.previous = tail; 
        }
        else {
            // put it where it goes in order, reassigning pointers as you go 
            Node<E> current = head; 
            while (current.hashIndex < hashIndex) {
                current = current.next;
            }
            // now, current.hashIndex > hashIndex, so need to put node before current
            Node<E> previous = current.previous;
            new_node.next = current;
            new_node.previous = previous;
            previous.next = new_node;
            current.previous = new_node;
        }
    }

    /**
     * Returns a bucket for a given hash index. If a bucket with that index exists, it returns that bucket. Otherwise, it returns the next bucket. 
     * If there are no buckets after the given index, it returns the head. 
     * @param hashIndex An integer value.
     * @return The bucket you hashed into. 
     */
    public E getNode(int hashIndex) {
        Node<E> current = head; // start from the head;
        if (current == null) {
            // the list is empty
            return null;
        }
        while (current.hashIndex < hashIndex) {
            current = current.next;
            if (current == head) {
                return head.data;
            }
        }
        return current.data; // a bucket exists where it should go
    }

    public int getPreviousIndex(int hashIndex) {
        Node<E> current = head; // start from head
        while (true) {
            if (current.hashIndex >= hashIndex) {
                return current.previous.hashIndex;
            }

            current = current.next;

            if (current == head) {
                return tail.hashIndex;
            }
        }
    }

    @Override
    public String toString() {
        String circString = "";
        if (head != null) {
            Node<E> start = head;
            circString += start.toString();
            while (start.next != head) {
                start = start.next;
                circString += "\n" + start.toString();
            }
            return circString;
        }
        return "Hash ring is empty.";
    }

    /**
     * A list node can store a data element, a hash index, a reference to the next list node, and a reference to the previous list node.
     * Adapted from David's CS 261 doubly linked list homework assignment.
     *
     * @author David & Elizabeth
     * @version 6/29/17, 12/9/2025
     */
    private static class Node<E> {
        private E data;
        private int hashIndex;
        private Node<E> next;
        private Node<E> previous;

        /**
         * Creates a new node with a null next field
         *
         * @param new_data a value for the data element
         */
        public Node(E new_data, int hashIndex) {
            this.data = new_data;
            this.hashIndex = hashIndex;
        }

        /**
         * Creates a new node with a given next field
         *
         * @param init_data a value for the data element
         * @param next_node a reference to the next node in the list
         */
        public Node(E new_data, Node<E> next_node) {
            this.data = new_data;
            this.next = next_node;
        }

        /**
         * @return the string representation of this node
         */
        @Override
        public String toString() {
            return this.data.toString();
        }
    }
}