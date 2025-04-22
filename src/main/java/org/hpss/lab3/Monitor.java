package org.hpss.lab3;

public class Monitor {
    private int s1 = 0;
    private int cFlag = 0;
    private int resReady = 0;
    private int p = 0;
    private int a = 0;
    private int d = 0;
    private final int[] C = new int[Lab3.N];

    public synchronized void signalStageReady() {
        s1++;
        if (s1 == 4) {
            notifyAll();
        }
        else if (s1 > 4) {
            s1 -= 4;
        }
    }

    public synchronized void waitStage() {
        if (s1 < Lab3.P) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void addToA(int value) {
        a += value;
    }

    public synchronized int getA() {
        return a;
    }

    public synchronized int getP() {
        return p;
    }

    public synchronized int getD() {
        return d;
    }
    public synchronized void setP(int p) {
        this.p = p;
    }
    public synchronized void setD(int d) {
        this.d = d;
    }

    public synchronized void signalC() {
        cFlag++;
        if (cFlag == 4) {
            notifyAll();
        }
        else if (cFlag > 4) {
            cFlag -= 4;
        }
    }
    public synchronized void setC(int threadId, int[] sourceC) {
        System.arraycopy(sourceC, 0, this.C, threadId * Lab3.H, Lab3.H);
    }

    public synchronized int[] getC() {
        return C;
    }
    public synchronized void waitC() {
        if (cFlag < Lab3.P) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void signalAComplete() {
        resReady++;

        if (resReady == 3) {
            notifyAll();
        }
        else if (resReady > 3) {
            resReady -= 3;
        }
    }

    public synchronized void waitForA() {
        if (resReady < 3) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
