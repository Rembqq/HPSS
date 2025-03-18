package org.hpss.lab2;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class T1 extends Thread {

    private final Semaphore sem;
    private final CyclicBarrier bar1, bar2;
    private final CountDownLatch latch;

    public static int[] С = new int[Lab2.N];
    private int[] Z1 = new int[Lab2.H];
    private final int[] Z = new int[Lab2.N];

    public static int x, a1, x1;

    public T1(CyclicBarrier bar1, CyclicBarrier bar2,
              Semaphore sem, CountDownLatch latch) {
        this.bar1 = bar1;
        this.bar2 = bar2;
        this.sem = sem;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {

            // Виведення повідомлення про початок виконання потоку T1
            System.out.println("T1 has started: ");

            // 	Введення C, x
            Arrays.fill(С, Lab2.DEFAULT_NUM);
            x = Lab2.DEFAULT_NUM;

            // Бар'єр 1
            // 3 & 4. Сигнал про введення C, x, чекати на введення даних в задачі T2, T3, T4
            bar1.await();

            // 5. Обчислення1: a1 = (Bh * Ch)
            int ai = Data.calculateA(0, T2.B, С);

            // Атомік 1
            // 6. Обчислення2: a = a + a1 (КД1; СР)
            Lab2.a.getAndAdd(ai);

            // Бар'єр 2
            // 7 & 8. Сигнал про завершення обчислень a, чекати на
            // завершення обчислень a в
            bar2.await();

            // Атомік 1
            // 9. Копія a1 = a (КД2)
            a1 = Lab2.a.get();

            // Семафор 1
            // 10.Копія x1 = x
            sem.acquire();
            x1 = x; // (КД3)
            sem.release();

            // 11. Обчислення3: Zh = a1 * Dh + E*(MA * MBh) * x1
            Z1 = Data.calculateZ(0, a1, T4.D, T3.readE(), T2.readMA(),
                    T4.MB, x1);

            // CountDownLatch 1
            // 12. Чекати на завершення обчислень Zh в T2, T3, T4
            latch.await();

            System.arraycopy(Z1, 0, Z, 0, Z1.length);
            System.arraycopy(T2.Z2, 0, Z, Z1.length, T2.Z2.length);
            System.arraycopy(T3.Z3, 0, Z, Z1.length +
                    T2.Z2.length, T3.Z3.length);
            System.arraycopy(T4.Z4, 0, Z, Z1.length +
                    T2.Z2.length + T3.Z3.length, T4.Z4.length);

            // 13. Виведення результату Z
            System.out.println(Arrays.toString(Z));

            // Виведення повідомлення про завершення виконання потоку
            System.out.println("T1 has ended ");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread 1 was interrupted.");
        } catch (BrokenBarrierException e) {
            System.out.println("Barrier is broken.");
        }
    }
}
