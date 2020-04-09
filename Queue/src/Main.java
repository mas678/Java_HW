import queue.ArrayQueueModule;

public class Main {

    public static void main(String[] args) {
        ArrayQueueModule.push(0);
        for (int i = 0; i < 71; i++) {
            ArrayQueueModule.enqueue(3 * i);
            ArrayQueueModule.enqueue(3 * i + 1);
            ArrayQueueModule.dequeue();
            ArrayQueueModule.enqueue(3 * i + 2);
        }
        System.out.println(ArrayQueueModule.dequeue());
    }
}
