import java.io.*;
import java.util.*;

public class Menu_Interactivo {
    
    final static Scanner sc = new Scanner(System.in);
    final static List<Process> procesosBackground = new ArrayList<>();

    public static void main(String[] args) {
        
        System.out.println("Bienvenido al menú.");
        boolean seguir = true;
        while (seguir) {
            limpiarProcesos();
            System.out.println("1. Lanzar un sleep en un proceso en primer plano.");
            System.out.println("2. Lanzar un sleep en un proceso en segundo plano.");
            System.out.println("3. Mostrar la lista de los PIDs de los procesos en segundo plano que siguen en ejecución.");
            System.out.println("4. Salir (hay que esperar hasta que todos los procesos finalizen de ejecutarse).");
            System.out.print("Introduce una opción: ");
            String opcion = sc.nextLine();
            try {
                switch (opcion) {
                    case "1" -> foreground();
                    case "2" -> background();
                    case "3" -> mostrarPIDs();
                    case "4" -> {
                        salir();
                        seguir = false;
                    }
                    default -> {
                        System.out.println("Introduce una opción válida.");
                        seguir = true;
                    }
                }
            } catch (IOException | InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public static int pedirSegundos() {
        int segundos = -1;
        boolean seguir = true;
        while (seguir) {
            try {
                System.out.print("Introduce los segundos: ");
                segundos = Integer.parseInt(sc.nextLine());
                seguir = false;
            } catch (NumberFormatException e) {
                sc.nextLine();
                System.err.println("Introduce un intervalo de segundos válido por favor.");
            }
        }
        return segundos;
    }

    public static void foreground() throws IOException, InterruptedException {
        int segs = pedirSegundos();
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "timeout -t " + segs);
        pb.inheritIO();
        Process p = pb.start();
        System.out.println("El programa se parará hasta que se termine el proceso.");
        int code = p.waitFor();
        if (code == 0) {
            System.out.println("Proceso ejecutado y terminado con éxito.");
        } else {
            System.out.println("Fallo en la ejecución del proceso.");
        }
    }

    public static void background() throws IOException, InterruptedException {
        int segs = pedirSegundos();
        ProcessBuilder pb = new ProcessBuilder("cmd", "/k", "timeout -t " + segs);
        System.out.println("Se estará ejectuando el proceso en segundo plano.");
        Process p = pb.start();
        procesosBackground.add(p);
    }

    public static void limpiarProcesos() {
        procesosBackground.removeIf(p -> !p.isAlive());
    }

    public static void mostrarPIDs() {
        limpiarProcesos();
        if (procesosBackground.isEmpty()) {
            System.out.println("Ningún proceso está ejecutándose en estos momentos.");
        } else {
            System.out.println("A continuación se muestran todos los procesos activos ejecutándose en segundo plano:");
            for (Process p : procesosBackground) {
                System.out.println("[" + p.pid() + "]");
            }
        }
    }

    public static void salir() throws IOException, InterruptedException {
        limpiarProcesos();
        for (Process p : procesosBackground) {
            p.waitFor();
        }
        System.out.println("Se han terminado de ejecutar todos los procesos. Hasta la próxima.");
    }

}