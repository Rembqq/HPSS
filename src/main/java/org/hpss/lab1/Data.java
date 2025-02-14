package org.hpss.lab1;

import java.util.*;

//public class Data {
//    public static int N = 3; // can be changed to a 100
//
//    public static int[][] generateMatrix(int value){
//        int[][] matrix = new int[N][N];
//        for(int i = 0; i < N; ++i) {
//            Arrays.fill(matrix[i], value);
//        }
//        return matrix;
//    }
//
//    public static int[] generateVector(int value){
//
//        int[] vector = new int[N];
//        Arrays.fill(vector, value);
//
//        return vector;
//    }
//
//    public static int[][] multiplyMatrices(int[][] mA, int[][] mB){
//        // matrices are always squares, so we ignore condition if mA_cols = mB_rows
//        int[][] result = new int[N][N];
//
//        for(int i = 0; i < N; i++) {
//            for(int j = 0; j < N; j++) {
//                for(int k = 0; k < N; k++) {
//                    result[i][j] += mA[i][k] * mB[k][j];
//                }
//            }
//        }
//        return result;
//    }
//
//    public static int[] multiplyVectorMatrix(int[] V, int[][] M) {
////        if(V.length != M.length) {
////            throw new IllegalArgumentException();
////        }
//
//        int[] res = new int[N];
//
//        for(int i = 0; i < N; ++i) {
//            for(int j = 0; j < N; ++j) {
//                res[i] += M[j][i] * V[j];
//            }
//        }
//        return res;
//    }
//
//    static int[][] transposeMatrix(int[][] M) {
//        int[][] res = new int[N][N];
//
//        for(int i = 0; i < N; ++i) {
//            for(int j = 0; j < N; ++j) {
//                res[i][j] = M[j][i];
//            }
//        }
//        return res;
//    }
//
//    static int maxMatrix(int[][] M) {
//        int max = Integer.MIN_VALUE;
//
//        for (int[] row : M)
//            for (int val : row)
//                max = Math.max(max, val);
//        return max;
//    }
//
//    static int scalarProduct(int[] A, int[] B) {
//        int sum = 0;
//
//        for(int i = 0; i < N; ++i) {
//            sum += A[i] * B[i];
//        }
//        return sum;
//
//    }
//
//    static int[] sortVector(int[] A) {
//        int[] sorted = A.clone();
//        Arrays.sort(sorted);
//        return sorted;
//    }
//}
class Data {
    static int N;
    static int[] A, B, S;
    static int[][] MA, MB, MG, MK, ML, MO, MP, MR;

    // T1 res
    static int c;

    // T2 res
    static int[][] MF;

    // T3 res
    static int[] T;

    static void initialize(int n, int method) {

        N = n;

        A = new int[N];
        B = new int[N];
        S = new int[N];
        T = new int[N];


        int[][][] matrices = {MA = new int[N][N], MB = new int[N][N], MG = new int[N][N],
                MK = new int[N][N], ML = new int[N][N], MO = new int[N][N],
                MP = new int[N][N], MR = new int[N][N], MF = new int[N][N]};

        if (method == 1) {
            Arrays.fill(A, 1);
            Arrays.fill(B, 1);
            Arrays.fill(S, 3);

            fillMatrix(MA, 1);
            fillMatrix(MB, 1);
            fillMatrix(MG, 2);
            fillMatrix(MK, 2);
            fillMatrix(ML, 2);
            fillMatrix(MO, 3);
            fillMatrix(MP, 3);
            fillMatrix(MR, 3);
        } else if(method == 2){
            Random rand = new Random();

            fillArrayRandom(A, rand);
            fillArrayRandom(B, rand);
            fillArrayRandom(S, rand);

            for (int[][] matrix : matrices) {
                fillMatrix(matrix, rand.nextInt(10) + 1);
            }
        } else {
            throw new IllegalArgumentException();
        }


//        N = n;
//        A = new int[N];
//        B = new int[N];
//        S = new int[N];
//        T = new int[N];
//        MA = new int[N][N];
//        MB = new int[N][N];
//        MG = new int[N][N];
//        MK = new int[N][N];
//        ML = new int[N][N];
//        MO = new int[N][N];
//        MP = new int[N][N];
//        MR = new int[N][N];
//        MF = new int[N][N];
//
//        if(method == 1) {
//            Arrays.fill(A, 1);
//            Arrays.fill(B, 1);
//            Arrays.fill(S, 3);
//            fillMatrix(MA, 1);
//            fillMatrix(MB, 1);
//            fillMatrix(MG, 2);
//            fillMatrix(MK, 2);
//            fillMatrix(ML, 2);
//            fillMatrix(MO, 3);
//            fillMatrix(MP, 3);
//            fillMatrix(MR, 3);
//        } else {
//            Random rand = new Random();
//            Arrays.fill(A, rand.nextInt(10) + 1);
//            Arrays.fill(B, rand.nextInt(10) + 1);
//            Arrays.fill(S, rand.nextInt(10) + 1);
//            fillMatrix(MA, rand.nextInt(10) + 1);
//            fillMatrix(MB, rand.nextInt(10) + 1);
//            fillMatrix(MG, rand.nextInt(10) + 1);
//            fillMatrix(MK, rand.nextInt(10) + 1);
//            fillMatrix(ML, rand.nextInt(10) + 1);
//            fillMatrix(MO, rand.nextInt(10) + 1);
//            fillMatrix(MP, rand.nextInt(10) + 1);
//            fillMatrix(MR, rand.nextInt(10) + 1);
//        }



//        Arrays.fill(A, value);
//        Arrays.fill(B, value);
//        Arrays.fill(S, value);
//        fillMatrix(MA, value);
//        fillMatrix(MB, value);
//        fillMatrix(MG, value);
//        fillMatrix(MK, value);
//        fillMatrix(ML, value);
//        fillMatrix(MO, value);
//        fillMatrix(MP, value);
//        fillMatrix(MR, value);
    }

    private static void fillMatrix(int[][] matrix, int value) {
        for (int i = 0; i < N; i++) {
            Arrays.fill(matrix[i], value);
        }
    }

    private static void fillArrayRandom(int[] array, Random rand) {
        for (int i = 0; i < array.length; i++) {
            array[i] = rand.nextInt(10) + 1;
        }
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

