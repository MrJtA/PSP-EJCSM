package Contador_Sincrono;

public class Contador {
    
    private int c = 0;

    public Contador(int c) {
        this.c = c;
    }

    public synchronized void incrementa() {
        this.c++;
        System.out.println(c);
    }
    
    public synchronized void decrementa() {
        this.c--;
        System.out.println(c);
    }

    public synchronized int getValor() {
        return this.c;
    }

}
