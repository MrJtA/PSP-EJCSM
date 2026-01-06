package Contador_Sincrono;

public class Sumador extends Thread {
    
    private String name;
    private Contador c;

    public Sumador(String name, Contador c) {
        this.name = name;
        this.c = c;
    }

    public void run() {
        for (int i = 0; i<10; i++) {
            c.incrementa();
        }
        System.out.println("El " + this.name + " ha terminado.");
    }

}