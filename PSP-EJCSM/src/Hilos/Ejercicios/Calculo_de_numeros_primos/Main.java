package Calculo_de_numeros_primos;

import java.util.*;

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

    public static int countPrimes(int rangeStart, int rangeEnd, int numThreads) {
        int contador = 0;
        int longitud = rangeEnd-rangeStart;
        int longitudPorcion = longitud/numThreads;
        int resto = rangeEnd%numThreads;
        Thread[] hilos = new Thread[numThreads];
        Calculador[] calculadores = new Calculador[numThreads];
        for (int i = 0; i<numThreads; i++) {
            if (i == numThreads-1) {
                longitudPorcion += resto;
            }
            Calculador c = new Calculador(rangeStart, rangeStart+longitudPorcion);
            Thread h = new Thread(c);
            hilos[i] = h;
            calculadores[i] = c;
            h.start();
            rangeStart += longitudPorcion;
        }
        for (int i = 0; i<numThreads; i++) {
            try {
                hilos[i].join();
                int porcion = i+1;
                int contadorPorcion = calculadores[i].getContador();
                System.out.println("Números primos encontrados en la porción " + porcion + ": " + contadorPorcion + ".");
                contador += contadorPorcion;
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                return -1;
            }
        }
        for (int i = 0; i<numThreads; i++) {
        }
        return contador;
    }
    
    public static void main(String[] args) {
        
        int inicioRango = pedirNumero("Introduce el inicio del rango: ");
        int finalRango = pedirNumero("Introduce el final del rango: ");
        int numeroHilos = pedirNumero("Introduce el número de hilos: ");
        int contador = countPrimes(inicioRango, finalRango, numeroHilos);
        System.out.println("El número total de primos encontrados en el rango especificado es de: " + contador);

    }

}