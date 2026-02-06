package Ejercicio1;

public final class Gymbro extends Thread {
    
    private final int idGymbro;
    private final Maquina[] maquinas;
    private final int[] rutina;
    private long tiempo;

    public Gymbro(int numeroGymbro, Maquina[] maquinas) {
        this.idGymbro = numeroGymbro;
        this.maquinas = maquinas;
        this.rutina = crearRutina();
    }

    public int[] crearRutina() {
        int[] aux = new int[4];
        for (int i = 0; i<4; i++) {
            aux[i] = (int)(Math.random()*this.maquinas.length);
        }
        return aux;
    }

    @Override
    public void run() {
        try {
            long inicio = System.currentTimeMillis();
            for (int i = 0; i<this.rutina.length; i++) {
                int numeroMaquina = this.rutina[i];
                System.out.println("Gymbro " + this.idGymbro + " esperando a usar la máquina " + numeroMaquina + "...");
                this.maquinas[numeroMaquina].usar();
                System.out.println("Gymbro " + this.idGymbro + " usando la máquina " + numeroMaquina + ".");
                Thread.sleep((long)(Math.random()*1000));
                this.maquinas[numeroMaquina].dejar();
                System.out.println("Gymbro " + this.idGymbro + " abandona la máquina " + numeroMaquina + ".");
            }
            this.tiempo = System.currentTimeMillis()-inicio;
            System.out.println("Gymbro " + this.idGymbro + " termina su rutina por hoy. Tiempo: " + this.tiempo + "ms.");
        } catch (InterruptedException e) {
            System.out.println("Error: Gymbro " + this.idGymbro + " interrumpido.");
            Thread.currentThread().interrupt();
        }
    }

    public int getIDGymbro() {
        return this.idGymbro;
    }

    public long getTiempo() {
        return this.tiempo;
    }

}