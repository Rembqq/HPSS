package org.hpss.lab2;

import java.util.Arrays;

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
        System.out.println(Thread.currentThread().getName() + " calculates Z");
        int[] vectorD = new int[Lab2.H];
        int[][] matrixMB = new int[Lab2.N][Lab2.H];

        System.arraycopy(D, startIndex, vectorD, 0, Lab2.H);

        System.out.println( Thread.currentThread().getName() + " " + startIndex + " " + a + "\n" + Arrays.toString(D) + "\n" + Arrays.toString(E) +
                             "\n" + Arrays.deepToString(MA) + "\n" + Arrays.deepToString(MB) + "\n" + x);

        for(int i = 0; i < Lab2.N; ++i) {
            System.arraycopy(MB[i], 0, matrixMB[i], 0, Lab2.H);
        }

        System.out.println(Thread.currentThread().getName() + " counted Z");

        return sumVectors(multiplyVectorScalar(a, vectorD), multiplyVectorScalar(x,
                multiplyVectorMatrix(E, multiplyMatrices(MA, matrixMB))));

    }

    static int[][] multiplyMatrices(int[][] m1, int[][] m2) {

        if (m1[0].length != m2.length) {
            throw new IllegalArgumentException(
                    "Columns of the first matrix must be equal to the number of rows of the second matrix.");
        }

        int[][] result = new int[N][N];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2[0].length; j++) {
                for (int k = 0; k < m1[0].length; k++) {
                    result[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return result;
    }

    static int[] sumVectors(int[] v1, int[] v2) {

//        if(v1.length != v2.length) {
//            throw new IllegalArgumentException("Vector addition error. " +
//                    "Vectors are not equal in size");
//        }


        System.out.println(Arrays.toString(v1));
        System.out.println(Arrays.toString(v2));

        System.out.println("DIVIDER\n\n");

        int minLength = Math.min(v1.length, v2.length);

        int[] res = new int[minLength];

        for(int i = 0; i < minLength; ++i) {
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
}

