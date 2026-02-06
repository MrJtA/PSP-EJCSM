package La_cena_de_los_Filosofos;

import java.util.concurrent.Semaphore;

// Clase "Tenedor" que actúa de tenedor para el uso de comer de un filósofo.

public class Tenedor {

    private final Semaphore semaforo = new Semaphore(1); // Semáforo de 1 capacidad que representará la disponibilidad del tenedor.

    // Método para que un filósofo tome el tenedor, y bloquee su uso a los demás filósofos por pertenencia del que lo ha agarrado.
    public void usar() throws InterruptedException {
        this.semaforo.acquire();
    }

    // Método para que un filósofo deje el tenedor, y libere su uso a otro filósofo que demande su uso para comer.
    public void dejar() throws InterruptedException {
        this.semaforo.release();
    }
 
}