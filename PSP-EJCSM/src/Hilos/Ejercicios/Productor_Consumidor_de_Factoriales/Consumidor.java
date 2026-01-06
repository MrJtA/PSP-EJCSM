package Productor_Consumidor_de_Factoriales;

public class Consumidor extends Thread {
    
    private Cola cola;
    private boolean parar;

    public Consumidor(Cola cola) {
        this.cola = cola;
        this.parar = false;
    }

    public void run() {
        try {
            while (!this.parar) {
                Thread.sleep((long)(Math.random()*1000));
                int numero = this.cola.quitar();
                int resultado = 1;
                for (int i = 1; i<numero+1; i++) {
                    resultado *= i;
                }
                System.out.println("Factorial de " + numero + ": " + resultado + ".");
            }
        } catch (InterruptedException e) {
            System.err.println("La producción ha sido interrumpida.");
            Thread.currentThread().interrupt();
        }
    }

    public void pararCalculos() {
        this.parar = true;
    }

}
