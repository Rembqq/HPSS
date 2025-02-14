package org.hpss.lab1;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Lab1 {
//    public static void main(String[] args) throws InterruptedException {
//        ExecutorService executorService = Executors.newFixedThreadPool(3);
//
//        executorService.execute(new T1());
//        executorService.execute(new T2());
//        executorService.execute(new T3());
//
//        executorService.shutdown();
//        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
//
//        System.out.println("Lab1 has ended");
//    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter N: ");
        int N = scanner.nextInt();

        System.out.print("Enter method (1: Fixed, 2: Random): ");
        int method = scanner.nextInt();

        Data.initialize(N, method);

//        if (method == 1) {
//            Data.initialize(N, method);
//        } else {
//            Random rand = new Random();
//            int r = rand.nextInt(10) + 1;
//            System.out.println("Випадкове значення яким ініціалізуються всі "r);
//            Data.initialize(N, r);
//        }

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

        System.out.println("Lab1 execution completed.");
    }
}