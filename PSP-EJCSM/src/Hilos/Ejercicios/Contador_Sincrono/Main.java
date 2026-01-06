package Contador_Sincrono;

public class Main {
    
    public static void main(String[] args) {
        
        Contador contador = new Contador(0);
        Sumador sumador = new Sumador("Sumador", contador);
        Restador restador = new Restador("Restador", contador);
        Thread h = new Thread(restador);
        sumador.start();
        h.start();
        try {
            sumador.join();
            h.join(); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("El hilo principal fue interrumpido.");
        }

    }

}