package Generacion_de_numeros_aleatorios;

public class Generador implements Runnable {
    
    private int longitudPorcion;
    private int[] array;

    public Generador(int longitudPorcion) {
        this.longitudPorcion = longitudPorcion;
        this.array = new int[longitudPorcion];
    }

    public void run() {
        for (int i = 0; i<this.longitudPorcion; i++) {
            this.array[i] = (int)(Math.random()*100);
        }
    }

    public int[] getArray() {
        return this.array;
    }

}