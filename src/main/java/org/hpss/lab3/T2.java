package lab3;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

class T2 extends Thread {

    static int[] B = new int[Lab3.N], Z2 = new int[Lab3.H];
    static int[][] MA = new int[Lab3.N][Lab3.N];
    private int x2, a2;

    private final Semaphore sem;

    private final static ReentrantLock Cs2 = new ReentrantLock();
    private final CountDownLatch latch;
    private final CyclicBarrier bar1, bar2;

    public T2(CyclicBarrier bar1, CyclicBarrier bar2,
              Semaphore sem, CountDownLatch latch) {
        this.sem = sem;
        this.latch = latch;
        this.bar1 = bar1;
        this.bar2 = bar2;
    }

    public static int[][] getMA() {
        Cs2.lock();
        try {
            return MA;
        } finally {
            Cs2.unlock();
        }
    }

    @Override
    public void run() {

        try {
            System.out.println("T2 has started: ");

            // 1. Введення B, MA
            Arrays.fill(B, Lab3.DEFAULT_NUM);
            Data.fillMatrixByValue(MA, Lab3.DEFAULT_NUM);

            // 2. Сигнал задачі T1, T3, T4 про введення B, MA
            // 3. Чекати на введення даних в задачі T1, T3, T4

            bar1.await();

            // 4. Обчислення1 a2 = (BH * CH)
            int ai = Data.calculateA(Lab3.H, B, T1.С);

            // 5. Обчислення2 a = a + a2 (КД1; СР)
            Lab3.a.getAndAdd(ai);

            // 6. Сигнал задачі T1, T3, T4 про завершення обчислень a
            // 7. Чекати на завершення обчислень a в T1, T3, T4

            bar2.await();

            // 8. Копія a2 = a (КД2)
            a2 = Lab3.a.get();

            // 9. Копія x2 = x (КД3)
            sem.acquire();
            x2 = T1.x;
            sem.release();

            // 10. Обчислення3: ZH = а * D2 + F2 * x

            Z2 = Data.calculateZ(Lab3.H, a2, T4.D, T3.getE(), getMA(), T4.MB, x2);

            // 11. Сигнал задачі T1 про завершення обчислень ZH
            latch.countDown();

            System.out.println("T2 has ended ");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread 2 was interrupted.");
        } catch (BrokenBarrierException e) {
            System.out.println("Barrier is broken.");
        }
    }
}
