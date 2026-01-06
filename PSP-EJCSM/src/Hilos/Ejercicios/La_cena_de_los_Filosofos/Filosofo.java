package La_cena_de_los_Filosofos;

// Clase "Filosofo" que actúa de hilo y que representa a un filosofo individual en la cena.

public class Filosofo extends Thread {
    
    private final int numeroFilosofo; // ID del filósofo pasado como parámetro.
    private final Tenedor tenedorIzquierdo; // Tenedor que el filósofo tiene a su izquierda pasado como parámetro.
    private final Tenedor tenedorDerecho; // Tenedor que el filósofo tiene a su derecha pasado como parámetro.
    private boolean parar;

    public Filosofo(int numeroFilosofo, Tenedor tenedorIzquierdo, Tenedor tenedorDerecho) {
        this.numeroFilosofo = numeroFilosofo;
        this.tenedorIzquierdo = tenedorIzquierdo;
        this.tenedorDerecho = tenedorDerecho;
        this.parar = false;
    }

    // La ejecución del hilo realiza un bucle infinito para que hace al filósofo alternar entre pensar y comer.
    @Override
    public void run() {
        try {
            while (!this.parar) {
                // El filósofo parará de pensar solo hasta que tenga a su disposición los dos tenedores para comer, los cuales intentará coger.
                System.out.println("Filósofo " + this.numeroFilosofo + " pensando...");
                tenedorIzquierdo.usar();
                if (this.parar) {
                    return;
                }
                System.out.println("Filósofo " + this.numeroFilosofo + " usa el tenedor IZQUIERDO.");
                tenedorDerecho.usar();
                System.out.println("Filósofo " + this.numeroFilosofo + " usa el tenedor DERECHO y empieza a comer.");
                // Se simulará un tiempo de comida con un sleep del hilo cuando pueda comer. Cuando termine, dejará de usar los tenedores y inciará otro bucle.
                Thread.sleep((long)(Math.random()*5000)); 
                System.out.println("Filósofo " + this.numeroFilosofo + " termina de comer y suelta los tenedores.");
                tenedorIzquierdo.dejar();
                tenedorDerecho.dejar();
                }
        } catch (InterruptedException e) {
            System.out.println("Filósofo " + this.numeroFilosofo + " ha sido interrumpido.");
            Thread.currentThread().interrupt();
        }
    }

    // Método para activar en el main cuando se quiera parar la actividad del hilo.
    public void parar() {
        this.parar = true;
    }

}