package Generacion_de_numeros_aleatorios;

import java.util.Scanner;

public class Main {
    
    final static Scanner sc = new Scanner(System.in);

    public static int pedirNumero(String enunciado) {
        int numero;
        while (true) {
            System.out.print(enunciado);
            String entrada = sc.nextLine();
            try {
                numero = Integer.parseInt(entrada);
                if (numero <= 0) {
                    System.out.println("Error: Introduce un número mayor que 0.");
                } else {
                    return numero;
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: Introduce un valor válido.");
            }
        }
    }

    public static int[] generateRandomNumbers(int numNumbers, int numThreads) {
        int[] array = new int[numNumbers];
        int longitudPorcion = numNumbers/numThreads;
        int resto = numNumbers%numThreads;
        int indice = 0;
        Thread[] hilos = new Thread[numThreads];
        Generador[] generadores = new Generador[numThreads];
        for (int i = 0; i<numThreads; i++) {
            if (i == numThreads-1) {
                longitudPorcion += resto;
            }
            Generador g = new Generador(longitudPorcion);
            Thread h = new Thread(g);
            hilos[i] = h;
            generadores[i] = g;
            h.start();
        }
        for (int i = 0; i<numThreads; i++) {
            try {
                hilos[i].join();
                int porcion = i+1;
                int[] arrayPorcion = generadores[i].getArray();
                System.out.println("Números generados por el " + porcion + "º hilo: ");
                System.out.print("[ ");
                for (int j = 0; j<arrayPorcion.length; j++) {
                    array[indice] = arrayPorcion[j];
                    System.out.print(arrayPorcion[j] + " ");
                    indice++;
                }
                System.out.println("]");
            } catch (InterruptedException e) {
                System.err.println("Error: Hilo interrumpido.");
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return array;
    }

    public static void main(String[] args) {
        
        int numeroNumeros = pedirNumero("Introduce el número de números que quieres generar: ");
        int numeroHilos = pedirNumero("Introduce el número de hilos: ");
        int[] array = generateRandomNumbers(numeroNumeros, numeroHilos);
        System.out.println("Los números generados han sido: ");
        System.out.print("[ ");
        for (int i = 0; i<array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println("]");

    }

}