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
        m.signalInputReady();

        // 3. Чекати на введення даних в задачі T1, T3, T4
        m.waitForInput();

        // 4. Обчислення1 a2 = (BH * CH)
        int ai = Data.calculateA(Lab3.H, T3.B, T4.Z);

        // 5.	Обчислення2 a = a + a2 (КД1; СР)
        m.addToA(ai);

        // 6. Сигнал задачі T1, T3, T4 про завершення обчислень a
//        m.signalInputReady();
//
//        // 7. Чекати на завершення обчислень a в T1, T3, T4
//        m.waitForInput();

        // 8. Копія a2 = a (КД2)
        int a2 = m.getA();

        // 9. Копія p2 = p (КД3)
        int p2 = m.getP();

        // 10. Копія d2 = d (КД3)
        int d2 = m.getD();


        // 11. Обчислення3: Ah = (R * MC) * MD * p + a * E * d
        A2 = Data.calculateRes(threadId, a2, p2, d2, T4.R,
                T1.E, T1.MC, MD);

        // 12. Сигнал задачі T3 про завершення обчислень A4
        m.signalAComplete();

        System.out.println("T2 has ended ");
    }
}
