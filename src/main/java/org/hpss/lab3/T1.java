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
        m.signalStageReady();

        // 3. Чекати на введення даних в задачі T2, T3, T4
        m.waitStage();

        // 4. Обчислення1 a1 = (BH * CH)
        int ai = Data.calculateA(0, T3.B, T4.Z);

        // 5.	Обчислення2 a = a + a1 (КД1; СР)
        m.addToA(ai);

        // 6. Сигнал задачі T2, T3, T4 про завершення обчислень a
        m.signalStageReady();

        // 7. Чекати на завершення обчислень a в T2, T3, T4
        m.waitStage();

        // 8. Обчислення3: Cн = R * MCн
        int[] Ch = Data.calculateC(0, T4.R, MC, T3.C);
        m.setC(threadId, Ch);

        // 9. Сигнал задачі T2, T3, T4 про завершення етапу обчислення
        m.signalC();

        // 10. Чекати на введення даних в задачі T2, T3, T4
        m.waitC();

        // 11. Копія a1 = a (КД2)
        int a1 = m.getA();

        // 12. Копія p1 = p (КД3)
        int p1 = m.getP();

        // 13. Копія d1 = d (КД3)
        int d1 = m.getD();

        // 14. Обчислення3: Ah = C * MD * p + a * E * d
        A1 = Data.calculateRes(threadId, a1, p1, d1, m.getC(),
                E, T2.MD);

        // 15. Сигнал задачі T3 про завершення обчислень A4
        m.signalAComplete();

        System.out.println("T1 has ended ");
    }
}
