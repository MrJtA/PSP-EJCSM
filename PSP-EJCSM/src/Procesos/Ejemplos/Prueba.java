import java.io.*;
import java.util.*;

public class Prueba {

    public static void main(String[] args) {
        
        try {
            ProcessBuilder pb1 = new ProcessBuilder("cmd", "/c", "dir | findstr \"system\"");
            pb1.inheritIO();
            Process p1 = pb1.start();
            int code1 = p1.waitFor();
            if (code1 == 0) {
            System.out.println("Proceso ejecutado con éxito.");
            } else {
                System.out.println("Fallo en el sistema.");
            }
            /*
            ProcessBuilder pb2 = new ProcessBuilder("wsl", "bash", "-c", "ls");
            pb2.inheritIO();
            Process p2 = pb2.start();
            int code2 = p2.waitFor();
            if (code2 == 0) {
                System.out.println("Proceso ejecutado con éxito.");
                } else {
                    System.out.println("Fallo en el sistema.");
                    }
                    */
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }

        
    }
    
    public static void pipes(List<ProcessBuilder> procesosEjecutables) throws IOException, InterruptedException {
        List<Process> procesosEjecutados = ProcessBuilder.startPipeline(procesosEjecutables);
        Process pUltimo = procesosEjecutados.get(procesosEjecutados.size()-1);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(pUltimo.getInputStream()))){
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(">" + linea);
            }
        }
        pUltimo.waitFor();
    }

}