package Carrera_de_galgos_2;

public class Galgo implements Runnable {
    
    private int numero;
    private double velocidad;
    private double tiempoS;
    private long tiempoMs;
    private Carrera carrera;

    public Galgo(int numero, int distancia, Carrera carrera) {
        this.numero = numero;
        this.velocidad = Math.random()*(20-15)+15;
        this.tiempoS = distancia/this.velocidad;
        this.tiempoMs = (long)((tiempoS)*1000);
        this.carrera = carrera;
    }

    public void run() {
        int pasos = 20;
        long tiempoPorPaso = tiempoMs/pasos;
        for (int i = 1; i <= pasos; i++) {
            try {
                Thread.sleep(tiempoPorPaso);
            } catch (InterruptedException e) {
                System.err.println("¡Atención!: El galgo " + this.numero + " ha tenido un accidente. Se suspende la carrera.");
                return;
            }
            String barra = generarBarraProgreso(i, pasos);
            System.out.println("Galgo " + numero + " " + barra);
        }
        System.out.println("El galgo " + this.numero + " ha terminado. Ha quedado en " + carrera.getPosicion() + "º posición. Tiempo: " + String.format("%.2f", this.tiempoS) + "s");
        carrera.incementarPosicion();
    }

    private String generarBarraProgreso(int paso, int totalPasos) {
        int ancho = 20;
        int posicion = paso*ancho/totalPasos;
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i<ancho; i++) {
            if (i<posicion) {
                sb.append("=");
            } else if (i == posicion){
                sb.append(">");
            } else {
                sb.append(".");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public int getNumero() {
        return this.numero;
    }

}