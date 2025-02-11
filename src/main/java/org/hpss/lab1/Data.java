package org.hpss.lab1;

import java.util.*;

public class Data {
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

    public static int[][] multiplyMatrices(int[][] mA, int[][] mB){
        // matrices are always squares, so we ignore condition if mA_cols = mB_rows
        int[][] result = new int[N][N];

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                for(int k = 0; k < N; k++) {
                    result[i][j] += mA[i][k] * mB[k][j];
                }
            }
        }
        return result;
    }

    public static int[] multiplyVectorMatrix(int[] V, int[][] M) {
//        if(V.length != M.length) {
//            throw new IllegalArgumentException();
//        }

        int[] res = new int[N];

        for(int i = 0; i < N; ++i) {
            for(int j = 0; j < N; ++j) {
                res[i] += M[j][i] * V[j];
            }
        }
        return res;
    }

    static int[][] transposeMatrix(int[][] M) {
        int[][] res = new int[N][N];

        for(int i = 0; i < N; ++i) {
            for(int j = 0; j < N; ++j) {
                res[i][j] = M[j][i];
            }
        }
        return res;
    }

    static int maxMatrix(int[][] M) {
        int max = Integer.MIN_VALUE;

        for (int[] row : M)
            for (int val : row)
                max = Math.max(max, val);
        return max;
    }

    static int scalarProduct(int[] A, int[] B) {
        int sum = 0;

        for(int i = 0; i < N; ++i) {
            sum += A[i] * B[i];
        }
        return sum;

    }

    static int[] sortVector(int[] A) {
        int[] sorted = A.clone();
        Arrays.sort(sorted);
        return sorted;
    }


}

