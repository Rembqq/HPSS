package org.hpss.lab2;

import java.util.Arrays;
import java.util.Scanner;


// Stream T3: T = S*(MO*MP) + SORT(S)*MR
class T3 extends Thread {
    public void run() {
        System.out.println("T3 has started: ");

        // 	Введення даних
        int[][] MO = new int[Data.N][Data.N];
        int[][] MP = new int[Data.N][Data.N];
        int[][] MR = new int[Data.N][Data.N];
        int[] S = new int[Data.N];
        int[] T = new int[Data.N];

        // num = 3 is a default value for T3
        int num = 3;

        if (Data.N <= 3) {
            System.out.println("T3 value: ");
            Scanner scanner = new Scanner(System.in);
            num = scanner.nextInt();
        }

        Data.fillT3(num, MO, MP, MR, S);

        // 	Обчислення F1
        int[][] MO_MP = Data.multiplyMatrices(MO, MP);
        int[] S_MO_MP = Data.multiplyVectorMatrix(S, MO_MP);
        //Data.maxA = Arrays.stream(Data.S).max().orElse(Integer.MIN_VALUE);
        int[] sortedS = Data.sortVector(S);
        int[] sortedS_MR = Data.multiplyVectorMatrix(sortedS, MR);
        for (int i = 0; i < Data.N; i++) {
            T[i] = S_MO_MP[i] + sortedS_MR[i];
        }

        // 	Виведення результату
        if(Data.N < 10) {
            System.out.println("T3: (T) = " + Arrays.toString(T));
        }
        System.out.println("T3 has ended ");
    }
}
