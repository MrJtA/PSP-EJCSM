package Productor_Consumidor_de_Factoriales;

public class Productor extends Thread {
    
    private Cola cola;
    private boolean parar;

    public Productor(Cola cola) {
        this.cola = cola;
    }

    public void run() {
        try {
            while (!this.parar) {
                Thread.sleep((long)(Math.random()*1000));
                int numero = (int)(Math.random()*10+1);
                this.cola.añadir(numero);
                System.out.println("Número " + numero + " añadido.");
            }
        } catch (InterruptedException e) {
            System.err.println("El cálculo del número ha sido interrumpido.");
            Thread.currentThread().interrupt();
        }
    }

    public void pararProduccion() {
        this.parar = true;
    }

}