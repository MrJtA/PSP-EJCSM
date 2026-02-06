import java.net.*;

public class Server {

    static int numero;
    static boolean ganador = false;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Servidor: "+ 12345);
            generarNumero();
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado: " + socket.getInetAddress());
                Handler handler = new Handler(socket, numero);
                handler.start();
            }
        } catch (Exception e) {
            System.out.println("Error: No se ha podido establecer la conexión entre el cliente y el servidor.");
        }

    }

    public static void generarNumero() {
        numero = (int)(Math.random()*100) + 1;
    }

    public synchronized static void nombrarGanador() {
        ganador = true;
    }

    public synchronized static boolean getGanador(){
        return ganador;
    }

}