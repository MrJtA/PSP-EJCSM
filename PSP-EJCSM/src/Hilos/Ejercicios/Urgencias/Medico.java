package Urgencias;

public class Medico {

    private static Paciente pacienteActual = null;

    public Paciente getPacienteActual() {
        return pacienteActual;
    }
    
    public static synchronized void atender(Paciente paciente) {
        String nombre = paciente.getNombre();
        pacienteActual = paciente;
        try {
            long tiempoInicioAtencion = System.currentTimeMillis();
            long tiempoEsperaMilis = tiempoInicioAtencion-paciente.getTiempo();
            int tiempoEsperaSegundos = (int) Math.round(tiempoEsperaMilis/1000.0);
            if (paciente.isInterrupted()) {
                System.err.println("La consulta del paciente " + nombre + " se realizará otro día.");
                return;
            }
            System.out.println("El paciente " + nombre + " ha entrado a la consulta. Ha esperado " + tiempoEsperaSegundos + "s.");
            Thread.sleep((long)(Math.random()*30000));
            System.out.println("El paciente " + nombre + " ha terminado su consulta.");
        } catch (InterruptedException e) {
            System.err.println("La consulta del paciente " + nombre + " ha sido interrumpida.");
            Thread.currentThread().interrupt();
        }
    }

}