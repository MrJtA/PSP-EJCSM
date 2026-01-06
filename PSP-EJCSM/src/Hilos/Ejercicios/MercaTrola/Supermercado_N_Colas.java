package MercaTrola;

import java.util.ArrayList;
import java.util.Scanner;

// Clase "Supermercado_N_Colas" en donde se ejecutará el código con el modelo de supermercado de una cola por cada grupo de cliente y caja.

public class Supermercado_N_Colas {
    
    final static Scanner sc = new Scanner(System.in);

    // Método para pedir por teclado un número mayor que 0, que se usará para indicar cuántos clientes y cajas participarán en el supermercado.
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

    // Método que inicia el código principal y representa la actividad del supermercado.
    public static void apertura(int numeroCajas, int numeroClientes) {
        System.out.println("MercaTrola abre sus puertas.");
        // En caso de que se indiquen más cajas que clientes y por ende sobren cajas y queden sin actividad, se igualará dicho número al de clientes indicado y se eliminarán las sobrantes para evitarlo.
        if (numeroCajas>numeroClientes) {
            int cajasSobrantes = numeroCajas-numeroClientes;
            numeroCajas = numeroClientes;
            System.out.println("Hay " + cajasSobrantes + " cajas sobrantes que no se utilizarán.");
        }
        long tiempoTotal = 0; // Tiempo total que consumen todas las cajas en milisegundos.
        int clientesPorCaja = numeroClientes/numeroCajas; // Número de cientes que corresponden a cada caja.
        int resto = numeroClientes%numeroCajas; // En caso de una repartición inexacta entre clientes y cajas, se calcula el resto de clientes para que luego los acoja la última caja.
        ArrayList<Cola> listaColas = new ArrayList<>(); // Lista de colas.
        ArrayList<Caja> listaCajas = new ArrayList<>(); // Lista de cajas.
        ArrayList<Cliente> listaClientes = new ArrayList<>(); // Lista de clientes.
        int numeroCliente = 1; // ID del cliente que se asignará y autoincrementará para cada cliente.
        // Bucle para la creación y ejecución de las cajas y sus respectivas colas de igual número.
        for (int i = 0; i<numeroCajas; i++) {
            Cola cola = new Cola(false);
            listaColas.add(cola);
            Caja caja = new Caja(i, cola);
            listaCajas.add(caja);
            caja.start();
            if (i == numeroCajas-1) {
                clientesPorCaja += resto;
            }
            // Bucle para la asignación y ejecución de los clientes a sus respectivas colas por orden.
            for (int j = 0; j<clientesPorCaja; j++) {
                Cliente cliente = new Cliente(numeroCliente++, cola);
                listaClientes.add(cliente);
                System.out.println("Cliente " + cliente.getNumeroCliente() + " asignado a caja " + caja.getNumeroCaja());
                cliente.start();
            }
        }
        // Serie de bucles para terminar de ejecutar los clientes y las cajas, insertar en las colas el cliente final para finalizar la operación de la estructura concurrente, y recopilar los tiempos totales de cada caja.
        try {
            for (Cliente cliente : listaClientes) {
                cliente.join();
            }
            for (Cola cola : listaColas) {
                cola.acabar();
            }
            for (Caja caja : listaCajas) {
                caja.join();
                tiempoTotal += caja.getTiempoEsperaTotal();
            }
        } catch (InterruptedException e) {
            System.err.println("Error: Ha ocurrido una interrupción.");
            Thread.currentThread().interrupt();
        }
        System.out.println("MercaTrola cierra sus puertas.");
        long tiempoEsperaMedio = tiempoTotal/numeroClientes; // Tiempo de espera medio de todas las cajas en milisegundos.
        System.out.println("Tiempo medio de espera: " + tiempoEsperaMedio + "ms.");
    }

    // Método main.
    public static void main(String[] args) {
        
        System.out.println("Bienvenido al supermercado MercaTrola.");
        int numeroCajas = pedirNumero("Introduce el número de cajas: ");
        int numeroClientes = pedirNumero("Introduce el número de clientes: ");
        apertura(numeroCajas, numeroClientes);

    }

}