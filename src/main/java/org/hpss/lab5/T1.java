package org.hpss.lab5;

import mpi.MPI;
import java.util.Arrays;

import static org.hpss.lab5.Data.maxMatrix;
import static org.hpss.lab5.Data.multiplyMatrices;
import static org.hpss.lab5.Lab5.H;
import static org.hpss.lab5.Lab5.N;

public class T1 {

    public static void run() {

        int threadId = 0;

        System.out.println("Process T1 (rank 0) working...");

        // Ініціалізація вектора B
        int[] B = new int[N];
        Arrays.fill(B, Lab5.DEFAULT_NUM);

        // Передача даних до T2 (rank 1)
        MPI.COMM_WORLD.Send(B, H, B.length - H, MPI.INT, 1, 0);

        // Прийом даних від T2
        int[][] MXh = new int[N][H];
        int[] Ch = new int[H];
        int[] Zh = new int[H];
        int[][] MR = new int[N][N];

        MPI.COMM_WORLD.Recv(MXh, 0, N * H, MPI.INT, 1, 1);
        MPI.COMM_WORLD.Recv(Zh, 0, H, MPI.INT, 1, 2);
        MPI.COMM_WORLD.Recv(Ch, 0, H, MPI.INT, 1, 3);

        MPI.COMM_WORLD.Recv(MR, 0, N * N, MPI.INT, 1, 4);

        int[][] result = multiplyMatrices(MXh, MR);
        int b1 = maxMatrix(result);

        // Передача b1 до T2
        MPI.COMM_WORLD.Send(b1, 0, 1, MPI.INT, 1, 5);

        // Прийом b від T2
        int b = -1;
        MPI.COMM_WORLD.Recv(b, 0, 1, MPI.INT, 1, 6);

        // Обчислення a = (BH + CH) * ZH + b

        int a = Data.calculateRes(threadId, B, Ch, Zh, b);

        // Прийом a від T2
        int[] finalA = new int[1];
        MPI.COMM_WORLD.Recv(finalA, 0, 1, MPI.INT, 1, 7);

        // Виведення результату
        System.out.println("Результат a: " + finalA[0]);

        MPI.Finalize();
        System.out.println("T1 has ended ");
    }
}
