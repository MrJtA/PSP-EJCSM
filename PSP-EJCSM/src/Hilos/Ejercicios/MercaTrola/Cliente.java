package MercaTrola;

public class Cliente extends Thread {


    private final int numeroCliente; // ID del cliente pasado como parmámetro.
    private final Cola cola; // Cola donde va a ser colocado el cliente para pasar a caja pasada como parmámetro.
    private long tiempoLlegada; // Tiempo de llegada de un cliente a una caja en milisegundos.

    public Cliente(int numeroCliente, Cola cola) {
        this.numeroCliente = numeroCliente;
        this.cola = cola;
    }
    
    // La ejecución del hilo registra a tiempo real el tiempo de llegada y añadirá al mismo hilo a la cola.
    @Override
    public void run() {
        this.tiempoLlegada = System.currentTimeMillis();
        this.cola.meter(this);
    }

    public int getNumeroCliente() {
        return this.numeroCliente;
    }

    public long getTiempoLlegada() {
        return this.tiempoLlegada;
    }
    
}