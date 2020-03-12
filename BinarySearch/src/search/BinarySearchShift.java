package search;

public class BinarySearchShift {
    //pre: \exist arr.length > j >= 0 : \forall arr.length > i > 0 && i != j : arr[i] <= arr[i - 1]
    //post: R = j
    public static void main(String[] args) {
        int n = args.length;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(args[i]);
        }
        System.out.print(n == 0 ? 0 : recursiveBinarySearchShift(arr, 0, n) % n);
    }

    // pre: \exist arr.length >= j > 0 : \forall arr.length > i > 0 && i != j : arr[i] <= arr[i - 1]
    // post: R = j

    public static int nonRecursiveBinarySearchShift(int[] arr) {
        int left = 0, right = arr.length;
        // pre && (left < j) && (right >= j)
        // Inv: pre && ((left < j) && (right >= j))
        while (right - left > 1) {
            // I && right - left > 1
            int m = (left + right) / 2;
            // I && right > m > left
            if (arr[m] <= arr[0]) {
                // I && m >= j
                // => \forall m1 : 0 < m1 < m m1 != j
                right = m;
                // I
            } else {
                // I && m < j
                // => \forall m1 :  < m1 < arr.length m1 != j
                left = m;
                // I
            }
            // I
        }
        // I && left >= right - 1
        // => right >= j && right - 1 <= left < j
        // => right == j
        return right;
    }

    // pre: \exist arr.length >= j > 0 : \forall arr.length > i > 0 && i != j : arr[i] <= arr[i - 1]
    // && (left < j) && (right >= j)
    // post: R = j

    public static int recursiveBinarySearchShift(int[] arr, int left, int right) {
        // pre
        if (right - left <= 1) {
            // pre && left >= right - 1
            // => right >= j && right - 1 <= left < j
            // => right == j
            return right;
        }
        // pre && right - left > 1
        int m = (left + right) / 2;
        // pre && right > m > left
        if (arr[m] <= arr[0]) {
            // pre && m >= j
            // => \forall m1 : 0 < m1 < m m1 != j
            right = m;
            // pre
        } else {
            // pre && m < j
            // => \forall m1 :  < m1 < arr.length m1 != j
            left = m;
            // pre
        }
        // pre
        return recursiveBinarySearchShift(arr, left, right);
    }
}
