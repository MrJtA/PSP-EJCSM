package Urgencias;

import java.util.*;

public class Main {

    final static Scanner sc = new Scanner(System.in);
    final static ArrayList<Paciente> listaPacientes = new ArrayList<>();
    final static Medico medico = new Medico();
    
    public static int pedirNumero(String enunciado) {
        int numero;
        while (true) {
            System.out.print(enunciado);
            String entrada = sc.nextLine();
            try {
                numero = Integer.parseInt(entrada);
                if (numero <= 0) {
                    System.out.println("Error: Introduce un número mayor que 0.");
                } else {
                    return numero;
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: Introduce un valor válido.");
            }
        }
    }

    public static void crearPaciente() {
        System.out.print("Introduce el nombre del paciente: ");
        String nombre = sc.nextLine();
        int prioridad = 0;
        selectionLoop: 
        while (true) {
            System.out.println("1. LEVE.");
            System.out.println("2. MODERADO.");
            System.out.println("3. GRAVE.");
            int opcion = pedirNumero("Seleccione el nivel de urgencia del paciente: ");
            switch (opcion) {
                case 1 -> {
                    prioridad = 1;
                    break selectionLoop;
                }
                case 2 -> {
                    prioridad = 5;
                    break selectionLoop;
                }
                case 3 -> {
                    prioridad = 10;
                    break selectionLoop;
                }
                default -> {
                    System.out.println("Seleccione una opción disponible.");
                }
            }
        }
        Paciente paciente = new Paciente(nombre);
        long tiempoActual = System.currentTimeMillis();
        paciente.setTiempo(tiempoActual);
        listaPacientes.add(paciente);
        paciente.setPriority(prioridad);
        paciente.start();
    }

    public static void finalizarConsultas() {
        System.out.println("Esperando a que termine el paciente en consulta... Los que siguen esperando excepto los graves, que vuelvan al siguiente día.");
        Paciente pacienteEnConsulta = medico.getPacienteActual();
        try {
            pacienteEnConsulta.join(); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        List<Paciente> pacientesPendientes = new ArrayList<>();
        for (Paciente paciente : listaPacientes) {
            if (paciente.isAlive()) {
                if (paciente.getPriority()<10) {
                    paciente.interrupt();
                }
                pacientesPendientes.add(paciente);
            }
        }
        for (Paciente paciente : pacientesPendientes) {
            if (paciente.isAlive()) {
                try {
                    paciente.join(); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
        listaPacientes.clear();
        System.out.println("Cerrado. Vuelva pronto.");
    }

    public static void main(String[] args) {
        
        System.out.println("Bienvenido a la consulta de urgencias del Dr. Castro.");
        boolean salir = false;
        while (!salir) {
            System.out.println("0. Salir.");
            System.out.println("1. Introducir paciente.");
            int opcion = pedirNumero("Seleccione una opción: ");
            switch (opcion) {
                case 0 -> {
                    finalizarConsultas();
                    salir = true;
                }
                case 1 -> crearPaciente();
                default -> System.out.println("Seleccione una opción disponible.");
            }
        }

    }

}