package Calculo_de_media;

import java.util.*;

public class Main {

    final static Scanner sc = new Scanner(System.in);

    public static int[] crearArray() {
        int longitud = pedirNumero("Introduce la longitud del array: ");
        int[] array = new int[longitud];
        System.out.println("El array es: ");
        System.out.print("[ ");
        for (int i = 0; i<array.length; i++) {
            array[i] = (int)(Math.random()*100);
            System.out.print(array[i] + " ");
        }
        System.out.println("]");
        return array;
    }

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

    public static double calculateMean(int[] array, int numThreads) {
        int longitudPorcion = array.length/numThreads;
        int resto = (int) array.length%numThreads;
        double suma = 0;
        int inicio = 0;
        Thread[] hilos = new Thread[numThreads];
        Calculador[] calculadores = new Calculador[numThreads];
        for (int i = 0; i<numThreads; i++) {
            if (i == numThreads-1) {
                longitudPorcion += resto;
            }
            int[] arrayPorcion = new int[longitudPorcion];
            int k = 0;
            for (int j = inicio; j<inicio+longitudPorcion; j++) {
                arrayPorcion[k] = array[j];
                k++;
            }
            Calculador c = new Calculador(arrayPorcion);
            Thread h = new Thread(c);
            hilos[i] = h;
            calculadores[i] = c;
            h.start();
            inicio += longitudPorcion;
        }
        for (int i = 0; i < numThreads; i++) {
            try {
                hilos[i].join();
                int porcion = i+1;
                int sumaParcial = calculadores[i].getSumaParcial();
                System.out.println("Suma de la porción " + porcion + ": " + sumaParcial);
                suma += sumaParcial;
            } catch (InterruptedException e) {
                System.err.println("Error: Hilo interrumpido.");
                Thread.currentThread().interrupt();
                return -1;
            }
        }
        suma = suma/array.length;
        return suma;
    }
    
    public static void main(String[] args) {
        
        int[] array = crearArray();
        int numeroHilos = pedirNumero("Introduce el número de hilos: ");
        double media = calculateMean(array, numeroHilos);
        System.out.println("La media de la suma de las porciones es: " + media);

    }

}