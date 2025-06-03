package org.hpss.lab5;

import java.util.Arrays;

import static org.hpss.lab5.Lab5.N;

class Data {

    public static void fillMatrixByValue(int[][] matrix, int value) {
        for (int i = 0; i < N; i++) {
            Arrays.fill(matrix[i], value);
        }
    }

    // b = max(MXh * MR)
    public static int calculateB(int startIndex, int[][] MX, int[][] MR) {
        int[] vectorB = new int[N];
        int[] vectorZ = new int[N];

        System.arraycopy(B, startIndex, vectorB, 0, Lab5.H);
        System.arraycopy(Z, startIndex, vectorZ, 0, Lab5.H);

        return multiplyVectorVector(vectorB, vectorZ);
    }

    public static int[] calculateC(int startIndex, int[] R, int[][] MC) {

        int[][] MCh = new int[N][Lab5.H];

        for(int i = 0; i < N; ++i) {
            System.arraycopy(MC[i], startIndex, MCh[i], 0, Lab5.H);
        }

        return multiplyVectorMatrix(R, MCh);
    }

    // a = (BH + CH) * ZH + b
    public static int calculateRes(int threadId, int[] B, int[] C, int[] Z,
                                     int b) {
        int[] sumBC = new int[N];
        for (int i = 0; i < N; i++) sumBC[i] = B[i] + C[i];
        return multiplyVectorVector(sumBC, Z) + b;
    }

    static int maxMatrix(int[][] matrix) {
        return Arrays.stream(matrix)
                .flatMapToInt(Arrays::stream)
                .max()
                .orElseThrow(() -> new IllegalArgumentException("MaxMatrix() error. Matrix is empty"));
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

        if(vector.length != matrix.length) {
            throw new IllegalArgumentException("Vector x Matrix multiplication failed: Different sizes\n" +
                    "v_size: " + vector.length + "; m_size: " + matrix.length + "x" + matrix[0].length);
        }

        int[] result = new int[matrix[0].length];
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                result[i] += vector[j] * matrix[j][i];
            }
        }
        return result;
    }
}

