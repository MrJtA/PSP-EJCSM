package Calculo_del_maximo_y_minimo;

import java.util.Scanner;

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
    
    public static int calculateMax(int[] array, int numThreads) {
        int longitudPorcion = array.length/numThreads;
        int resto = (int) array.length%numThreads;
        int maximoTotal = Integer.MIN_VALUE;
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
        for (int i = 0; i<numThreads; i++) {
            try {
                hilos[i].join();
                int porcion = i+1;
                int maximoParcial = calculadores[i].getMaximo();
                System.out.println("Máximo de la porción " + porcion + ": " + maximoParcial);
                if (maximoParcial>maximoTotal) {
                    maximoTotal = maximoParcial;
                }
            } catch (InterruptedException e) {
                System.err.println("Error: Hilo interrumpido.");
                Thread.currentThread().interrupt();
                return -1;
            }
        }
        return maximoTotal;
    }

    public static int calculateMin(int[] array, int numThreads) {
        int longitudPorcion = array.length/numThreads;
        int resto = (int) array.length%numThreads;
        int minimoTotal = Integer.MAX_VALUE;
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
        for (int i = 0; i<numThreads; i++) {
            try {
                hilos[i].join();
                int porcion = i+1;
                int minimoParcial = calculadores[i].getMinimo();
                System.out.println("Mínimo de la porción " + porcion + ": " + minimoParcial);
                if (minimoParcial<minimoTotal) {
                    minimoTotal = minimoParcial;
                }
            } catch (InterruptedException e) {
                System.err.println("Error: Hilo interrumpido.");
                Thread.currentThread().interrupt();
                return -1;
            }
        }
        for (int i = 0; i<numThreads; i++) {
        }
        return minimoTotal;
    }

    public static void main(String[] args) {
        
        int[] array = crearArray();
        int numeroHilos = pedirNumero("Introduce el número de hilos: ");
        int maximo = calculateMax(array, numeroHilos);
        int minimo = calculateMin(array, numeroHilos);
        System.out.println("El número máximo encontrado es: " + maximo + ", y el número mínimo encontrado es: " + minimo + "."); 

    }

}