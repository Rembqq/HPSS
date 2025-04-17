package org.hpss.lab3;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

public class T4 extends Thread{

    static int[] R = new int[Lab3.N];
    static int[] Z = new int[Lab3.N];

    @Override
    public void run() {
        try {

            Monitor m = Lab3.monitor;
            System.out.println("T4 has started: ");

            // 1. Введення R, Z
            Arrays.fill(R, Lab3.DEFAULT_NUM);
            Arrays.fill(Z, Lab3.DEFAULT_NUM);

            // 2. Сигнал задачі T1, T2, T3 про введення D, MB
            m.signalInputReady();

            // 3. Чекати на введення даних в задачі T1, T2, T3
            m.waitForInput();

            // 4. Обчислення1 a4 = (BH * CH)
            int ai = Data.calculateA(Lab3.H * 3, T3.B, Z);

            // 5.	Обчислення2 a = a + a2 (КД1; СР)
            m.addToA(ai);

            // 6. Сигнал задачі T1, T2, T3 про завершення обчислень a
            m.signalInputReady();

            // 7. Чекати на завершення обчислень a в T1, T2, T3
            m.waitForInput();

            // 8. Копія a4 = a (КД2)
            int a4 = m.getA();

            // 9. Копія p4 = p (КД3)
            int p4 = m.getP();

            // 10. Копія d4 = d (КД3)
            int d4 = m.getD();


            // 11. Обчислення3: ZH = а * D2 + F2 * x

            Z4 = Data.calculateZ(Lab3.H * 3, a4, D, T3.getE(),
                    T2.getMA(), MB, x4);

            // 12. Сигнал задачі T3 про завершення обчислень ZH
            m.signalAComplete();

            System.out.println("T4 has ended ");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread 4 was interrupted.");
        } catch (BrokenBarrierException e) {
            System.out.println("Barrier is broken.");
        }
    }
}
