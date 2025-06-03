package org.hpss.lab5;

import org.hpss.lab3.Monitor;

import java.util.Arrays;
public class T6 extends Thread{

    static int[] R = new int[Lab5.N];
    static int[] Z = new int[Lab5.N];
    static int[] A4 = new int[Lab5.H];

    @Override
    public void run() {
        Monitor m = Lab5.monitor;
        int threadId = 3;

        System.out.println("T4 has started: ");

        // 1. Введення R, Z
        Arrays.fill(R, Lab5.DEFAULT_NUM);
        Arrays.fill(Z, Lab5.DEFAULT_NUM);

        // 2. Сигнал задачі T1, T2, T3 про введення R, Z
        m.signalStageReady();

        // 3. Чекати на введення даних в задачі T1, T2, T3
        m.waitStage();

        // 4. Обчислення1 a4 = (BH * CH)
        int ai = Data.calculateA(Lab5.H * threadId, T3.B, Z);

        // 5.	Обчислення2 a = a + a4 (КД1; СР)
        m.addToA(ai);

        // 6. Сигнал задачі T1, T2, T3 про завершення обчислень a
        m.signalStageReady();

        // 7. Чекати на завершення обчислень a в T1, T2, T3
        m.waitStage();

        // 8. Обчислення3: Cн = R * MCн
        int[] Ch = Data.calculateC(Lab5.H * threadId, T4.R, T1.MC);
        m.setC(threadId, Ch);

        // 9. Сигнал задачі T1, T2, T4 про завершення етапу обчислення
        m.signalC();

        // 10. Чекати на введення даних в задачі T1, T2, T4
        m.waitC();

        // 11. Копія a4 = a (КД2)
        int a4 = m.getA();

        // 12. Копія p4 = p (КД3)
        int p4 = m.getP();

        // 13. Копія d4 = d (КД3)
        int d4 = m.getD();

        // 14. Обчислення3: Ah = (R * MC) * MD * p + a * E * d
        A4 = Data.calculateRes(threadId, a4, p4, d4, m.getC(),
                T1.E, T2.MD);

        // 15. Сигнал задачі T3 про завершення обчислень A4
        m.signalAComplete();

        System.out.println("T4 has ended");
    }
}
