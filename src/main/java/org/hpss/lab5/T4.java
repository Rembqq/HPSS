package org.hpss.lab5;

import mpi.MPI;

import java.util.Arrays;

import static org.hpss.lab5.Data.maxMatrix;
import static org.hpss.lab5.Data.multiplyMatrices;
import static org.hpss.lab5.Lab5.H;
import static org.hpss.lab5.Lab5.N;

public class T4 {
    public static void run() {

        System.out.println("T4 has started: ");

        // int threadId = 3;
        int rank = 3;
        int blockSize = N * H;

        int[][] MR = new int[N][N];

        // Поточні значення
        int[] Ch = new int[H];
        int[] Bh = new int[H];
        int[] Zh = new int[H];
        int[][] MXh = new int[H][N];

        // Транзитні значення
        int[] transitB = new int[3 * H];
        int[][] transitMX = new int[3 * H][N];
        int[] transitZ = new int[3 * H];
        int[] transitC = new int[N];

        // 1. Введення C, MR
        Arrays.fill(transitC, Lab5.DEFAULT_NUM);
        Data.fillMatrixByValue(MR, Lab5.DEFAULT_NUM);

        MPI.COMM_WORLD.Recv(transitB, 0, transitB.length, MPI.INT, rank - 1, 3);
        MPI.COMM_WORLD.Recv(transitMX, 0, transitMX.length * transitMX[0].length, MPI.INT, rank - 1, 4);
        MPI.COMM_WORLD.Recv(transitZ, 0, transitZ.length, MPI.INT, rank - 1, 5);

        // Передати до T5: B, MX, Z, C, MR
        MPI.COMM_WORLD.Send(transitB, H, transitB.length - H, MPI.INT, rank + 1, 6);
        MPI.COMM_WORLD.Send(transitMX, blockSize, transitMX.length * transitMX[0].length - blockSize, MPI.INT, rank + 1, 7);
        MPI.COMM_WORLD.Send(transitZ, H, transitZ.length - H, MPI.INT, rank + 1, 8);
        MPI.COMM_WORLD.Send(transitC, 4 * H, transitC.length - (4 * H), MPI.INT, rank + 1, 9);

        MPI.COMM_WORLD.Send(MR, 0, MR.length * MR[0].length, MPI.INT, rank + 1, 10);

        // Передати до T3: C, MR
        MPI.COMM_WORLD.Send(transitC, 0, H * rank, MPI.INT, rank - 1, 16);
        MPI.COMM_WORLD.Send(MR, 0, N * N, MPI.INT, rank - 1, 17);

        // Розпаковка поточних значень
        System.arraycopy(transitC, H * rank, Ch, 0, H);
        System.arraycopy(transitB, 0, Bh, 0, H);
        System.arraycopy(transitZ, 0, Zh, 0, H);

        for(int i = rank * H; i < (rank + 1) * H ; ++i) {
            System.arraycopy(transitMX[i], 0, MXh[i], 0, N);
        }

        // b4 = max(MXH * MR)
        int[][] MX_MR_prod = multiplyMatrices(MXh, MR);
        int b4 = maxMatrix(MX_MR_prod);

        // Прийняти b3 від T3
        int b3 = 0;
        MPI.COMM_WORLD.Recv(b3, 0, 1, MPI.INT, rank - 1, 27);

        int bMax14 = Math.max(b3, b4);

        // Передати T5: bMax14
        MPI.COMM_WORLD.Send(bMax14, 0, 1, MPI.INT, rank + 1, 28);

        // Прийом b від T5
        int b = -1;
        MPI.COMM_WORLD.Recv(b, 0, 1, MPI.INT, 1, 31);

        // Передати задачі Т3 дані: b
        MPI.COMM_WORLD.Send(b, 0, 1, MPI.INT, rank - 1, 32);

        // Обчислення 3: a = (BH + CH) * ZH + b
        int a4 = Data.calculateRes(Bh, Ch, Zh, b);
        int a56 = 0;

        MPI.COMM_WORLD.Recv(a56, 0, 1, MPI.INT, rank + 1, 36);

        int sumA46 = a4 + a56;

        // Передати задачі Т3 дані: a
        MPI.COMM_WORLD.Send(sumA46, 0, 1, MPI.INT, rank - 1, 37);

        System.out.println("T4 has ended ");
    }
}
