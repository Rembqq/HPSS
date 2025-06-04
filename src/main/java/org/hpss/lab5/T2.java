package org.hpss.lab5;

import mpi.MPI;

import java.util.Arrays;

import static org.hpss.lab5.Data.*;
import static org.hpss.lab5.Lab5.H;
import static org.hpss.lab5.Lab5.N;

class T2 {
    public static void run() {

        System.out.println("T2 has started: ");

        int rank = MPI.COMM_WORLD.Rank();
        int blockSize = N * H;

        // Поточні значення
        int[] Ch = new int[H];
        int[] Bh = new int[H];
        int[] Zh = new int[H];
        int[] MXh = new int[H * N];

        // Транзитні значення
        int[] transitMX = new int[N * N];
        int[] transitZ = new int[2 * H];
        int[] transitC = new int[2 * H];
        int[] MR = new int[N * N];

        // 1. Введення MX
        Arrays.fill(transitMX, Lab5.DEFAULT_NUM);

        int[] transitB = new int[5 * H];
        MPI.COMM_WORLD.Recv(transitB, 0, transitB.length, MPI.INT, rank - 1, 0);

        // Передати до T3: B, MX
        MPI.COMM_WORLD.Send(transitB, H, transitB.length - H, MPI.INT, rank + 1, 1);
        MPI.COMM_WORLD.Send(transitMX, 2 * blockSize, transitMX.length - (2 * blockSize),
                MPI.INT, rank + 1, 2);

        // Прийняти ZH, MR, Cн від T3
        MPI.COMM_WORLD.Recv(transitZ, 0, transitZ.length, MPI.INT, rank + 1, 18);
        MPI.COMM_WORLD.Recv(transitC, 0, transitC.length, MPI.INT, rank + 1, 19);
        MPI.COMM_WORLD.Recv(MR, 0, N * N, MPI.INT, rank + 1, 20);

        // Передати T1: MXH, ZH, MR, CH
        MPI.COMM_WORLD.Send(transitMX, 0, blockSize, MPI.INT, rank - 1, 21);
        MPI.COMM_WORLD.Send(transitZ, 0, H, MPI.INT, rank - 1, 22);
        MPI.COMM_WORLD.Send(transitC, 0, H, MPI.INT, rank - 1, 23);
        MPI.COMM_WORLD.Send(MR, 0, N * N, MPI.INT, rank - 1, 24);

        // Розпаковка поточних значень
        System.arraycopy(transitC, H, Ch, 0, H);
        System.arraycopy(transitB, H, Bh, 0, H);
        System.arraycopy(transitZ, H, Zh, 0, H);
        System.arraycopy(transitMX, 0, MXh, 0, blockSize);

        // b2 = max(MXH * MR)
        int[][] MX_MR_prod = multiplyMatrices(unflat(MXh, H, N), unflat(MR, N, N));
        int b2 = maxMatrix(MX_MR_prod);

        // Прийняти b1 від T1
        int[] b1 = new int[1];
        MPI.COMM_WORLD.Recv(b1, 0, 1, MPI.INT, rank - 1, 25);

        int[] bMax12 = new int[] { Math.max(b1[0], b2) } ;

        // Передати T3: bMax12
        MPI.COMM_WORLD.Send(bMax12, 0, 1, MPI.INT, rank + 1, 26);

        // Прийом b від T3
        int[] b = new int[1];
        MPI.COMM_WORLD.Recv(b, 0, 1, MPI.INT, rank + 1, 33);

        // Передати задачі Т1 дані: b
        MPI.COMM_WORLD.Send(b, 0, 1, MPI.INT, rank - 1, 34);

        // Обчислення 3: a = (BH + CH) * ZH + b
        int a2 = Data.calculateRes(Bh, Ch, Zh, b[0]);
        int[] a36 = new int[1];

        MPI.COMM_WORLD.Recv(a36, 0, 1, MPI.INT, rank + 1, 38);

        int[] sumA26 = new int[] { a2 + a36[0] };

        // Передати задачі Т1 дані: a
        MPI.COMM_WORLD.Send(sumA26, 0, 1, MPI.INT, rank - 1, 39);

        System.out.println("T2 has ended ");
    }
}