package org.hpss.lab2;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

class T3 extends Thread {

    static int[] E = new int[Lab2.N], Z3 = new int[Lab2.H];
    private int x3, a3;

    private final Semaphore sem;
    private static final Object Cs1 = new Object();
    private final CountDownLatch latch;
    private final CyclicBarrier bar1, bar2;

    public T3(CyclicBarrier bar1, CyclicBarrier bar2,
              Semaphore sem, CountDownLatch latch) {
        this.sem = sem;
        this.latch = latch;
        this.bar1 = bar1;
        this.bar2 = bar2;
    }

    public static int[] getE() {
        synchronized (Cs1) {
            return E;
        }
    }
    @Override
    public void run() {

        try {
            // Виведення повідомлення про початок виконання потоку T1
            System.out.println("T3 has started: ");

            // 2. Введення E
            Arrays.fill(E, Lab2.DEFAULT_NUM);

            // 3. Сигнал задачі T1, T2, T4 про введення E
            // 4. Чекати на введення даних в задачі T1, T2, T4
            bar1.await();

            //5. Обчислення1 a3 = (BH * CH)
            int ai = Data.calculateA(Lab2.H * 2, T2.B, T1.С);

            //6. Обчислення2 a = a + a3 (КД1; СР)
            Lab2.a.getAndAdd(ai);

            //7. Сигнал задачі T1, T2, T4 про завершення обчислень a
            //8. Чекати на завершення обчислень a в T1, T2, T4
            bar2.await();

            //9. Копія a3 = a (КД2)
            a3 = Lab2.a.get();

            //10. Копія x3 = x (КД3)
            sem.acquire();
            x3 = T1.x;
            sem.release();

            //11. Обчислення3: ZH = a3 * DH + E*(MA * MBH) * x3
            Z3 = Data.calculateZ(Lab2.H * 2, a3, T4.D, getE(),
                    T2.getMA(), T4.MB, x3);

            //12. Сигнал задачі T1 про завершення обчислень ZH
            latch.countDown();

            //13. Завершення виконання задачі Т3
            System.out.println("T3 has ended ");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread 3 was interrupted.");
        } catch (BrokenBarrierException e) {
            System.out.println("Barrier is broken.");
        }
    }
}
