package org.hpss.lab2;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Lab2 {

    static int N = 16, P = 4, DEFAULT_NUM = 0, H;
    static AtomicInteger a = new AtomicInteger(0);

    public static void main(String[] args) {

        if (N % P != 0) {
            throw new IllegalArgumentException("N % P != 0");
        } else {
            H = N / P;
        }

        CyclicBarrier bar1 = new CyclicBarrier(P);
        CyclicBarrier bar2 = new CyclicBarrier(P);

        Semaphore sem = new Semaphore(1);

        CountDownLatch latch = new CountDownLatch(3);

        // Create timer
        long startTime = System.currentTimeMillis();

        Thread T1 = new T1(bar1, bar2, sem, latch);
        Thread T2 = new T2(bar1, bar2, sem, latch);
        Thread T3 = new T3(bar1, bar2, sem, latch);
        Thread T4 = new T3(bar1, bar2, sem, latch);

        T1.start();
        T2.start();
        T3.start();
        T4.start();

        try {
            T1.join();
            T2.join();
            T3.join();
            T4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long stopTime = System.currentTimeMillis();
        System.out.printf("Lab2 execution completed, it took %d ms", stopTime - startTime);
    }
}