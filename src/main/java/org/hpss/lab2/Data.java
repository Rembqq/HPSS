package org.hpss.lab2;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hpss.lab2.Lab2.N;

class Data {

    public static void fillMatrixByValue(int[][] matrix, int value) {
        for (int i = 0; i < N; i++) {
            Arrays.fill(matrix[i], value);
        }
    }

    // a = (B * C)
    public static int calculateA(int startIndex, int[] B, int[] C) {
        int[] vectorB = new int[N];
        int[] vectorC = new int[N];

        System.arraycopy(B, startIndex, vectorB, 0, Lab2.H);
        System.arraycopy(C, startIndex, vectorC, 0, Lab2.H);

        return multiplyVectorVector(vectorB, vectorC);
    }

    // Z = a * D + E*(MA * MB) * x
    public static int[] calculateZ(int startIndex, int a, int[] D, int[] E, int[][] MA,
                                 int[][] MB, int x) {
        int[] vectorD = new int[Lab2.H];
        int[][] matrixMB = new int[Lab2.H][Lab2.H];

        System.arraycopy(D, startIndex, vectorD, 0, Lab2.H);
        for(int i = 0; i < MB.length; ++i) {
            System.arraycopy(MB[i], startIndex, matrixMB[i], 0, Lab2.H);
        }

        return  sumVectors(multiplyVectorScalar(a, vectorD), multiplyVectorScalar(x,
                multiplyVectorMatrix(E, multiplyMatrices(MA, matrixMB))));

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

    static int[] sumVectors(int[] v1, int[] v2) {

        if(v1.length != v2.length) {
            throw new IllegalArgumentException("Vector addition error. " +
                    "Vectors are not equal in size");
        }

        int[] res = new int[v1.length];

        for(int i = 0; i < v1.length; ++i) {
            res[i] = v1[i] + v2[i];
        }

        return res;
    }


    static int[] multiplyVectorScalar(int scalar, int[] vector) {
        for(int i = 0; i < vector.length; ++i) {
            vector[i] *= scalar;
        }
        return vector;
    }

    static int multiplyVectorVector(int[] v1, int[] v2) {
        int res = 0;

        if(v1.length != v2.length) {
            throw new IllegalArgumentException("Vector multiplication failed: Different sizes");
        }

        for(int i = 0; i < v1.length; ++i) {
            res += v1[i] * v2[i];
        }

        return res;

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

