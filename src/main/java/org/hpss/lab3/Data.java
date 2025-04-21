package org.hpss.lab3;

import java.util.Arrays;

import static org.hpss.lab3.Lab3.N;

class Data {

    public static void fillMatrixByValue(int[][] matrix, int value) {
        for (int i = 0; i < N; i++) {
            Arrays.fill(matrix[i], value);
        }
    }

    // a = (B * Z)
    public static int calculateA(int startIndex, int[] B, int[] Z) {
        int[] vectorB = new int[N];
        int[] vectorZ = new int[N];

        System.arraycopy(B, startIndex, vectorB, 0, Lab3.H);
        System.arraycopy(Z, startIndex, vectorZ, 0, Lab3.H);

        return multiplyVectorVector(vectorB, vectorZ);
    }

    public static void calculateC(int startIndex, int[] R, int[][] MC, int[] C) {

        int[][] MCh = new int[N][Lab3.H];

        for(int i = 0; i < N; ++i) {

            System.arraycopy(MC[i], startIndex, MCh[i], 0, Lab3.H);
        }

        System.arraycopy(multiplyVectorMatrix(R, MCh), 0, C, startIndex, Lab3.H);
    }

    // A = C*MDh*p + a*E*d
    public static int[] calculateRes(int threadId, int a, int p, int d,
                                     int[] C, int[] E, int[][] MD) {

        int threadOffset = threadId * Lab3.H;
        int[][] MDh = new int[N][Lab3.H];
        int[] Eh = new int[Lab3.H];

        System.out.println(Arrays.deepToString(MD));

        System.arraycopy(E, threadOffset, Eh, 0, Lab3.H);

        for(int i = 0; i < N; ++i) {
            System.arraycopy(MD[i], threadOffset,
                    MDh[i], 0, Lab3.H);
        }

        System.out.println("ddoAwpdAWdjWPDa");
        System.out.println(Arrays.deepToString(MDh));

        return sumVectors(multiplyVectorScalar(p, multiplyVectorMatrix(C,
                MDh)), multiplyVectorScalar(d, multiplyVectorScalar(a, E)));
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

