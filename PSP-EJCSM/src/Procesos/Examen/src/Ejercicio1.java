import java.io.*;
import java.util.*;

public class Ejercicio1 {

    
    final static String ficheroSalida = "./fichero.txt";
    final static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        System.out.println("0. Salir.");
        System.out.println("1. Guadar procesos activos.");
        System.out.println("2. Contar procesos activos.");
        System.out.println("Introduce una opción: ");
        int opcion = Integer.parseInt(sc.nextLine());
        boolean seguir = true;
        while (seguir) {
            switch(opcion) {
                case 0:
                    seguir = false;
                case 1:
                    guardarProcesosActivos(ficheroSalida);
                case 2:
                    System.out.println("Hay un total de " + contarProcesosActivos() + " procesos activos.");
                default:
                    System.out.println("Introduce una opción válida.");
                    seguir = true;
            }
        }

    }

    public static void guardarProcesosActivos(String salida) {
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "tasklist");
            pb.redirectOutput(new File(salida));
            Process p = pb.start();
            int code = p.waitFor();
            if (code == 0) {
                System.out.println("Proceso 1 ejecutado con éxito.");
            } else {
                System.out.println("Proceso fallido.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int contarProcesosActivos() {
        int procesosActivos = 0;
        try {
            List<ProcessBuilder> procesosEjecutables = new ArrayList<>();
            ProcessBuilder pb1 = new ProcessBuilder("cmd", "/c", "tasklist");
            ProcessBuilder pb2 = new ProcessBuilder("cmd", "/c", "find /c /v \"\"");
            procesosEjecutables.add(pb1);
            procesosEjecutables.add(pb2);
            List<Process> procesosEjecutados = ProcessBuilder.startPipeline(procesosEjecutables);
            Process p2 = procesosEjecutados.get(procesosEjecutados.size()-1);
            BufferedReader br = new BufferedReader(new InputStreamReader(p2.getInputStream()));
            String linea;
            while ((linea = br.readLine()) != null) {
                procesosActivos = Integer.parseInt(linea);
            }
            int code = p2.waitFor();
            if (code == 0) {
                System.out.println("Proceso 2 ejecutado con éxito.");
            } else {
                System.out.println("Proceso fallido.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return procesosActivos;
    }

}
