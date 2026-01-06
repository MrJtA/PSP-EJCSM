import java.io.*;
import java.util.*;

public class Contador_de_apariciones {

    final static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        String rutaRelativa = "PSP-EJCSM/src/Procesos/Ejercicios/Contador_de_apariciones";
        File rutaBase = new File(System.getProperty("user.dir"), rutaRelativa);
        String script = "./contar_palabras.sh";
        List<String> listaFicheros = new ArrayList<>();
        listaFicheros.add("fichero1.txt");
        listaFicheros.add("fichero2.txt");
        listaFicheros.add("fichero3.txt");
        String ficheroResultado = "resultados.txt";
        System.out.print("Introduce la palabra a buscar: ");
        String palabra = sc.nextLine();
        int suma = 0, i = 0;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(rutaBase, ficheroResultado)))) {
            for (String fichero : listaFicheros) {
                ProcessBuilder pb = new ProcessBuilder("wsl", script, palabra, fichero);
                pb.directory(rutaBase);
                Process p = pb.start();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                    String lineaResultado = br.readLine();
                    int cantidad = Integer.parseInt(lineaResultado);
                    suma += cantidad;
                    i++;
                    bw.write("Fichero " + i + ": " + lineaResultado);
                    bw.newLine();
                }
                int code = p.waitFor();
                if (code == 0) {
                    System.out.println("El conteo de palabras del fichero " + i + " se ha realizado con éxito.");
                } else {
                    System.out.println("Fallo en la ejecución del script.");
                }
            }
            System.out.println("El número de apariciones que se han encontrado de la palabra '" + palabra + "' ha sido de " + suma);
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }
    
}