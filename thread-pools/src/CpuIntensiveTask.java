import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CpuIntensiveTask {
    public static void main(String[] args) {
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService service = Executors.newFixedThreadPool(cores);
        System.out.println("Created thread pool with " + cores + " cores");

        for (int i = 0; i < 50; i++) {
            service.execute(new CpuTask(i));
        }

        service.shutdown();
        try {
            if (!service.awaitTermination(60, TimeUnit.SECONDS)) {
                service.shutdownNow();
            }
        } catch (InterruptedException e) {
            service.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

class CpuTask implements Runnable {
    private final int taskId;

    public CpuTask(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        System.out.println("Task " + taskId + " started by " + Thread.currentThread().getName());
        // Simulate CPU-intensive work
        long result = 0;
        for (int i = 0; i < 1_000_000; i++) {
            result += (long) Math.sqrt(i);
        }
        System.out.println("Task " + taskId + " completed by " + Thread.currentThread().getName());
    }
}
