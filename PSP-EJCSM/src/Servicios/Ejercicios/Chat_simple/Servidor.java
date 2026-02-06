package Servicios.Ejercicios.Chat_simple;

import java.io.*;
import java.net.*;

public class Servidor {

    public static void main(String[] args) {
        System.out.println("Servidor esperando conexiones...");
        try (ServerSocket socketServidor = new ServerSocket(5000)) {
            System.out.println("Cliente conectado al servidor. Iniciando intercambio de mensajes...");
            while (true) {
                Socket socket = socketServidor.accept();
                Handler handler = new Handler(socket);
                handler.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
}