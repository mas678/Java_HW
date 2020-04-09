package queue;

public class ArrayQueue extends AbstractQueue{

    private int head;
    private Object[] elements = new Object[16];


    public void enqueue(Object element) {
        assert element != null;

        ensureCapacity(size + 1);
        elements[(head + size++) % elements.length] = element;
    }


    private void ensureCapacity(int capacity) {
        if (capacity > elements.length) {
            Object[] newElements = new Object[2 * capacity];
            System.arraycopy(elements, head, newElements, 0, size - head);
            System.arraycopy(elements, 0, newElements, size - head, head);
            elements = newElements;
            head = 0;
        }
    }

    public Object dequeue() {
        assert size > 0;

        size--;
        Object ans = elements[head];
        elements[head++] = null;
        head = head % elements.length;
        return ans;
    }

    public Object element() {
        assert size > 0;

        return elements[head];
    }

    public void clear() {
        elements = new Object[16];
        size = 0;
        head = 0;
    }
}
