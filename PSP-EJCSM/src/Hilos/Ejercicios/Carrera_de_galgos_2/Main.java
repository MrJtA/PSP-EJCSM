package Carrera_de_galgos_2;

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

    public static void startRace(int numDogs, int distance) {
        Carrera carrera = new Carrera();
        Thread[] hilos = new Thread[numDogs];
        Galgo[] galgos = new Galgo[numDogs];
        for (int i = 0; i<numDogs; i++) {
            int numeroGalgo = i+1;
            Galgo g = new Galgo(numeroGalgo, distance, carrera);
            Thread h = new Thread(g);
            galgos[i] = g;
            hilos[i] = h;
            h.start();
        }
    }

    public static void main(String[] args) {
        
        int numeroGalgos = pedirNumero("Introduce el número de galgos: ");
        int distancia = pedirNumero("Introduce la distancia en metros de la carrera: ");
        startRace(numeroGalgos, distancia);

    }

}