package org.hpss.lab3;

class T2 extends Thread {
    static int[] A2 = new int[Lab3.H];
    static int[][] MD = new int[Lab3.N][Lab3.N];

    @Override
    public void run() {
        Monitor m = Lab3.monitor;
        int threadId = 1;

        System.out.println("T2 has started: ");

        // 1. Введення MD, d
        Data.fillMatrixByValue(MD, Lab3.DEFAULT_NUM);
        m.setD(Lab3.DEFAULT_NUM);

        // 2. Сигнал задачі T1, T3, T4 про введення D, MB
        m.signalStageReady();

        // 3. Чекати на введення даних в задачі T1, T3, T4
        m.waitStage();

        // 4. Обчислення1 a2 = (BH * CH)
        int ai = Data.calculateA(Lab3.H, T3.B, T4.Z);

        // 5.	Обчислення2 a = a + a2 (КД1; СР)
        m.addToA(ai);

        // 6. Сигнал задачі T1, T3, T4 про завершення обчислень a
        m.signalStageReady();

        // 7. Чекати на завершення обчислень a в T1, T3, T4
        m.waitStage();

        // 8. Обчислення3: Cн = R * MCн
        Data.calculateC(Lab3.H * threadId, T4.R, T1.MC, T3.C);

        // 9. Сигнал задачі T1, T3, T4 про завершення етапу обчислення
        m.signalStageReady();

        // 10. Чекати на введення даних в задачі T1, T3, T4
        m.waitStage();

        // 11. Копія a2 = a (КД2)
        int a2 = m.getA();

        // 12. Копія p2 = p (КД3)
        int p2 = m.getP();

        // 13. Копія d2 = d (КД3)
        int d2 = m.getD();


        // 14. Обчислення3: Ah = (R * MC) * MD * p + a * E * d
        A2 = Data.calculateRes(threadId, a2, p2, d2, T3.C,
                T1.E, MD);

        // 15. Сигнал задачі T3 про завершення обчислень A4
        m.signalAComplete();

        System.out.println("T2 has ended ");
    }
}
