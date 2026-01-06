package Productor_Consumidor_de_Factoriales;

import java.util.*;

public class Cola {
    
    private int tamaño;
    private ArrayList<Integer> cola;

    public Cola() {
        this.tamaño = 10;
        this.cola = new ArrayList<>();
    }

    public synchronized void añadir(int numero) throws InterruptedException {
        while (this.cola.size() == this.tamaño) {
            System.out.println("Productor esperando...");
            wait();
        }
        this.cola.add(numero);
        notifyAll();
    }

    public synchronized int quitar() throws InterruptedException {
        while (this.cola.isEmpty()) {
            System.out.println("Consumidor esperando...");
            wait();
        }
        int numero = this.cola.remove(0);
        notifyAll();
        return numero;
    }

    public int getTamaño() {
        return this.tamaño;
    }

}