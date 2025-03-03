package org.hpss.lab1;

import java.util.*;

class Data {
    static int N;

    static void initialize(int n) {
        N = n;
    }

    private static void fillMatrixByValue(int[][] matrix, int value) {
        for (int i = 0; i < N; i++) {
            Arrays.fill(matrix[i], value);
        }
    }

    static void fillT1(int value, int[][] MA, int[][] MB, int[] A, int[] B) {
        fillMatrixByValue(MA, value);
        fillMatrixByValue(MB, value);
        Arrays.fill(A, value);
        Arrays.fill(B, value);
    }

    static void fillT2(int value, int[][] MG, int[][] MK, int[][] ML) {
        fillMatrixByValue(MG, value);
        fillMatrixByValue(MK, value);
        fillMatrixByValue(ML, value);
    }

    static void fillT3(int value, int[][] MO, int[][] MP, int[][] MR, int[] S) {
        fillMatrixByValue(MO, value);
        fillMatrixByValue(MP, value);
        fillMatrixByValue(MR, value);
        Arrays.fill(S, value);
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

    static int maxMatrix(int[][] matrix) {
        return Arrays.stream(matrix).flatMapToInt(Arrays::stream).max().orElse(Integer.MIN_VALUE);
    }

    static int dotProduct(int[] vec1, int[] vec2) {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            sum += vec1[i] * vec2[i];
        }
        return sum;
    }

    static int[][] multiplyMatrices(int[][] m1, int[][] m2) {
        int[][] result = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    result[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return result;
    }

    static int[] multiplyVectorMatrix(int[] vector, int[][] matrix) {
        int[] result = new int[N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                result[i] += vector[j] * matrix[j][i];
            }
        }
        return result;
    }

    static int[] sortVector(int[] vec) {
        int[] sorted = vec.clone();
        Arrays.sort(sorted);
        return sorted;
    }
}

