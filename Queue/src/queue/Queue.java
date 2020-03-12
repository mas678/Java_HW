package queue;

import java.util.function.Predicate;

public interface Queue {
    // pre: element ≠ null
    // post: n = n' + 1 ∧ ∀i=1..n' : a[i]' = a[i] ∧ a[n] = element
    public void enqueue(Object element);

    // pre: n > 0
    // post: ℝ = a[1] ∧ ∀i=2..n : a[i]' = a[i + 1]
    public Object dequeue();

    // pre: n > 0
    // post: ℝ = a[1] ∧ n = n' ∧ ∀i=1..n : a[i]' = a[i]
    public Object element();

    // pre: Immutable
    // post: ℝ = n ∧ n = n' ∧ ∀i=1..n : a[i]' = a[i]
    public int size();

    // pre: Immutable
    // post: ℝ = n > 0 ∧ n = n' ∧ ∀i=1..n : a[i]' = a[i]
    public boolean isEmpty();

    // pre: Immutable
    // post: n = 0
    public void clear();

    //pre:
    //post: n'>= n >= 0 ∧ ∀i=1..n !predicate(a[i]) ∧ ∀j=1..n' : a'[j] = a[i] j_i < j_(i + 1)
    // ∧ ∀j=1..n' : a'[j] ≠ a[i] ∀i=1..n predicate(a[j])
    public void removeIf(Predicate<Object> predicate);

    //pre:
    //post: n'>= n >= 0 ∧ ∀i=1..n !predicate(a[i]) ∧ ∀j=1..n' : a'[j] = a[i] j_i < j_(i + 1)
    // ∧ ∀j=1..n' : a'[j] ≠ a[i] ∀i=1..n predicate(a[j])
    public void retainIf(Predicate<Object> predicate);

    //
    // post: n'>= n >= 0 ∧ ∀i=1..n predicate(a[i]) ∧ a'[i] = a[i]
    // ∧ (n == n' || !predicate(a'[n]))
    public void takeWhile(Predicate<Object> predicate);

    // post: n'>= n >= 0 ∧ ∀i=1..n' - n predicate(a'[i])
    // ∧ ∀i=1..n a'[i + n' - n] = a[i] ∧ (n == 0 || !predicate(a[0]))
    public void dropWhile(Predicate<Object> predicate);
}
