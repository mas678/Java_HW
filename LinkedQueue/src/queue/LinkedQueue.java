package queue;

import java.util.function.Predicate;

public class LinkedQueue extends AbstractQueue {
    private Node head;
    private Node tail;

    @Override
    public void enqueue(Object element) {
        assert element != null;

        tail = new Node(element, tail);
        if (isEmpty()) {
            head = tail;
        }
        size++;
    }

    @Override
    public Object dequeue() {
        assert size > 0;

        Object value = head.value;
        size--;
        head = head.next;
        return value;
    }

    @Override
    public Object element() {
        assert size > 0;

        return head.value;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
        tail = null;
    }


    @Override
    public void takeWhile(Predicate<Object> predicate) {
        Node temp = head;
        head = null;
        tail = null;
        size = 0;
        while (temp != null && predicate.test(temp.value)) {
            if (head == null) {
                head = temp;
            }
            size++;
            tail = temp;
            temp = temp.next;
        }
        tailEnd();
    }

    private void tailEnd() {
        if (size != 0) {
            tail.next = null;
        }
    }

    private class Node {
        private Object value;
        private Node next;

        public Node(Object value, Node prev) {
            assert value != null;

            this.value = value;
            if (prev != null) {
                prev.next = this;
            }
        }
    }
}
