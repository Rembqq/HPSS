package org.hpss.lab1;

// Потік T1: c = MAX(MA*MB)*(A*B)

//public class T1 implements Runnable{
//    @Override
//    public void run() {
//        int[][] MA = Data.generateMatrix(1);
//        int[][] MB = Data.generateMatrix(1);
//        int[] A = Data.generateVector(1);
//        int[] B = Data.generateVector(1);
//
//        int[][] MA_MB = Data.multiplyMatrices(MA, MB);
//        int maxMA_MB = Data.maxMatrix(MA_MB);
//        int scalarAB = Data.scalarProduct(A, B);
//        int c = maxMA_MB * scalarAB;
//
//        System.out.println("T1 (c) = " + c);
//
//    }
//}
class T1 extends Thread {
    public void run() {
        Data.c = Data.maxMatrix(Data.multiplyMatrices(Data.MA, Data.MB)) * Data.dotProduct(Data.A, Data.B);
        System.out.println("T1: c = " + Data.c);
    }
}
