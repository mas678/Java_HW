package queue;

import java.util.function.Predicate;

abstract class AbstractQueue implements Queue {
    protected int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void removeIf(Predicate<Object> predicate) {
        int past = size;
        for (int i = 0; i < past; i++) {
            if (!predicate.test(element())) {
                enqueue(element());
            }
            dequeue();
        }
    }

    @Override
    public void retainIf(Predicate<Object> predicate) {
        removeIf(predicate.negate());
    }

    @Override
    public void dropWhile(Predicate<Object> predicate) {
        int past = size;
        while (past-- > 0 && predicate.test(element())) {
            dequeue();
        }
    }

    @Override
    public void takeWhile(Predicate<Object> predicate) {
        int past = size;
        while (past-- > 0 && predicate.test(element())) {
            enqueue(element());
            dequeue();
        }

        while (past-- >= 0) {
            dequeue();
        }
    }
}
