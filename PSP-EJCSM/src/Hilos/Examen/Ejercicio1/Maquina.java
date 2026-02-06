package Ejercicio1;

import java.util.concurrent.Semaphore;

public class Maquina {
    
    private final int idMaquina;
    private final Semaphore semaforo;

    public Maquina(int idMaquina) {
        this.idMaquina = idMaquina;
        this.semaforo = new Semaphore(1);
    }

    public void usar() throws InterruptedException {
        this.semaforo.acquire();
    }

    public void dejar() {
        this.semaforo.release();
    }
    
    public int getIDMaquina() {
        return this.idMaquina;
    }

}