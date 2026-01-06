package Contador_Sincrono;

public class Restador implements Runnable {
    
    private String name;
    private Contador c;

    public Restador(String name, Contador c) {
        this.name = name;
        this.c = c;
    }

    public void run() {
        for (int i = 0; i<10; i++) {
            c.decrementa();
        }
        System.out.println("El " + this.name + " ha terminado.");
    }

}