package org.hpss.lab2;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;


// Stream T3: T = S*(MO*MP) + SORT(S)*MR
class T3 extends Thread {

    static int[] E = new int[Lab2.N], Z2 = new int[Lab2.H];
    private int x3, a3;

    private Semaphore sem;
    private CountDownLatch latch;
    private CyclicBarrier bar1, bar2;

    public T3(CyclicBarrier bar1, CyclicBarrier bar2,
              Semaphore sem, CountDownLatch latch) {
        this.sem = sem;
        this.latch = latch;
        this.bar1 = bar1;
        this.bar2 = bar2;
    }
    public void run() {
        System.out.println("T3 has started: ");


        // 	Виведення результату
        if(Data.N < 10) {
            System.out.println("T3: (T) = " + Arrays.toString(T));
        }
        System.out.println("T3 has ended ");
    }
}
