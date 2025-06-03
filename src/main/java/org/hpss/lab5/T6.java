package org.hpss.lab5;

import mpi.MPI;

import static org.hpss.lab5.Data.*;
import static org.hpss.lab5.Data.unflat;
import static org.hpss.lab5.Lab5.H;
import static org.hpss.lab5.Lab5.N;

public class T6{

    public static void run() {

        // int threadId = 3;
        int rank = 5;

        System.out.println("T6 has started: ");

        int[] Bh = new int[H];
        int[] MXh = new int[H * N];
        int[] Zh = new int[H];
        int[] Ch = new int[H];
        int[] MR = new int[N * N];

        MPI.COMM_WORLD.Recv(Bh, 0, Bh.length, MPI.INT, rank - 1, 11);
        MPI.COMM_WORLD.Recv(MXh, 0, MXh.length, MPI.INT, rank - 1, 12);
        MPI.COMM_WORLD.Recv(Zh, 0, Zh.length, MPI.INT, rank - 1, 13);
        MPI.COMM_WORLD.Recv(Ch, 0, Ch.length, MPI.INT, rank - 1, 14);
        MPI.COMM_WORLD.Recv(MR, 0, MR.length, MPI.INT, rank - 1, 15);

        // b6 = max(MXH * MR)
        int[][] MX_MR_prod = multiplyMatrices(unflat(MXh, H, N), unflat(MR, N, N));
        int b6 = maxMatrix(MX_MR_prod);

        // Прийняти b5 від T5
        int[] b5 = new int[1];
        MPI.COMM_WORLD.Recv(b5, 0, 1, MPI.INT, rank - 1, 29);

        int[] b = new int[] { Math.max(b5[0], b6) };

        // Передати T5: b
        MPI.COMM_WORLD.Send(b, 0, 1, MPI.INT, rank - 1, 30);

        // Обчислення 3: a = (BH + CH) * ZH + b
        int[] a6 = new int[] { Data.calculateRes(Bh, Ch, Zh, b[0]) };

        // Передати задачі Т5 дані: a
        MPI.COMM_WORLD.Send(a6, 0, 1, MPI.INT, rank - 1, 35);

        System.out.println("T6 has ended ");
    }
}
