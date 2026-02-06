import java.io.*;
import java.net.Socket;

public class Handler extends Thread {
    private final Socket socket;
    private int numeroAleatorio;

    public Handler(Socket socket, int n) {
        this.socket = socket;
        this.numeroAleatorio = n;
    }

    @Override
    public void run() {
        try (PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
            String mensaje;
            while (!Server.getGanador() && (mensaje = entrada.readLine()) != null) {
                try {
                    int numero = Integer.parseInt(mensaje);
                    if (numero == numeroAleatorio) {
                        salida.println("¡Enhorabuena! Has acertado el número.");
                        Server.nombrarGanador();
                        break; 
                    } else {
                        if (Server.getGanador()){
                            break;
                        }
                        salida.println("Inténtalo de nuevo.");
                    }
                } catch (NumberFormatException e) {
                    salida.println("Error: Introduce un valor válido.");
                }
            }
            salida.println("Fin del juego.");
        } catch (IOException e) {
            System.out.println("Error: No se ha podido establecer la conexión...");
        }
    }
}