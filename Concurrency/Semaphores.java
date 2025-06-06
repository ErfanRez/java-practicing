//! The following code is written by Erfan Rezaei
//! Operating Systems, Alireza Nikian, Spring 2025
//! Islamic Azad University of Najafabad

//? Printing (ABBC)* using only semaphore's acquire and release

import java.util.concurrent.Semaphore;

public class Semaphores {
    public static void main(String[] args) {
        Semaphore x = new Semaphore(1);
        Semaphore y = new Semaphore(0);
        Semaphore z = new Semaphore(0);

        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    x.acquire();
                    System.out.print("A");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    y.release();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            while (true) {
                try {
                    y.acquire();
                    System.out.print("B");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    z.release();
                }
            }
        });

        Thread t3 = new Thread(() -> {
            while (true) {
                try {
                    z.acquire();
                    y.release();
                    z.acquire();
                    System.out.println("C");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    x.release();
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
