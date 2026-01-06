import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Suma_de_Cuadrados {
    
    final static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        
        String rutaRelativa = "PSP-EJCSM/src/Procesos/Ejercicios/Suma_de_Cuadrados";
        File rutaBase = new File(System.getProperty("user.dir"), rutaRelativa);
        String scriptCuadrados = "./cuadrados.sh";
        String scriptSuma = "./suma.sh";
        List<Integer> listaNumeros = new ArrayList<>();
        System.out.println("Introduce los números para calcular sus cuadrados. En caso de querer parar, introduce un carácter no numérico o un número no entero.");
        while (true) {
            try {
                System.out.print("Introduce un número entero: ");
                int numero = Integer.parseInt(sc.nextLine());
                listaNumeros.add(numero);
            } catch (NumberFormatException e) {
                break;
            }
        }
        if (listaNumeros.isEmpty()) {
            System.out.println("La lista de números está vacía, por lo que no se pueden ejecutar los programas.");
            return;
        }
        List<String> argumentos1 = new ArrayList<>();
        argumentos1.add("wsl");
        argumentos1.add(scriptCuadrados);
        argumentos1.addAll(listaNumeros.stream().map(String::valueOf).collect(Collectors.toList()));
        try {
            ProcessBuilder pbCuadrados = new ProcessBuilder(argumentos1);
            pbCuadrados.directory(rutaBase);
            Process pCuadrados = pbCuadrados.start();
            int code1 = pCuadrados.waitFor();
            if (code1 != 0) System.out.println("Fallo en el cálculo de los cuadrados.");
            try (BufferedReader br1 = new BufferedReader(new InputStreamReader(pCuadrados.getInputStream()))) {
                List<String> argumentos2 = new ArrayList<>();
                argumentos2.add("wsl");
                argumentos2.add(scriptSuma);
                String linea;
                System.out.println("Los cuadrados resultantes son: ");
                while ((linea = br1.readLine()) != null) {
                    System.out.print(linea + " ");
                    argumentos2.add(linea);
                }
                ProcessBuilder pbSuma = new ProcessBuilder(argumentos2);
                pbSuma.directory(rutaBase);
                Process pSuma = pbSuma.start();
                int code2 = pSuma.waitFor();
                if (code2 != 0) System.out.println("Fallo en la suma de cuadrados.");
                try (BufferedReader br2 = new BufferedReader(new InputStreamReader(pSuma.getInputStream()))) {
                    System.out.println("\nLa suma de los cuadrados es de: ");
                    System.out.println(br2.readLine());
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }

    }

}