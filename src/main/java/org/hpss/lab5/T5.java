package org.hpss.lab5;

import mpi.MPI;

import static org.hpss.lab5.Data.maxMatrix;
import static org.hpss.lab5.Data.multiplyMatrices;
import static org.hpss.lab5.Lab5.H;
import static org.hpss.lab5.Lab5.N;

public class T5 {
    public static void run() {

        System.out.println("T5 has started: ");

        // int threadId = 3;
        int rank = 4;
        int blockSize = N * H;

        int[][] MR = new int[N][N];

        // Поточні значення
        int[] Ch = new int[H];
        int[] Bh = new int[H];
        int[] Zh = new int[H];
        int[][] MXh = new int[H][N];

        // Транзитні значення
        int[] transitB = new int[2 * H];
        int[][] transitMX = new int[2 * H][N];
        int[] transitZ = new int[2 * H];
        int[] transitC = new int[2 * H];

        // Прийняти дані від задачі Т4: B2H, MX2H, MR, C2H, Z2H
        MPI.COMM_WORLD.Recv(transitB, 0, transitB.length, MPI.INT, rank - 1, 6);
        MPI.COMM_WORLD.Recv(transitMX, 0, transitMX.length * transitMX[0].length, MPI.INT, rank - 1, 7);
        MPI.COMM_WORLD.Recv(transitZ, 0, transitZ.length, MPI.INT, rank - 1, 8);
        MPI.COMM_WORLD.Recv(transitC, 0, transitC.length, MPI.INT, rank - 1, 9);
        MPI.COMM_WORLD.Recv(MR, 0, MR.length * MR[0].length, MPI.INT, rank - 1, 10);

        // Передати до T6: B, MX, Z, C, MR
        MPI.COMM_WORLD.Send(transitB, H, transitB.length - H, MPI.INT, rank + 1, 11);
        MPI.COMM_WORLD.Send(transitMX, blockSize, transitMX.length * transitMX[0].length - blockSize, MPI.INT, rank + 1, 12);
        MPI.COMM_WORLD.Send(transitZ, H, transitZ.length - H, MPI.INT, rank + 1, 13);
        MPI.COMM_WORLD.Send(transitC, H, transitC.length - H, MPI.INT, rank + 1, 14);

        MPI.COMM_WORLD.Send(MR, 0, MR.length * MR[0].length, MPI.INT, rank + 1, 15);

        // Розпаковка поточних значень
        System.arraycopy(transitC, 0, Ch, 0, H);
        System.arraycopy(transitB, 0, Bh, 0, H);
        System.arraycopy(transitZ, 0, Zh, 0, H);

        for(int i = rank * H; i < (rank + 1) * H ; ++i) {
            System.arraycopy(transitMX[i], 0, MXh[i], 0, N);
        }

        // b5 = max(MXH * MR)
        int[][] MX_MR_prod = multiplyMatrices(MXh, MR);
        int b5 = maxMatrix(MX_MR_prod);

        // Прийняти b4 від T4
        int b4 = 0;
        MPI.COMM_WORLD.Recv(b4, 0, 1, MPI.INT, rank - 1, 28);

        int bMax15 = Math.max(b4, b5);

        // Передати T6: bMax15
        MPI.COMM_WORLD.Send(bMax15, 0, 1, MPI.INT, rank + 1, 29);

        // Прийом b від T6
        int b = -1;
        MPI.COMM_WORLD.Recv(b, 0, 1, MPI.INT, rank + 1, 30);

        // Передати задачі Т4 дані: b
        MPI.COMM_WORLD.Send(b, 0, 1, MPI.INT, rank - 1, 31);

        // Обчислення 3: a = (BH + CH) * ZH + b
        int a5 = Data.calculateRes(Bh, Ch, Zh, b);
        int a6 = 0;

        MPI.COMM_WORLD.Recv(a6, 0, 1, MPI.INT, rank + 1, 35);

        int sumA56 = a5 + a6;

        // Передати задачі Т4 дані: a
        MPI.COMM_WORLD.Send(sumA56, 0, 1, MPI.INT, rank - 1, 36);

        System.out.println("T5 has ended ");
    }
}
