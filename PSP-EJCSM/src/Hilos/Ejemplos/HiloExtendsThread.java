// Se crea el hilo como objeto herencia de la clase Thread. Esta clase es en sí un Thread.

public class HiloExtendsThread extends Thread {
    
    private String nombre;

    public HiloExtendsThread(String nombre) {
        this.nombre = nombre;
    }

    public void run() {
        try {
            Thread.sleep((long)(Math.random()*1000));
            System.out.println(this.nombre);
        } catch (InterruptedException e) {
            System.out.println("Hilo interrumpido.");
            Thread.currentThread().interrupt();
        }
    }

}