import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExample {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            int taskNumber = i;
            pool.execute(() -> {
                System.out.println("Task " + taskNumber);
            });
        }

        pool.shutdown(); // finish tasks, no new tasks accepted
        while (!pool.isTerminated());
            // wait for all tasks to finish

        long end = System.currentTimeMillis();
        System.out.println("Time (thread pool): " + (end - start) + " ms");
    }
}
