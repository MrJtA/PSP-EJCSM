import java.io.*;

public class ProcesoGuardian {

    public static void main(String[] args) {

        String url = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
        System.out.println("Se ejecutará el navegador si no está ejecutado.");
        
        try {
            while (true) { 
                ProcessBuilder pbBuscar = new ProcessBuilder("cmd", "/c", "tasklist | find \"chrome.exe\"");
                Process pBuscar = pbBuscar.start();
                boolean encontrado = false;
                try (BufferedReader br = new BufferedReader(new InputStreamReader(pBuscar.getInputStream()))) {
                    pBuscar.waitFor();
                    if (br.readLine() != null) {
                        encontrado = true;
                        continue;
                    } 
                    if (!encontrado) {
                        ProcessBuilder pbEjecutar = new ProcessBuilder("cmd", "/c", "start chrome " + url);
                        Process pEjecutar = pbEjecutar.start();
                        int code1 = pEjecutar.waitFor();
                        if (code1 == 0) {
                            System.out.println("Navegador ejectuado con éxito.");
                        } else {
                            System.out.println("Fallo en la ejecución del navegador.");
                        }
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }

}