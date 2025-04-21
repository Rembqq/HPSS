package org.hpss.lab3;

public class Monitor {
    private int s1 = 0;
    private int resReady = 0;
    private int p = 0;
    private int a = 0;
    private int d = 0;

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

    public synchronized void signalAComplete() {
        resReady = (resReady + 1) % 3;
        if (resReady == 0) {
            notifyAll();
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
