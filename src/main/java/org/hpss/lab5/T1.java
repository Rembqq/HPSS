package org.hpss.lab5;

import mpi.MPI;
import java.util.Arrays;

import static org.hpss.lab5.Data.*;
import static org.hpss.lab5.Lab5.H;
import static org.hpss.lab5.Lab5.N;

public class T1 {
    public static void run() {

        System.out.println("Process T1 (rank 0) working...");

        // int threadId = 3;
        int rank = 0;

        // Прийом даних від T2
        int[] MXh = new int[H * N];
        int[] Ch = new int[H];
        int[] Zh = new int[H];
        int[] Bh = new int[H];

        // Транзитні значення
        int[] transitB = new int[N];
        int[] MR = new int[N * N];

        Arrays.fill(transitB, Lab5.DEFAULT_NUM);

        // Передача даних до T2 (rank 1)
        MPI.COMM_WORLD.Send(transitB, H, transitB.length - H, MPI.INT, rank + 1, 0);

        MPI.COMM_WORLD.Recv(MXh, 0, N * H, MPI.INT, rank + 1, 21);
        MPI.COMM_WORLD.Recv(Zh, 0, H, MPI.INT, rank + 1, 22);
        MPI.COMM_WORLD.Recv(Ch, 0, H, MPI.INT, rank + 1, 23);

        MPI.COMM_WORLD.Recv(MR, 0, N * N, MPI.INT, rank + 1, 24);

        // Розпаковка поточних значень
        System.arraycopy(transitB, 0, Bh, 0, H);

        int[][] MX_MR_prod = multiplyMatrices(unflat(MXh, H, N), unflat(MR, N, N));
        int b1 = maxMatrix(MX_MR_prod);

        // Передача b1 до T2
        MPI.COMM_WORLD.Send(b1, 0, 1, MPI.INT, rank + 1, 25);

        // Прийом b від T2
        int b = -1;
        MPI.COMM_WORLD.Recv(b, 0, 1, MPI.INT, rank + 1, 34);

        // Обчислення 3: a = (BH + CH) * ZH + b
        int a1 = Data.calculateRes(Bh, Ch, Zh, b);
        int a26 = 0;

        MPI.COMM_WORLD.Recv(a26, 0, 1, MPI.INT, rank + 1, 39);

        int a = a1 + a26;

        // Виведення результату
        System.out.println("Результат a: " + a);

        MPI.Finalize();
        System.out.println("T1 has ended ");
    }
}
