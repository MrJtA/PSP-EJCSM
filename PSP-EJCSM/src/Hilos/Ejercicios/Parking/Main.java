package Parking;

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

    public static void crearCoches(int numeroPlazas) {
        Parking parking = new Parking(numeroPlazas);
        while (true) {
            System.out.print("Pulsa ENTER para crear un coche, o introduce exit para salir.");
            System.out.println("Introduce 'coches' para ver los coches actualmente estacionados.");
            System.out.println("Introduce 'exit' para salir.");
            String accion = sc.nextLine();
            if (accion.isEmpty()) {
                System.out.print("Introduce la matrícula: ");
                String matricula = sc.nextLine();
                Coche coche = new Coche(parking, matricula);
                coche.start();
            } else if (accion.equals("exit")) {
                break;
            } else if (accion.equals("coches")) {
                parking.getCoches();
            }
        }
    }
    
    public static void main(String[] args) {
        
        int numeroPlazas = pedirNumero("Introduce la capacidad de plazas disponibles: ");
        crearCoches(numeroPlazas);

    }

}