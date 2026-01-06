package Productor_Consumidor_de_Factoriales;

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
    
    public static void realizacion(int numeroProductores, int numeroConsumidores) {
        Cola cola = new Cola();
        ArrayList<Productor> productores = new ArrayList<>();
        ArrayList<Consumidor> consumidores = new ArrayList<>();
        System.out.println("Se trabajará con una cola compartida de tamaño " + cola.getTamaño() + ".");
        System.out.println("Pulsa ENTER para finalizar la producción.");
        for (int i = 0; i<numeroProductores; i++) {
            Productor productor = new Productor(cola);
            productores.add(productor);
            productor.start();
        }
        for (int i = 0; i<numeroConsumidores; i++) {
            Consumidor consumidor = new Consumidor(cola);
            consumidores.add(consumidor);
            consumidor.start();
        }
        sc.nextLine();
        System.out.println("Finalizando la producción...");
        for (Productor productor : productores) {
            productor.pararProduccion();
        }
        for (Consumidor consumidor : consumidores) {
            consumidor.pararCalculos();
        }
    }
    
    public static void main(String[] args) {
        
        int numeroProductores = pedirNumero("Introduce el número de productores: ");
        int numeroConsumidores = pedirNumero("Introduce el número de consumidores: ");
        realizacion(numeroProductores, numeroConsumidores);

    }

}