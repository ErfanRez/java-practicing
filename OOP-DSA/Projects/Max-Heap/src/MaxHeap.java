public class MaxHeap { // IntMaxHeap
    public static final int INITIAL_CAPACITY = 25;
    private int[] a;
    private int n; // number of elements in max-heap

    public MaxHeap() {
        this(INITIAL_CAPACITY);
    }
    public MaxHeap(int initCapacity) {
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
        int i = n; // current node
        while (i != 1 && a[i / 2] < item) {
            a[i] = a[i / 2]; // move parent down
            i /= 2;
        }
        a[i] = item;
    }
    public static MaxHeap readMaxHeap() {
        MaxHeap heap = new MaxHeap();
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
    public static MaxHeap createMaxHeap(int... args) {
        MaxHeap heap = new MaxHeap();
        for (int i = 0; i < args.length; i++)
            heap.add(args[i]);
        return heap;
    }
    public int removeMax() throws EmptyMaxHeapException {
        if (n == 0)
            throw new EmptyMaxHeapException("max-heap is empty");
        int maxElement = a[1];
        int lastElement = a[n--]; // remove last element from max-heap
        int i = 1; // current node
        int j = 2; // a child of current node
        boolean flag = false;
        while (!flag && j <= n) {
            // set child to larger child of current node
            if (j < n && a[j] < a[j + 1])
                j++;
            // can we put last element in current node?
            if (lastElement < a[j]) {
                a[i] = a[j]; // move child up
                i = j;
                j *= 2;
            } else
                flag = true;
        }
        a[i] = lastElement;
        return maxElement;
    }

    public int[] getHeapArray(){
        return a;
    }
}
