package maxheap;

public class EmptyMaxHeapException extends Exception {
    public EmptyMaxHeapException() {
        super();
    }
    public EmptyMaxHeapException(String message) {
        super(message);
    }
}
