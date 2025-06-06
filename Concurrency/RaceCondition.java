public class RaceCondition {
    public static volatile int x = 0;
    public static int LOOP_COUNTER = 300_000_000;
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < LOOP_COUNTER; i++) {
            x = 0;
//            twoProcessesWithLoops();
            threeProcesses();
        }
    }

    private static void twoProcessesWithLoops() throws InterruptedException {
        Thread one = new Thread(() -> {
            for (int j = 0; j < 3; j++) {
                x++;
            }
        });

        Thread two = new Thread(() -> {
            for (int j = 0; j < 3; j++) {
                x++;
            }
        });

        one.start();
        two.start();

        one.join();
        two.join();

        if(x != 6){
            System.out.println(x); // Expected to be 6
        }
    }

    private static void threeProcesses() throws InterruptedException {
        Thread one = new Thread(() -> {
            x++;
        });

        Thread two = new Thread(() -> {
            x++;
        });

        Thread three = new Thread(() -> {
            x++;
        });

        one.start();
        two.start();
        three.start();

        one.join();
        two.join();
        three.join();

        if(x != 3){
            System.out.println(x); // Expected to be 3
        }
    }
}
