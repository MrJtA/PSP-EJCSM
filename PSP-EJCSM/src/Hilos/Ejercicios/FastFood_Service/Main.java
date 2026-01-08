package FastFood_Service;

import java.util.ArrayList;
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

    public static void empezar(int numeroCamareros, int numeroCocineros, int numeroFoodRunners) {
        Pedidos pedidosNuevos = new Pedidos();
        Pedidos pedidosPreaparados = new Pedidos();
        ArrayList<Camarero> camareros = new ArrayList<>();
        ArrayList<Cocinero> cocineros = new ArrayList<>();
        ArrayList<FoodRunner> foodRunners = new ArrayList<>();
        System.out.println("Capacidad de procesamiento de pedidos: " + pedidosNuevos.getCapacidad());
        System.out.println("Pulsa ENTER para finalizar la jornada.");
        for (int i = 0; i<numeroCamareros; i++) {
            Camarero camarero = new Camarero(pedidosNuevos);
            camareros.add(camarero);
            camarero.start();
        }
        for (int i = 0; i<numeroCocineros; i++) {
            Cocinero cocinero = new Cocinero(pedidosNuevos, pedidosPreaparados);
            cocineros.add(cocinero);
            cocinero.start();
        }
        for (int i = 0; i<numeroFoodRunners; i++) {
            FoodRunner foodRunner = new FoodRunner(pedidosPreaparados);
            foodRunners.add(foodRunner);
            foodRunner.start();
        }
        sc.nextLine();
        System.out.println("Finalizando la jornada...");
        for (Camarero camarero : camareros) {
            camarero.pararHilo();
        }
        for (Cocinero cocinero : cocineros) {
            cocinero.pararHilo();
        }
        for (FoodRunner foodRunner : foodRunners) {
            foodRunner.pararHilo();
        }
        
    }
    
    public static void main(String[] args) {
        
        System.out.println("Bienvenido al restaurante PK2 de Orgullo.");
        int numeroCamareros = pedirNumero("Introduce el número de camareros: ");
        int numeroCocineros = pedirNumero("Introduce el número de cocineros: ");
        int numeroFoodRunners = pedirNumero("Introduce el número de food runners: ");
        empezar(numeroCamareros, numeroCocineros, numeroFoodRunners);

    }


}