package org.hpss.lab5;

import mpi.MPI;

import static org.hpss.lab5.Data.maxMatrix;
import static org.hpss.lab5.Data.multiplyMatrices;
import static org.hpss.lab5.Lab5.H;
import static org.hpss.lab5.Lab5.N;

class T2 extends Thread {

    public void run() {

        int[][] MX = new int[N][N];
        int threadId = 1;
        int offset = N * H;

        System.out.println("T2 has started: ");

        // 1. Введення MX
        Data.fillMatrixByValue(MX, Lab5.DEFAULT_NUM);

        int[] B = new int[5 * H];
        MPI.COMM_WORLD.Recv(B, 0, B.length, MPI.INT, 0, 0);

        // Передати до T3: B, MX
        MPI.COMM_WORLD.Send(B, H, B.length - H, MPI.INT, 2, 1);
        MPI.COMM_WORLD.Send(MX, 2 * offset, MX.length * MX[0].length - (2 * offset), MPI.INT, 2, 2);

        // Прийняти ZH, MR, Cн від T3
        int[] Z = new int[N];
        int[][] MR = new int[N][N];
        int[] C = new int[N];

        // Передати T1: MXH, ZH, MR, CH
        MPI.COMM_WORLD.Send(MX, 0, N * N, MPI.INT, 0, 1);
        MPI.COMM_WORLD.Send(Z, 0, N, MPI.INT, 0, 2);
        MPI.COMM_WORLD.Send(C, 0, N, MPI.INT, 0, 3);
        MPI.COMM_WORLD.Send(MR, 0, N * N, MPI.INT, 0, 4);

        // b2 = max(MXH * MR)
        int[][] result = multiplyMatrices(MX, MR);
        int b2 = maxMatrix(result);

        // Прийняти b1 від T1
        int b1 = 0;
        MPI.COMM_WORLD.Recv(b1, 0, 1, MPI.INT, 0, 5);

        int b = Math.max(b1, b2);

        // Передати T1: b
        MPI.COMM_WORLD.Send(b, 0, 1, MPI.INT, 0, 6);

        // a = (BH + CH) * ZH + b
        int a = Data.calculateRes(threadId, B, C, Z, b);

        // Передати T1: a
        MPI.COMM_WORLD.Send(a, 0, 1, MPI.INT, 0, 7);


        System.out.println("T2 has ended ");
    }
}
