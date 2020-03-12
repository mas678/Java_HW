package queue;

public class ArrayQueueADT {
    private int size;
    private int head;
    private Object[] elements = new Object[16];

    // pre: element ≠ null
    // post: n = n' + 1 ∧ ∀i=1..n' : a[i]' = a[i] ∧ a[n] = element
    public static void enqueue(ArrayQueueADT queue, Object element) {
        assert element != null;

        queue.ensureCapacity(queue, queue.size + 1);
        queue.elements[(queue.head + queue.size++) % queue.elements.length] = element;
    }


    // pre: element ≠ null
    // post: n = n' + 1 ∧ ∀i=1..n' : a[i]' = a[i] ∧ a[1] = element
    public static void push(ArrayQueueADT queue, Object element) {
        assert element != null;

        queue.ensureCapacity(queue,queue.size + 1);
        queue.size++;
        queue.head = (queue.head - 1 + queue.elements.length) % queue.elements.length;
        queue.elements[queue.head] = element;

    }

    // post: immutable
    private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
        if (capacity > queue.elements.length) {
            Object[] newElements = new Object[2 * capacity];
            System.arraycopy(queue.elements, queue.head, newElements, 0, queue.size - queue.head);
            System.arraycopy(queue.elements, 0, newElements, queue.size - queue.head, queue.head);
            queue.elements = newElements;
            queue.head = 0;
        }
    }

    // pre: n > 0
    // post: ℝ = a[1] ∧ ∀i=2..n : a[i]' = a[i + 1]
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue.size > 0;

        queue.size--;
        Object ans = queue.elements[queue.head];
        queue.elements[queue.head++] = null;
        queue.head = queue.head % queue.elements.length;
        return ans;
    }

    // pre: n > 0
    // post: ℝ = a[n] ∧ n = n' − 1 ∧ ∀i=1..n : a[i]' = a[i]
    public static Object remove(ArrayQueueADT queue) {
        assert queue.size > 0;

        queue.size--;
        Object ans = queue.elements[(queue.size + queue.head) % queue.elements.length];
        queue.elements[(queue.size + queue.head) % queue.elements.length] = null;
        return ans;
    }

    // pre: n > 0
    // peek: ℝ = a[n] ∧ n = n' ∧ ∀i=1..n : a[i]' = a[i]
    public static Object peek(ArrayQueueADT queue) {
        assert queue.size > 0;

        return queue.elements[(queue.head + queue.size - 1) % queue.elements.length];
    }

    // pre: n > 0
    // peek: ℝ = a[1] ∧ n = n' ∧ ∀i=1..n : a[i]' = a[i]
    public static Object element(ArrayQueueADT queue) {
        assert queue.size > 0;

        return queue.elements[queue.head];
    }

    // pre: n > index
    // peek: ℝ = a[index] ∧ n = n' ∧ ∀i=1..n : a[i]' = a[i]
    public static Object get(ArrayQueueADT queue, int index) {
        assert index < queue.size;

        return queue.elements[(queue.head + index) % queue.elements.length];
    }

    // pre: n > index
    // peek: n = n' ∧ ∀i=1.j-1 j+1.n : a[i]' = a[i] ∧ a[j]' = value
    public static void set(ArrayQueueADT queue, int index, Object value) {
        assert index < queue.size;

        queue.elements[(queue.head + index) % queue.elements.length] = value;
    }

    // pre: ℝ = n ∧ n = n' ∧ ∀i=1..n : a[i]' = a[i]
    // post: R = size
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

    // post: ℝ = n > 0 ∧ n = n' ∧ ∀i=1..n : a[i]' = a[i]
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }

    // post: n = 0
    public static void clear(ArrayQueueADT queue) {
        queue.elements = new Object[16];
        queue.size = 0;
        queue.head = 0;
    }
}
