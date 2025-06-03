package org.hpss.lab5;

import mpi.MPI;

import java.util.Arrays;

import static org.hpss.lab5.Data.*;
import static org.hpss.lab5.Lab5.H;
import static org.hpss.lab5.Lab5.N;

class T3 {
    public static void run() {

        System.out.println("T3 has started: ");

        // int threadId = 3;
        int rank = 2;
        int blockSize = N * H;

        int[] MR = new int[N * N];

        // Поточні значення
        int[] Ch = new int[H];
        int[] Bh = new int[H];
        int[] Zh = new int[H];
        int[] MXh = new int[H * N];

        // Транзитні значення
        int[] transitZ = new int[N];
        int[] transitC = new int[H * (rank + 1)];
        int[] transitB = new int[4 * H];
        int[] transitMX = new int[4 * H * N];

        // 1. Введення Z
        Arrays.fill(transitZ, Lab5.DEFAULT_NUM);

        // Прийняти дані від задачі Т2: B4H, MX4H
        MPI.COMM_WORLD.Recv(transitB, 0, transitB.length, MPI.INT, rank - 1, 1);
        MPI.COMM_WORLD.Recv(transitMX, 0, transitMX.length, MPI.INT, rank - 1, 2);

        // Передати до T4: B, MX, Z
        MPI.COMM_WORLD.Send(transitB, H, transitB.length - H, MPI.INT, rank + 1, 3);
        MPI.COMM_WORLD.Send(transitMX, blockSize, transitMX.length - blockSize, MPI.INT, rank + 1, 4);
        MPI.COMM_WORLD.Send(transitZ, 3 * H, transitZ.length - (3 * H), MPI.INT, rank + 1, 5);

        // Прийняти дані від задачі Т4: C3H, MR
        MPI.COMM_WORLD.Recv(transitC, 0, transitC.length, MPI.INT, rank + 1, 16);
        MPI.COMM_WORLD.Recv(MR, 0, N * N, MPI.INT, rank + 1, 17);

        // Передати до T2: Z, C, MR
        MPI.COMM_WORLD.Send(transitZ, 0, H * rank, MPI.INT, rank - 1, 18);
        MPI.COMM_WORLD.Send(transitC, 0, H * rank, MPI.INT, rank - 1, 19);
        MPI.COMM_WORLD.Send(MR, 0, N * N, MPI.INT, rank - 1, 20);

        // Розпаковка поточних значень
        System.arraycopy(transitC, H * rank, Ch, 0, H);
        System.arraycopy(transitB, 0, Bh, 0, H);
        System.arraycopy(transitZ, H * rank, Zh, 0, H);
        System.arraycopy(transitMX, 0, MXh, 0, blockSize);

//        for (int i = rank * H; i < (rank + 1) * H; ++i) {
//            System.arraycopy(transitMX, i * N, MXh, i * N, N);
//        }

        // b3 = max(MXH * MR)
        int[][] MX_MR_prod = multiplyMatrices(unflat(MXh, H, N), unflat(MR, N, N));
        int[] b3 = new int[] { maxMatrix(MX_MR_prod) };

        // Прийняти b2 від T2
        int b2 = 0;
        MPI.COMM_WORLD.Recv(b3, 0, 1, MPI.INT, rank - 1, 26);

        int[] bMax13 = new int[] { Math.max(b2, b3[0]) };

        // Передати T4: bMax12
        MPI.COMM_WORLD.Send(bMax13, 0, 1, MPI.INT, rank + 1, 27);

        //System.out.println("Rank " + rank + " checkpoint 2 ");

        // Прийом b від T4
        int[] b = new int[1];
        MPI.COMM_WORLD.Recv(b, 0, 1, MPI.INT, rank + 1, 32);

        //System.out.println("Rank " + rank + " checkpoint 3 ");

        // Передати задачі Т2 дані: b
        MPI.COMM_WORLD.Send(b, 0, 1, MPI.INT, rank - 1, 33);

        // Обчислення 3: a = (BH + CH) * ZH + b
        int a3 = Data.calculateRes(Bh, Ch, Zh, b[0]);
        int[] a46 = new int[1];

        MPI.COMM_WORLD.Recv(a46, 0, 1, MPI.INT, rank + 1, 37);

        int[] sumA36 = new int[] { a3 + a46[0] };

        // Передати задачі Т2 дані: a
        MPI.COMM_WORLD.Send(sumA36, 0, 1, MPI.INT, rank - 1, 38);

        System.out.println("T3 has ended ");
    }
}
