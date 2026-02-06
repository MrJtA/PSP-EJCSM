import java.io.*;
import java.util.*;

public class Ejercicio2 {

    final static String scriptUpper = "/mnt/c/Users/DAM2/Documents/castroEthan_PSP/castroEthan_PSP/upper_p.sh";
    final static String scriptReverse = "/mnt/c/Users/DAM2/Documents/castroEthan_PSP/castroEthan_PSP/reverse_p.sh";
    final static String ficheroEntrada = "./ficheroEntrada.txt";
    final static String ficheroSalida = "./ficheroSalida.txt";

    public static void main(String[] args) throws Exception {
        toUpperCase(ficheroEntrada, ficheroSalida);
        toUpperCaseAndReverse(ficheroSalida);
    }

    public static void toUpperCase(String entrada, String salida) {
        try {
            List<ProcessBuilder> procesosEjecutables = new ArrayList<>();
            ProcessBuilder pb1 = new ProcessBuilder("wsl", "bash", "-c", "cat", entrada);
            ProcessBuilder pb2 = new ProcessBuilder("wsl", scriptReverse);
            ProcessBuilder pb3 = new ProcessBuilder("wsl", salida);
            procesosEjecutables.add(pb1);
            procesosEjecutables.add(pb2);
            procesosEjecutables.add(pb3);
            List<Process> procesosEjecutados = ProcessBuilder.startPipeline(procesosEjecutables);
            Process p2 = procesosEjecutados.get(procesosEjecutados.size()-1);
            BufferedReader br = new BufferedReader(new InputStreamReader(p2.getInputStream()));
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(">" + linea);
            }
            int code = p2.waitFor();
            if (code == 0) {
                System.out.println("Proceso ejecutado con éxito.");
            } else {
                System.out.println("Proceso fallido.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void toUpperCaseAndReverse(String entrada) {
        try {
            List<ProcessBuilder> procesosEjecutables = new ArrayList<>();
            ProcessBuilder pb1 = new ProcessBuilder("wsl", "bash", "-c", "cat", scriptReverse);
            ProcessBuilder pb2 = new ProcessBuilder("wsl", entrada);
            procesosEjecutables.add(pb1);
            procesosEjecutables.add(pb2);
            List<Process> procesosEjecutados = ProcessBuilder.startPipeline(procesosEjecutables);
            Process p2 = procesosEjecutados.get(procesosEjecutados.size()-1);
            BufferedReader br1 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
            String linea1;
            while ((linea1 = br1.readLine()) != null) {
                System.out.println(linea1);
            }
            int code = p2.waitFor();
            if (code == 0) {
                System.out.println("Proceso ejecutado con éxito.");
            } else {
                System.out.println("Proceso fallido.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}