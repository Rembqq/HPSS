package org.hpss.lab2;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class T4 extends Thread{

    static int[] D = new int[Lab2.N], Z2 = new int[Lab2.H];
    static int[][] MB = new int[Lab2.N][Lab2.N];
    private int x4, a4;

    private Semaphore sem;
    private CountDownLatch latch;
    private CyclicBarrier bar1, bar2;

    public T4(Semaphore sem, CountDownLatch latch,
              CyclicBarrier bar1, CyclicBarrier bar2) {
        this.sem = sem;
        this.latch = latch;
        this.bar1 = bar1;
        this.bar2 = bar2;
    }

    @Override
    public void run() {

    }
}
