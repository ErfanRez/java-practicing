package minheap;

public class MinHeap {
    public static final int INITIAL_CAPACITY = 25;
    private int[] a;
    private int n; // number of elements in min-heap

    public MinHeap() {
        this(INITIAL_CAPACITY);
    }

    public MinHeap(int initCapacity) {
        if (initCapacity < 1)
            throw new IllegalArgumentException("initCapacity must be >= 1");
        a = new int[initCapacity + 1]; // a[0] is not used
        n = 0;
    }

    public int size() {
        return n;
    }

    private void ensureCapacity() {
        if (n == a.length - 1) {
            int[] b = new int[a.length + INITIAL_CAPACITY];
            for (int i = 0; i < a.length; i++)
                b[i] = a[i];
            a = b;
        }
    }

    public void add(int item) {
        ensureCapacity();
        n++;
        int i = n;
        while (i != 1 && a[i / 2] > item) {
            a[i] = a[i / 2];
            i /= 2;
        }
        a[i] = item;
    }

    public int removeMin() throws EmptyMinHeapException {
        if (n == 0)
            throw new EmptyMinHeapException("min-heap is empty");
        int minElement = a[1];
        int lastElement = a[n--];
        int i = 1;
        int j = 2;
        boolean flag = false;
        while (!flag && j <= n) {
            // pick the smaller child
            if (j < n && a[j] > a[j + 1])
                j++;
            // can we put last element in current node?
            if (lastElement > a[j]) {
                a[i] = a[j]; // move child up
                i = j;
                j *= 2;
            } else {
                flag = true;
            }
        }
        a[i] = lastElement;
        return minElement;
    }

    public static MinHeap readMinHeap() {
        MinHeap heap = new MinHeap();
        java.util.Scanner input = new java.util.Scanner(System.in);
        System.out.print("Enter an integer number, negative or zero to quit: ");
        int item = input.nextInt();
        while (item > 0) {
            heap.add(item);
            System.out.print("Enter an integer number, negative or zero to quit: ");
            item = input.nextInt();
        }
        return heap;
    }

    public static MinHeap createMinHeap(int...args) {
        MinHeap heap = new MinHeap();
        for (int v: args)
            heap.add(v);
        return heap;
    }


    public int[] getHeapArray() {
        return a;
    }
}