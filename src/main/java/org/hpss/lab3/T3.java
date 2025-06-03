package org.hpss.lab3;

import java.util.Arrays;

class T3 extends Thread {
    static int[] B = new int[Lab3.N];
    static int[] A3 = new int[Lab3.H];
    static int[] A = new int[Lab3.N];

    @Override
    public void run() {
        Monitor m = Lab3.monitor;
        int threadId = 2;

        System.out.println("T3 has started: ");

        // 1. Введення A, B
        Arrays.fill(A, Lab3.DEFAULT_NUM);
        Arrays.fill(B, Lab3.DEFAULT_NUM);
        m.setP(Lab3.DEFAULT_NUM);

        // 2. Сигнал задачі T1, T2, T3 про введення D, MB
        m.signalStageReady();

        // 3. Чекати на введення даних в задачі T1, T2, T3
        m.waitStage();

        // 4. Обчислення1 a3 = (BH * CH)
        int ai = Data.calculateA(Lab3.H * 2, T3.B, T4.Z);

        // 5.	Обчислення2 a = a + a3 (КД1; СР)
        m.addToA(ai);

        // 6. Сигнал задачі T1, T2, T4 про завершення обчислень a
        m.signalStageReady();

        // 7. Чекати на завершення обчислень a в T1, T2, T4
        m.waitStage();

        // 8. Обчислення3: Cн = R * MCн
        int[] Ch = Data.calculateC(Lab3.H * threadId, T4.R, T1.MC);
        m.setC(threadId, Ch);

        // 9. Сигнал задачі T1, T2, T4 про завершення етапу обчислення
        m.signalC();

        // 10. Чекати на введення даних в задачі T1, T2, T4
        m.waitC();

        // 11. Копія a3 = a (КД2)
        int a3 = m.getA();

        // 12. Копія p3 = p (КД3)
        int p3 = m.getP();

        // 13. Копія d3 = d (КД3)
        int d3 = m.getD();


        // 14. Обчислення3: Ah = C * MD * p + a * E * d
        A3 = Data.calculateRes(threadId, a3, p3, d3, m.getC(),
                T1.E, T2.MD);

        // 15. Чекати на завершення обчислень Ah в T1, T2, T4
        m.waitForA();

        System.arraycopy(T1.A1, 0, A, 0, T1.A1.length);
        System.arraycopy(T2.A2, 0, A, T1.A1.length, T2.A2.length);
        System.arraycopy(A3, 0, A, T1.A1.length +
                T2.A2.length, A3.length);
        System.arraycopy(T4.A4, 0, A, T1.A1.length +
                T2.A2.length + A3.length, T4.A4.length);

        // 16. Виведення результату A

        if(Lab3.N <= 20) {
            System.out.println(Arrays.toString(A));
        }

        System.out.println("T3 has ended ");
    }
}
