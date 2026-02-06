package Servicios.Ejercicios.Chat_simple;

import java.io.*;
import java.util.*;
import java.net.*;

public class Handler extends Thread {

    private Socket socket;
    
    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             Scanner teclado = new Scanner(System.in)) {
            String mensajeCliente, mensajeServidor;
            while ((mensajeCliente = entrada.readLine()) != null) {
                System.out.println("[" + socket.getInetAddress() + "]: " + mensajeCliente);
                mensajeServidor = teclado.nextLine();
                salida.println(mensajeServidor);
                if (mensajeCliente.equalsIgnoreCase("exit")) {
                    System.out.println("Cerrando conexión...");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                socket.close();
                System.out.println("Socket cerrado para un cliente.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}