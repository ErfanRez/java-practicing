//! The following code is written by Erfan Rezaei
//! Operating Systems, Alireza Nikian, Spring 2025
//! Islamic Azad University of Najafabad

//? Printing (ABC | ACB | BAC |BCA |CAB |CBA )* using only Cyclic Barrier

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Barrier {

    public static void main(String[] args) {
        int numberOfThreads = 3;
        CyclicBarrier barrier = new CyclicBarrier(numberOfThreads, () -> {
            System.out.println();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    System.out.print("A");
                    barrier.await();
                }catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            while (true) {
                try {
                    System.out.print("B");
                    barrier.await();
                }catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t3 = new Thread(() -> {
            while (true) {
                try {
                    System.out.print("C");
                    barrier.await();
                }catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
