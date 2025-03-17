package org.hpss.lab2;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

class T2 extends Thread {

    static int[] B = new int[Lab2.N], Z2 = new int[Lab2.H];
    static int[][] MA = new int[Lab2.N][Lab2.N];
    private int x2, a2;

    private Semaphore sem;
    private CountDownLatch latch;
    private CyclicBarrier bar1, bar2;

    public T2(CyclicBarrier bar1, CyclicBarrier bar2,
              Semaphore sem, CountDownLatch latch) {
        this.sem = sem;
        this.latch = latch;
        this.bar1 = bar1;
        this.bar2 = bar2;
    }

    public void run() {

        try {
            // 1. Початок виконання задачі Т2
            System.out.println("T2 has started: ");

            // 2. Введення B, MA
            Arrays.fill(B, Lab2.DEFAULT_NUM);
            Data.fillMatrixByValue(MA, Lab2.DEFAULT_NUM);

            // 3. Сигнал задачі T1, T3, T4 про введення B, MA
            // 4. Чекати на введення даних в задачі T1, T3, T4

            bar1.await();

            // 5. Обчислення1 a2 = (BH * CH)
            int ai = Data.calculateA(Lab2.H, B, T1.С);

            // 6.	Обчислення2 a = a + a2 (КД1; СР)
            Lab2.a.getAndAdd(ai);

            // 7. Сигнал задачі T1, T3, T4 про завершення обчислень a
            // 8. Чекати на завершення обчислень a в T1, T3, T4

            bar2.await();

            // 9. Копія a2 = a (КД2)
            a2 = Lab2.a.get();

            // 10. Копія x2 = x (КД3)
            sem.acquire();
            x2 = T1.x;
            sem.release();

            // 11. Обчислення3: ZH = а * D2 + F2 * x

            Z2 = Data.calculateZ(Lab2.H, a2, T4.D, T3.E, MA, T4.MB, x2);

            // 	Виведення результату
            if (Data.N < 10) {
                System.out.println("T2: MF = " + Arrays.deepToString(MF));
            }
            System.out.println("T2 has ended ");
        } catch (BrokenBarrierException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
