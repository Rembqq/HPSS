package org.hpss.lab3;

/*
Лабораторна робота ЛР3 Варіант 6
A = (R*MC)*MD*p + (B*Z)*E*d
ПВВ1: MC, E
ПВВ2: MD, d
ПВВ3: A, B, p
ПВВ4: R, Z
Волковський М.І. ІМ-24
Дата 22.04.2024
 */

public class Lab3 {

    static int N = 10000, P = 4, DEFAULT_NUM = 1, H;
    static final Monitor monitor = new Monitor();

    public static void main(String[] args) {

        if (N % P != 0) {
            throw new IllegalArgumentException("N % P != 0");
        } else {
            H = N / P;
        }

        // Create timer
        long startTime = System.currentTimeMillis();

        Thread T1 = new T1();
        Thread T2 = new T2();
        Thread T3 = new T3();
        Thread T4 = new T4();

        T1.start();
        T2.start();
        T3.start();
        T4.start();

        try {
            T1.join();
            T2.join();
            T3.join();
            T4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long stopTime = System.currentTimeMillis();
        System.out.printf("Lab5 execution completed, it took %d ms\n", stopTime - startTime);
    }
}