package org.hpss.lab3;

import java.util.Arrays;

public class T1 extends Thread {
    public static int[] A1 = new int[Lab3.H];
    public static int[][] MC = new int[Lab3.N][Lab3.N];

    public static int[] E = new int[Lab3.N];

    @Override
    public void run() {
        Monitor m = Lab3.monitor;
        int threadId = 0;

        System.out.println("T1 has started: ");

        // 1. Введення MC, E
        Data.fillMatrixByValue(MC, Lab3.DEFAULT_NUM);
        Arrays.fill(E, Lab3.DEFAULT_NUM);

        // 2. Сигнал задачі T2, T3, T4 про введення MC, E
        m.signalInputReady();

        // 3. Чекати на введення даних в задачі T2, T3, T4
        m.waitForInput();

        // 4. Обчислення1 a1 = (BH * CH)
        int ai = Data.calculateA(0, T3.B, T4.Z);

        // 5.	Обчислення2 a = a + a1 (КД1; СР)
        m.addToA(ai);

        // 6. Сигнал задачі T2, T3, T4 про завершення обчислень a
//        m.signalInputReady();
//
//        // 7. Чекати на завершення обчислень a в T2, T3, T4
//        m.waitForInput();

        // 8. Копія a1 = a (КД2)
        int a1 = m.getA();

        // 9. Копія p1 = p (КД3)
        int p1 = m.getP();

        // 10. Копія d1 = d (КД3)
        int d1 = m.getD();


        // 11. Обчислення3: Ah = (R * MC) * MD * p + a * E * d
        A1 = Data.calculateRes(threadId, a1, p1, d1, T4.R,
                E, MC, T2.MD);

        // 12. Сигнал задачі T3 про завершення обчислень A4
        m.signalAComplete();

        System.out.println("T1 has ended ");
    }
}
