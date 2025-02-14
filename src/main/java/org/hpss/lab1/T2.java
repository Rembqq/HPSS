package org.hpss.lab1;

import java.util.Arrays;

// Потік T2: MF = TRANS(MG) + MK*ML

//public class T2 implements Runnable{
//
//    @Override
//    public void run() {
//        int[][] MG = Data.generateMatrix(2);
//        int[][] MK = Data.generateMatrix(2);
//        int[][] ML = Data.generateMatrix(2);
//
//        int[][] transMG = Data.transposeMatrix(MG);
//        int[][] MK_ML = Data.multiplyMatrices(MK, ML);
//
//        int[][] MF = new int[Data.N][Data.N];
//        for (int i = 0; i < Data.N; i++) {
//            for (int j = 0; j < Data.N; j++) {
//                MF[i][j] = transMG[i][j] + MK_ML[i][j];
//            }
//        }
//        System.out.println("T2 (MF) = " + Arrays.deepToString(MF));
//    }
//}
class T2 extends Thread {
    public void run() {
        Data.MF = Data.multiplyMatrices(Data.MK, Data.ML);
        Data.MG = Data.transposeMatrix(Data.MG);
        for (int i = 0; i < Data.N; i++) {
            for (int j = 0; j < Data.N; j++) {
                Data.MF[i][j] += Data.MG[j][i];
            }
        }
        System.out.println("T2: (MF) = " + Arrays.deepToString(Data.MF));
    }
}
