package org.hpss.lab1;

import java.util.Scanner;

public class Lab1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter N: ");
        int N = scanner.nextInt();

        Data.initialize(N);

        // Create timer
        long startTime = System.currentTimeMillis();

        T1 t1 = new T1();
        T2 t2 = new T2();
        T3 t3 = new T3();

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long stopTime = System.currentTimeMillis();
        System.out.printf("Lab1 execution completed, it took %d ms", stopTime - startTime);
    }
}