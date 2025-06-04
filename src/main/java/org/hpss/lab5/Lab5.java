package org.hpss.lab5;

/*
Лабораторна робота ЛР5 Варіант 10
a = ((B+C)*Z)+ max (MX*MR)
ПВВ1: a, B
ПВВ2: MX
ПВВ3: Z
ПВВ4: C, MR
Волковський М.І. ІМ-24
Дата 04.06.2025
 */

import mpi.MPI;

public class Lab5 {
    static final int N = 18;
    static final int P = 6;
    static final int DEFAULT_NUM = 1;
    static int H;

    public static void main(String[] args) {
        // Ініціалізація MPI
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();

        if (N % P != 0) {
            if (rank == 0) {
                System.err.println("N % P != 0, завершення програми.");
            }
            MPI.Finalize();
            return;
        } else {
            H = N / P;
        }

        long startTime = 0;
        if (rank == 0) {
            startTime = System.currentTimeMillis();
        }

        switch (rank) {
            case 0 -> T1.run();
            case 1 -> T2.run();
            case 2 -> T3.run();
            case 3 -> T4.run();
            case 4 -> T5.run();
            case 5 -> T6.run();
            default -> System.out.println("Процес з рангом " + rank + " не визначено.");
        }

        MPI.COMM_WORLD.Barrier(); // дочекатися завершення всіх процесів

        if (rank == 0) {
            long stopTime = System.currentTimeMillis();
            System.out.printf("Lab5 MPI execution completed, it took %d ms\n", stopTime - startTime);
        }

        MPI.Finalize();
    }
}