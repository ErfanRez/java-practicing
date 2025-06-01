public class RawThreadExample {
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[1000];
        long start = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            int taskNumber = i;
            threads[i] = new Thread(() -> {
                System.out.println("Task " + taskNumber);
            });
            threads[i].start();
        }

        for (int i = 0; i < 1000; i++) {
            threads[i].join();
        }

        long end = System.currentTimeMillis();
        System.out.println("Time (raw threads): " + (end - start) + " ms");
    }
}
