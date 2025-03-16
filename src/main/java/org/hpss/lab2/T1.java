package org.hpss.lab2;

// Потік T1: c = MAX(MA*MB)*(A*B)

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

class T1 extends Thread {

    private Semaphore sem;
    private CyclicBarrier bar1, bar2;
    private CountDownLatch latch;

    public static int[] С = new int[Lab2.N];
    public static int x, a1, x1;

    public T1(CyclicBarrier bar1, CyclicBarrier bar2, Semaphore sem, CountDownLatch latch) {
        this.bar1 = bar1;
        this.bar2 = bar2;
        this.sem = sem;
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("T1 has started: ");

        // 	Введення даних
        Arrays.fill(C, 1);
        x = Lab2.DEFAULT_NUM;


        // num = 1 is a default value for T1
        int c, num = 1;

        if (Data.N <= 3) {
            System.out.println("T1 value: ");
            Scanner scanner = new Scanner(System.in);
            num = scanner.nextInt();
        }

        Data.fillT1(num, MA, MB, A, B);

        // 	Обчислення F1
        c = Data.maxMatrix(Data.multiplyMatrices(MA, MB)) * Data.dotProduct(A, B);

        // 	Виведення результату
        if(Data.N < 10) {
            System.out.println("T1: c = " + c);
        }
        System.out.println("T1 has ended ");
    }
}
