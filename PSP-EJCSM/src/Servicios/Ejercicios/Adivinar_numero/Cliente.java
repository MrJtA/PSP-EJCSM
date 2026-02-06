import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 12345);
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner teclado = new Scanner(System.in)) {
            System.out.println("Conectado al servidor...");
            String mensajeServidor;
            String mensajeCliente;
            while (true) {
                System.out.print("Introduce un número: ");
                mensajeCliente = teclado.nextLine();
                salida.println(mensajeCliente);
                mensajeServidor = entrada.readLine(); 
                if (mensajeServidor == null) {
                    break;
                } 
                System.out.println("Servidor: " + mensajeServidor);
                if (mensajeServidor.contains("¡Enhorabuena! Has acertado el número.") || mensajeServidor.contains("Fin del juego.")) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: No se ha podido establecer la conexión entre el cliente y el servidor.");
        }

    }
}
