package Parking;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Parking {
    
    private Semaphore semaforo;
    private Lock candado;
    private int numeroPlazas;
    private double recaudacion;
    private double precio;
    private ArrayList<Coche> listaCoches;

    public Parking(int numeroPlazas) {
        this.numeroPlazas = numeroPlazas;
        this.semaforo = new Semaphore(this.numeroPlazas);
        this.candado = new ReentrantLock();
        this.recaudacion = 0;
        this.precio = 10;
        this.listaCoches = new ArrayList<>();
    }

    public void meterCoche(Coche coche) throws InterruptedException {
        this.semaforo.acquire();
        this.listaCoches.add(coche);
    }

    public double cobrar(int tiempo) {
        this.candado.lock();
        this.recaudacion += tiempo*this.precio;
        this.candado.unlock();
        return this.recaudacion;
    }

    public void sacarCoche(Coche coche) throws InterruptedException {
        this.semaforo.release();
        this.listaCoches.remove(coche);
    }

    public void getCoches() {
        for (Coche coche : this.listaCoches) {
            System.out.println("- " + coche);
        }
    }

    public double getRecaudacion() {
        this.candado.lock();
        try {
            return this.recaudacion;
        } finally {
            this.candado.unlock();
        }
    }

}