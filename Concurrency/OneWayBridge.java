import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

enum Direction {
    None(0),
    Left(1),
    Right(2);

    private final int value;

    Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

class Car extends Thread {
    private static AtomicInteger carCount = new AtomicInteger(0);
    public final int Id;
    public Direction Direction = null;
    private final Bridge _bridge;
    private static final Random random = new Random();

    public Car(Bridge bridge) {
        this.Id = carCount.incrementAndGet();
        this.Direction = random.nextInt(2) == 0 ? Direction.Left : Direction.Right;
        this._bridge = bridge;
    }

    @Override
    public void run() {
        int delay = random.nextInt(1001) + 1000;
        System.out.println("Car " + Id + " from " + Direction + " STARTED crossing (" + delay + "ms)");
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Car " + Id + " from " + Direction + " FINISHED crossing");

        try {
            _bridge.BridgeLimit.release();
        } catch (Exception e) {
        }
    }
}

class Bridge {
    private final int k;
    private final Queue<Car> _queue = new LinkedList<>();
    public final Semaphore BridgeLimit;
    private volatile Direction _direction = Direction.None;
    private final Object _lock = new Object();
    public volatile boolean isRunning = true;

    public Bridge(int k) {
        this.k = k;
        this.BridgeLimit = new Semaphore(k);
    }

    public void EnqueueCar(Car car) {
        _queue.add(car);
        System.out.println("Car " + car.Id + " from " + car.Direction + " waiting...");
    }

    public void HandleCarQueue() {
        while (true) {
            Car car = _queue.peek();
            if (car != null) {
                synchronized (_lock) {
                    if (BridgeLimit.availablePermits() == k) {
                        _direction = car.Direction;
                        System.out.println("Bridge EMPTY. Direction: " + _direction);
                    }

                    if (car.Direction == _direction && BridgeLimit.availablePermits() > 0) {
                        try {
                            BridgeLimit.acquire();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            continue;
                        }
                        _queue.poll();

                        System.out.println("Car " + car.Id + " from " + car.Direction + " ENTERS. " +
                                "Permits: " + BridgeLimit.availablePermits() + "/" + k);

                        car.start();
                    }
                }
            }
        }
    }
}

public class OneWayBridge {
    static void main(String[] args) {
        Random random = new Random();
        int k = random.nextInt(5) + 1;
        int carCount = random.nextInt(46) + 5;

        System.out.println("=== ONE-WAY BRIDGE SIMULATION ===\n");
        System.out.println("Cars: " + carCount + ", Capacity: " + k + "\n");

        Bridge bridge = new Bridge(k);

        Thread bridgeThread = new Thread(bridge::HandleCarQueue);
        bridgeThread.start();

        for (int i = 0; i < carCount; i++) {
            Car car = new Car(bridge);
            bridge.EnqueueCar(car);

            try {
                Thread.sleep(random.nextInt(1001));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\nAll " + carCount + " cars enqueued\n");
        bridge.isRunning = false;

        try {
            bridgeThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}