/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package diferenciacionnumerica;

import java.util.Scanner;

public class DiferenciacionNumerica {

    // Función a derivar
    public static double f(double x) {
        return Math.exp(x); // e^x
    }

    // Derivada exacta
    public static double derivadaExacta(double x) {
        return Math.exp(x);
    }

    // Diferencia progresiva
    public static double diferenciaProgresiva(double x, double h) {
        return (f(x + h) - f(x)) / h;
    }

    // Diferencia regresiva
    public static double diferenciaRegresiva(double x, double h) {
        return (f(x) - f(x - h)) / h;
    }

    // Diferencia centrada
    public static double diferenciaCentrada(double x, double h) {
        return (f(x + h) - f(x - h)) / (2 * h);
    }

    // Error absoluto
    public static double errorAbsoluto(double exacto, double aproximado) {
        return Math.abs(exacto - aproximado);
    }

    // Richardson usando diferencia centrada
    public static double richardson(double x, double h) {

        double D0_h = diferenciaCentrada(x, h);
        double D0_2h = diferenciaCentrada(x, 2 * h);

        return (4 * D0_h - D0_2h) / 3.0;
    }

    // Tabla de Richardson
    public static void tablaRichardson(double x, double h, int niveles) {

        double[][] D = new double[niveles][niveles];

        for (int i = 0; i < niveles; i++) {
            double hi = h * Math.pow(2, i);
            D[i][0] = diferenciaCentrada(x, hi);
        }

        for (int k = 1; k < niveles; k++) {

            for (int i = 0; i < niveles - k; i++) {

                D[i][k] =
                        (Math.pow(4, k) * D[i][k - 1]
                        - D[i + 1][k - 1])
                        / (Math.pow(4, k) - 1);

            }
        }

        System.out.println("\nTABLA DE RICHARDSON");
        System.out.println("--------------------------------");

        for (int i = 0; i < niveles; i++) {

            for (int j = 0; j < niveles - i; j++) {

                System.out.printf("%15.10f", D[i][j]);

            }
            System.out.println();
        }
    }

    // Estudio del tamaño de paso
    public static void analizarPaso(double x) {

        double exacta = derivadaExacta(x);

        System.out.println("\nANALISIS DEL TAMANO DE PASO");
        System.out.println("------------------------------------------");
        System.out.printf("%10s %20s %20s%n",
                "h", "Aprox. Centrada", "Error");

        for (int i = 1; i <= 10; i++) {

            double h = Math.pow(10, -i);

            double aprox = diferenciaCentrada(x, h);

            double error = errorAbsoluto(exacta, aprox);

            System.out.printf("%10.1e %20.10f %20.10e%n",
                    h, aprox, error);
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese x: ");
        double x = sc.nextDouble();

        System.out.print("Ingrese h: ");
        double h = sc.nextDouble();

        double exacta = derivadaExacta(x);

        double prog = diferenciaProgresiva(x, h);
        double reg = diferenciaRegresiva(x, h);
        double cent = diferenciaCentrada(x, h);
        double rich = richardson(x, h);

        System.out.println("\nRESULTADOS");
        System.out.println("--------------------------------");

        System.out.printf("Derivada exacta      : %.10f%n", exacta);

        System.out.printf("Progresiva           : %.10f%n", prog);
        System.out.printf("Error               : %.10e%n",
                errorAbsoluto(exacta, prog));

        System.out.printf("\nRegresiva            : %.10f%n", reg);
        System.out.printf("Error               : %.10e%n",
                errorAbsoluto(exacta, reg));

        System.out.printf("\nCentrada             : %.10f%n", cent);
        System.out.printf("Error               : %.10e%n",
                errorAbsoluto(exacta, cent));

        System.out.printf("\nRichardson           : %.10f%n", rich);
        System.out.printf("Error               : %.10e%n",
                errorAbsoluto(exacta, rich));

        analizarPaso(x);

        tablaRichardson(x, h, 4);

        sc.close();
    }
}
