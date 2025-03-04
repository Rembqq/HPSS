package org.hpss.lab1;

import java.util.Arrays;
import java.util.Scanner;

// Потік T2: MF = TRANS(MG) + MK*ML
class T2 extends Thread {
    public void run() {
        System.out.println("T2 has started: ");

        // 	Введення даних
        int[][] MG = new int[Data.N][Data.N];
        int[][] MK = new int[Data.N][Data.N];
        int[][] ML = new int[Data.N][Data.N];

        int[][] MK_ML_tmp;

        int[][] MF = new int[Data.N][Data.N];

        // num = 2 is a default value for T2
        int num = 1;

        if (Data.N <= 3) {
            System.out.println("T2 value: ");
            Scanner scanner = new Scanner(System.in);
            num = scanner.nextInt();
        }

        Data.fillT2(num, MG, MK, ML);
        // 	Обчислення F1
        MK_ML_tmp = Data.multiplyMatrices(MK, ML);
        MG = Data.transposeMatrix(MG);

        for (int i = 0; i < Data.N; i++) {
            for (int j = 0; j < Data.N; j++) {
                MF[i][j] = MG[i][j] + MK_ML_tmp[i][j];
            }
        }

        // 	Виведення результату
        if(Data.N < 10) {
            System.out.println("T2: MF = " + Arrays.deepToString(MF));
        }
        System.out.println("T2 has ended ");
    }
}
