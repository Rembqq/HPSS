package org.hpss.lab5;

import mpi.MPI;

import java.util.Arrays;

import static org.hpss.lab5.Lab5.H;
import static org.hpss.lab5.Lab5.N;

class T3 {
    public void run() {

        int[] Z = new int[N];
        int threadId = 2;
        int offset = N * H;

        System.out.println("T3 has started: ");

        // 1. Введення Z
        Arrays.fill(Z, Lab5.DEFAULT_NUM);

        int[] B = new int[4 * H];
        int[][] MX = new int[5 * H][N];
        MPI.COMM_WORLD.Recv(B, 0, B.length, MPI.INT, 1, 1);
        MPI.COMM_WORLD.Recv(MX, 0, MX.length * MX[0].length, MPI.INT, 1, 2);

        // Передати до T4: B, MX, Z
        MPI.COMM_WORLD.Send(B, H, B.length - H, MPI.INT, 3, 3);
        MPI.COMM_WORLD.Send(MX, offset, MX.length * MX[0].length - offset, MPI.INT, 3, 4);
        MPI.COMM_WORLD.Send(Z, 3 * offset, B.length - (3 * H), MPI.INT, 3, 5);

        System.out.println("T3 has ended ");
    }
}
