//! The following code is written by Erfan Rezaei
//! Operating Systems, Alireza Nikian, Spring 2025
//! Islamic Azad University of Najafabad

//? Implementing Cyclic Barrier with 3 semaphores for 3 processes and no shared variable

import java.util.concurrent.Semaphore;

public class CyclicBrWithSemaphores {
    public static void main(String[] args) {
        Semaphore x = new Semaphore(1);
        Semaphore y = new Semaphore(0);
        Semaphore z = new Semaphore(0);

        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    x.acquire();
                    System.out.println("Thread 1 starting part");
                    Thread.sleep(1000);
                    y.release();
                    x.acquire();
                    System.out.println("Thread 1 ending part");
                    Thread.sleep(1000);
                    y.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            while (true) {
                try {
                    y.acquire();
                    System.out.println("Thread 2 starting part");
                    Thread.sleep(1000);
                    z.release();
                    y.acquire();
                    System.out.println("Thread 2 ending part");
                    Thread.sleep(1000);
                    z.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t3 = new Thread(() -> {
            while (true) {
                try {
                    z.acquire();
                    System.out.println("Thread 3 starting part");
                    Thread.sleep(1000);
                    x.release();
                    z.acquire();
                    System.out.println("Thread 3 ending part");
                    Thread.sleep(1000);
                    x.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
