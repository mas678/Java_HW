package queue;

import java.util.function.Predicate;

public class LinkedQueue extends AbstractQueue {
    private Node head;
    private Node tail;

    @Override
    public void enqueue(Object element) {
        assert element != null;

        size++;
        tail = new Node(element, tail);
        if (head == null) {
            head = tail;
        }
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
    public void removeIf(Predicate<Object> predicate) {
        Node temp = head;
        Node prev = null;
        size = 0;
        while (temp != null) {
            if (!predicate.test(temp.value)) {
                size++;
                if (prev != null) {
                    prev.next = temp;
                } else {
                    head = temp;
                }
                prev = temp;
            }
            temp = temp.next;
        }
        tail = prev;
    }

    @Override
    public void retainIf(Predicate<Object> predicate) {
        removeIf(predicate.negate());
    }

    @Override
    public void takeWhile(Predicate<Object> predicate) {
        Node temp = head;
        size = 0;
        while (temp != null && predicate.test(temp.value)) {
            size++;
            tail = temp;
            temp = temp.next;
        }
    }

    @Override
    public void dropWhile(Predicate<Object> predicate) {
        Node temp = head;
        while (temp != null && predicate.test(temp.value)) {
            size--;
            temp = temp.next;
        }
        head = temp;
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
