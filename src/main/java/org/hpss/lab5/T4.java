package org.hpss.lab5;

import mpi.MPI;
import org.hpss.lab3.Monitor;

import java.util.Arrays;

import static org.hpss.lab5.Lab5.H;
import static org.hpss.lab5.Lab5.N;

public class T4 {


    public void run() {

        int[] C = new int[N];
        int[][] MR = new int[N][N];
        int threadId = 3;
        int offset = N * H;

        System.out.println("T4 has started: ");

        // 1. Введення C, MR
        Arrays.fill(C, Lab5.DEFAULT_NUM);
        Data.fillMatrixByValue(MR, Lab5.DEFAULT_NUM);

        int[] B = new int[3 * H];
        int[][] MX = new int[4 * H][N];
        int[] Z = new int[3 * H];
        MPI.COMM_WORLD.Recv(B, 0, B.length, MPI.INT, 0, 3);
        MPI.COMM_WORLD.Recv(MX, 0, MX.length * MX[0].length, MPI.INT, 2, 4);
        MPI.COMM_WORLD.Recv(Z, 0, Z.length, MPI.INT, 2, 5);

        // Передати до T4: B
        MPI.COMM_WORLD.Send(B, H, B.length - H, MPI.INT, 2, 0);
        MPI.COMM_WORLD.Send(MX, offset, MX.length * MX[0].length - offset, MPI.INT, 2, 1);
        MPI.COMM_WORLD.Send(Z, 3 * offset, B.length - (3 * H), MPI.INT, 2, 1);

        System.out.println("T4 has ended ");
    }
}
