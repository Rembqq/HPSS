package org.hpss.lab3;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

public class T1 extends Thread {
    public static int[][] MC = new int[Lab3.N][Lab3.N];

    public static int[] E = new int[Lab3.N];
    private int[] A1 = new int[Lab3.H];

    @Override
    public void run() {
        try {

            System.out.println("T1 has started: ");
            Monitor m = Lab3.monitor;

            // 	Введення MC, E
            Data.fillMatrixByValue(MC, Lab3.DEFAULT_NUM);
            Arrays.fill(E, Lab3.DEFAULT_NUM);

            // Бар'єр 1
            // 3 & 4. Сигнал про введення MC, E, чекати на введення даних в задачі T2, T3, T4
            m.


            // 5. Обчислення1: a1 = (Bh * Ch)
            int ai = Data.calculateA(0, T2.B, С);

            // Атомік 1
            // 6. Обчислення2: a = a + a1 (КД1; СР)
            Lab3.a.getAndAdd(ai);

            // Бар'єр 2
            // 7 & 8. Сигнал про завершення обчислень a, чекати на
            // завершення обчислень a в T2, T3, T4
            bar2.await();

            // Атомік 1
            // 9. Копія a1 = a (КД2)
            a1 = Lab3.a.get();

            // Семафор 1
            // 10.Копія x1 = x
            sem.acquire();
            x1 = x; // (КД3)
            sem.release();

            // 11. Обчислення3: Zh = a1 * Dh + E*(MA * MBh) * x1
            Z1 = Data.calculateZ(0, a1, T4.D, T3.getE(), T2.getMA(),
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

            System.out.println("T1 has ended ");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread 1 was interrupted.");
        } catch (BrokenBarrierException e) {
            System.out.println("Barrier is broken.");
        }
    }
}
