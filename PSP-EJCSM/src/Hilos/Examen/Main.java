import Ejercicio1.Gymbro;
import Ejercicio1.Maquina;
import Ejercicio2.Alumno;
import Ejercicio2.Profesor;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    final static Scanner sc = new Scanner(System.in);

    public static int pedirNumero(String enunciado) {
        int numero = 0;
        try {
            while (true) { 
            System.out.println(enunciado);
                numero = sc.nextInt();
                if (numero < 0) {
                    System.out.println("Introduce un número positivo.");
                } else {
                    break;
                }
            }
        } catch (InputMismatchException e) {
            System.err.println("Error: Introduce un valor válido.");
        }
        return numero;
    }

    public static void ejercicio1() {
        long tiempoTotal = 0;
        int numeroGymbros = pedirNumero("Introduce el número de gymbros: ");
        int numeroMaquinas = pedirNumero("Introduce el número de máquinas: ");
        Maquina[] maquinas = new Maquina[numeroMaquinas];
        Gymbro[] gymbros = new Gymbro[numeroGymbros];
        for (int i = 0; i<numeroMaquinas; i++) {
            maquinas[i] = new Maquina(i);
        }
        for (int i = 0; i<numeroGymbros; i++) {
            gymbros[i] = new Gymbro(i, maquinas);
            gymbros[i].start();
        }
        for (int i = 0; i<numeroGymbros; i++) {
            try {
                gymbros[i].join();
                tiempoTotal += gymbros[i].getTiempo();
            } catch (InterruptedException e) {
                System.out.println("Error: Gymbro " + gymbros[i].getIDGymbro() + " interrumpido.");
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("El tiempo promedio de la duración de la rutina de cada gmybro ha sido de " + tiempoTotal/numeroGymbros + "ms.");
    }

    public static void ejercicio2() {
        Profesor dani = new Profesor();
        int numeroAlumnos = pedirNumero("Introduce el número de alumnos: ");
        Alumno[] alumnos = new Alumno[numeroAlumnos];
        for (int i = 0; i<numeroAlumnos; i++) {
            alumnos[i] = new Alumno(i, dani);
            alumnos[i].start();
        }
        for (int i = 0; i<numeroAlumnos; i++) {
            try {
                alumnos[i].join();
            } catch (InterruptedException e) {
                System.out.println("Error: Gymbro " + alumnos[i].getIDAlumno() + " interrumpido.");
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Se acabó la clase.");
    }

    public static void main(String[] args) {
        boolean seguir = true;
        System.out.println("Bienvenido a mi exámen de hilos.");
        while (seguir) {
            System.out.println("0. Salir.");
            System.out.println("1. Ejercicio 1.");
            System.out.println("2. Ejercicio 2.");
            int opcion = pedirNumero("Introduce una opción: ");
            switch(opcion) {
                case 0 -> {
                    seguir = false;
                    System.out.println("Muchas gracias por su atención.");
                }
                case 1 -> ejercicio1();
                case 2 -> ejercicio2();
                default -> {
                    System.out.println("Introduce una opción disponible.");
                    seguir = true;
                }
            }
        }

    }
}