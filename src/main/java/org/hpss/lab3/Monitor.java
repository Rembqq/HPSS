package org.hpss.lab3;

import java.util.Arrays;

public class Monitor {
    private int inputReady = 0;
    private int aReady = 0;
    private int resReady = 0;
    private int p = Lab3.DEFAULT_NUM;
    private int a = Lab3.DEFAULT_NUM;
    private int d = Lab3.DEFAULT_NUM;
//    private final int[] E = new int[Lab3.N];
//    private final int[] A = new int[Lab3.N];
//    private final int[] B = new int[Lab3.N];
//    private final int[] R = new int[Lab3.N];
//    private final int[] Z = new int[Lab3.N];
//    private final int[][] MC = new int[Lab3.N][Lab3.N];
//    private final int[][] MD = new int[Lab3.N][Lab3.N];

    public synchronized void signalInputReady() {
        inputReady++;
        notifyAll();
    }

    public synchronized void waitForInput() {
        while (inputReady < 4) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void addToA(int value) {
        a += value;
        aReady++;
        notifyAll();
    }

    public synchronized int getA() {
//        while (aReady < Lab3.P) {
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }
        return a;
    }

    public synchronized int getP() {
        return p;
    }

    public synchronized int getD() {
        return d;
    }

    public synchronized void setA(int a) {
        this.a = a;
    }
    public synchronized void setP(int p) {
        this.p = p;
    }
    public synchronized void setD(int d) {
        this.d = d;
    }

    public synchronized void signalAComplete() {
        resReady++;
        if(resReady == 4) {
            notifyAll();
        }
    }

    public synchronized void waitForA() {
        while (resReady < 3) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
