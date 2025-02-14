package org.hpss.lab1;

import java.util.Arrays;

import static org.hpss.lab1.Data.N;

// Stream T3: T = S*(MO*MP) + SORT(S)*MR

//public class T3 implements Runnable{
//    @Override
//    public void run() {
//        int[][] MO = Data.generateMatrix(3);
//        int[][] MP = Data.generateMatrix(3);
//        int[][] MR = Data.generateMatrix(3);
//        int[] S = Data.generateVector(3);
//
//        int[] sortedS = Data.sortVector(S);
//        int[][] MO_MP = Data.multiplyMatrices(MO, MP);
//        int[] S_MO_MP = Data.multiplyVectorMatrix(S, MO_MP);
//        int[] sortedS_MR = Data.multiplyVectorMatrix(sortedS, MR);
//
//        int[] T = new int[N];
//
//        for (int i = 0; i < N; i++) {
//            T[i] = S_MO_MP[i] + sortedS_MR[i];
//        }
//
//        System.out.println("T3 (T) = " + Arrays.toString(T));
//
//    }
//}

// Stream T3: T = S*(MO*MP) + SORT(S)*MR
class T3 extends Thread {
    public void run() {
        //Data.T = Data.multiplyMatrices(Data.MO, Data.MP);
        int[][] MO_MP = Data.multiplyMatrices(Data.MO, Data.MP);
        int[] S_MO_MP = Data.multiplyVectorMatrix(Data.S, MO_MP);
        //Data.maxA = Arrays.stream(Data.S).max().orElse(Integer.MIN_VALUE);
        int[] sortedS = Data.sortVector(Data.S);
        int[] sortedS_MR = Data.multiplyVectorMatrix(sortedS, Data.MR);
        for (int i = 0; i < Data.N; i++) {
            Data.T[i] = S_MO_MP[i] + sortedS_MR[i];
        }
        System.out.println("T3: (T) = " + Arrays.toString(Data.T));
    }
}
