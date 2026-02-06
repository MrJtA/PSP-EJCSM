package Servicios.Ejercicios.Chat_simple;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000);
        PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
        Scanner teclado = new Scanner(System.in)) {
            System.out.println("Conectado al servidor. Iniciando intercambio de mensajes...");
            Thread hilo = new Thread(() -> {
                try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String mensajeServidor;
                    while ((mensajeServidor = entrada.readLine()) != null) {
                        System.out.println("\n\t\t\tServidor: " + mensajeServidor);
                        System.out.print("--> ");
                    }
                } catch (IOException e) {
                    System.out.println("\nConexión cerrada con el servidor.");
                }
            });
            hilo.start();
            String mensajeCliente;
            while (true) {
                System.out.print("--> ");
                mensajeCliente = teclado.nextLine();
                salida.println(mensajeCliente);
                if (mensajeCliente.equalsIgnoreCase("exit")) {
                    System.out.println("Saliendo del chat...");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}