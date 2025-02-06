package org.hpss.lab1;

import java.util.Arrays;

class Data {
    public static int N = 3; // can be changed to a 100

    public static int[][] generateMatrix(int value){
        int[][] matrix = new int[N][N];
        for(int i = 0; i < N; ++i) {
            Arrays.fill(matrix[i], value);
        }

        return matrix;
    }

    public static int[] generateVector(int value){

        int[] vector = new int[N];
        Arrays.fill(vector, value);

        return vector;
    }

    public static void main(String[] args) {
        Data data = new Data();
        T1 t1 = new T1();
        T2 t2 = new T2();
        T3 t3 = new T3();


    }
}

// Stream T1: C = A + B * (MO * ME)
class T1 extends Thread{
    public void run() {

    }
}

// Stream T2: MF = MG * (MK * ML) - MK
class T2 extends Thread{
    public void run() {

    }
}

// Stream T3: O = TRANS(MP * MR) * V
class T3 extends Thread{
    public void run() {

    }
}
