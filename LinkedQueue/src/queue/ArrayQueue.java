package queue;

import java.util.function.Predicate;

public class ArrayQueue extends AbstractQueue{
    // inv: n >= 0 ∧ ∀i=1..n' : a[i] ≠ null

    private int size;
    private int head;
    private Object[] elements = new Object[16];

    // pre: element ≠ null
    // post: n = n' + 1 ∧ ∀i=1..n' : a[i]' = a[i] ∧ a[n] = element
    public void enqueue(Object element) {
        assert element != null;

        ensureCapacity(size + 1);
        elements[(head + size++) % elements.length] = element;
    }


    // pre:/ element ≠ null
    // post: n = n' + 1 ∧ ∀i=1..n' : a[i]' = a[i] ∧ a[1] = element
    public void push(Object element) {
        assert element != null;

        ensureCapacity(size + 1);
        size++;
        head = (head - 1 + elements.length) % elements.length;
        elements[head] = element;

    }

    // post: elements.length = elements.length' && \forall i = 1..size elements[i] = a[i]
    // && \forall i = size...elements.length-1 elements[i] = null
    private void ensureCapacity(int capacity) {
        if (capacity > elements.length) {
            Object[] newElements = new Object[2 * capacity];
            System.arraycopy(elements, head, newElements, 0, size - head);
            System.arraycopy(elements, 0, newElements, size - head, head);
            elements = newElements;
            head = 0;
        }
    }

    // pre: n > 0
    // post: ℝ = a[1] ∧ ∀i=2..n : a[i]' = a[i + 1]
    public Object dequeue() {
        assert size >   0;

        size--;
        Object ans = elements[head];
        elements[head++] = null;
        head = head % elements.length;
        return ans;
    }

    // pre: n > 0
    // post: ℝ = a[n] ∧ n = n' − 1 ∧ ∀i=1..n : a[i]' = a[i]
    public Object remove() {
        assert size > 0;

        size--;
        Object ans = elements[(size + head) % elements.length];
        elements[(size + head) % elements.length] = null;
        return ans;
    }

    // pre: n > 0
    // peek: ℝ = a[n] ∧ n = n' ∧ ∀i=1..n : a[i]' = a[i]
    public Object peek() {
        assert size > 0;

        return elements[(head + size - 1) % elements.length];
    }

    // pre: n > 0
    // peek: ℝ = a[1] ∧ n = n' ∧ ∀i=1..n : a[i]' = a[i]
    public Object element() {
        assert size > 0;

        return elements[head];
    }

    // pre: n > index
    // peek: ℝ = a[index] ∧ n = n' ∧ ∀i=1..n : a[i]' = a[i]
    public Object get(int index) {
        assert index < size;

        return elements[(head + index) % elements.length];
    }

    // pre: n > index
    // peek: n = n' ∧ ∀i=1.j-1 j+1.n : a[i]' = a[i] ∧ a[j]' = value
    public void set(int index, Object value) {
        assert index < size;

        elements[(head + index) % elements.length] = value;
    }

    // pre: ℝ = n ∧ n = n' ∧ ∀i=1..n : a[i]' = a[i]
    // post: R = size
    public int size() {
        return size;
    }

    // pre: Immutable
    // post: ℝ = n > 0 ∧ n = n' ∧ ∀i=1..n : a[i]' = a[i]
    public boolean isEmpty() {
        return size == 0;
    }


    // post: n = 0
    public void clear() {
        elements = new Object[16];
        size = 0;
        head = 0;
    }

    @Override
    public void removeIf(Predicate<Object> predicate) {

    }

    @Override
    public void retainIf(Predicate<Object> predicate) {

    }

    @Override
    public void takeWhile(Predicate<Object> predicate) {

    }

    @Override
    public void dropWhile(Predicate<Object> predicate) {

    }
}
