package org.hpss.lab1;

// Потік T1: c = MAX(MA*MB)*(A*B)

import java.util.Scanner;

class T1 extends Thread {
    public void run() {
        System.out.println("T1 has started: ");

        // 	Введення даних
        int[][] MA = new int[Data.N][Data.N];
        int[][] MB = new int[Data.N][Data.N];
        int[] A = new int[Data.N];
        int[] B = new int[Data.N];
        // num = 1 is a default value for T1
        int c, num = 1;

        if (Data.N <= 3) {
            System.out.println("T1 value: ");
            Scanner scanner = new Scanner(System.in);
            num = scanner.nextInt();
        }

        Data.fillT1(num, MA, MB, A, B);

        // 	Обчислення F1
        c = Data.maxMatrix(Data.multiplyMatrices(MA, MB)) * Data.dotProduct(A, B);

        // 	Виведення результату
        if(Data.N < 10) {
            System.out.println("T1: c = " + c);
        }
        System.out.println("T1 has ended ");
    }
}
