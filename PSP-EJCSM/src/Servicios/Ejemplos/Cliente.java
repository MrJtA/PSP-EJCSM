package Servicios.Ejemplos;

import java.io.*;
import java.net.*;

public class Cliente {
    
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Conectado al servidor. Iniciando intercambio de mensajes...");
            String mensajeCliente, mensajeServidor;
            while (true) {
                System.out.print("Cliente: ");
                mensajeCliente = console.readLine();
                out.println(mensajeCliente);
                if (mensajeCliente.equalsIgnoreCase("exit")) {
                    System.out.println("Cliente ha terminado la conexión.");
                    break;
                }
                System.out.print("\t\t\t\tServidor: ");
                mensajeServidor = in.readLine();
                if (mensajeServidor == null || mensajeServidor.equalsIgnoreCase("exit")) {
                    System.out.println("El servidor ha terminado la conexión.");
                    break;
                }
                System.out.println(mensajeServidor);
            }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

}