package Servicios.Ejemplos;

import java.io.*;
import java.net.*;

public class Servidor {

    public static void main(String[] args) {
        try (ServerSocket socketServidor = new ServerSocket(5000)) {
            System.out.println("Servidor esperando conexiones...");
            try (Socket socketCliente = socketServidor.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
                PrintWriter out = new PrintWriter(socketCliente.getOutputStream(), true);
                BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
                System.out.println("Cliente conectado al servidor. Iniciando intercambio de mensajes...");
                String mensajeCliente, mensajeServidor;
                while (true) {
                    System.out.print("\t\t\t\tCliente: ");
                    mensajeCliente = in.readLine();
                    if (mensajeCliente == null || mensajeCliente.equalsIgnoreCase("exit")) {
                        System.out.println("El cliente ha terminado la conexión.");
                        break;
                    }
                    System.out.println(mensajeCliente);
                    System.out.print("Servidor: ");
                    mensajeServidor = console.readLine();
                    out.println(mensajeServidor);
                    if (mensajeServidor.equalsIgnoreCase("exit")) {
                        System.out.println("El servidor ha terminado la conexión.");
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
}