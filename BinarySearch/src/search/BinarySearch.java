package search;

public class BinarySearch {
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int n = args.length - 1;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(args[i + 1]);
        }
        System.out.print(recursiveBinarySearch(x, arr, -1, n));
    }

    // pre: \forall arr.length > i >= 0 : arr[i] >= arr[i + 1]
    // post: R = i : arr[i] <= x && (i == 0 || arr[i - 1] > x)

    public static int nonRecursiveBinarySearch(int x, int[] arr) {
        int left = -1, right = arr.length;
        // (left == -1 || arr[left]) > x && (right == arr.length || arr[right] =< x) && pre
        // Inv: (left == -1 || arr[left]) > x && (right == arr.length || arr[right] =< x) && pre
        while (right - left > 1) {
            // I && right - left > 1
            int m = (left + right) / 2;
            // I && right > m > left
            if (arr[m] > x) {
                // I && arr[m] > x
                // => \forall m1 : 0 < m1 < m m1 != R
                left = m;
                // I
            } else {
                // I && arr[m] <= x
                // => \forall m1 :  < m1 < arr.length m1 != R
                right = m;
                // I
            }
            // I
        }
        // I && left >= right - 1
        // => arr[right - 1] >= arr[left] > x
        // => (arr[right] <= x && (i + 1 == arr.length || arr[left + 1] < x))
        // || (right == arr.length && arr[arr.length - 1] > x])
        return right;
    }

    // pre: \forall arr.length > i >= 0 : arr[i] >= arr[i + 1]
    // && (left == -1 || arr[left]) > x && (right == arr.length || arr[right] =< x)
    // post: R = i : arr[i] <= x && (i == 0 || arr[i - 1] > x)

    public static int recursiveBinarySearch(int x, int[] arr, int left, int right) {
        // Inv: pre
        if (right - left <= 1) {
            // I && left >= right - 1
            // => arr[right - 1] >= arr[left] > x
            // => (arr[right] <= x && (i + 1 == arr.length || arr[left + 1] < x))
            // || (right == arr.length && arr[arr.length - 1] > x])
            return right;
        }
        // I && right - left > 1
        int m = (left + right) / 2;
        // I && right > m > left
        if (arr[m] > x) {
            // I && arr[m] > x
            // => \forall m1 : 0 < m1 < m m1 != R
            left = m;
            // I
        } else {
            // I && arr[m] <= x
            // => \forall m1 :  < m1 < arr.length m1 != R
            right = m;
            // I
        }
        // I
        return recursiveBinarySearch(x, arr, left, right);
    }
}
