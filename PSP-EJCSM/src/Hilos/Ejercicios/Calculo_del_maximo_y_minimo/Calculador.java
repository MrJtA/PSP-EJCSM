package Calculo_del_maximo_y_minimo;

public class Calculador implements Runnable {
    
    private int[] array;
    private int maximo;
    public int minimo;

    public Calculador(int[] array) {
        this.array = array;
        this.maximo = Integer.MIN_VALUE;
        this.minimo = Integer.MAX_VALUE;
    }

    public void run() {
        for (int i = 0; i<this.array.length; i++) {
            if (array[i]>this.maximo) {
                this.maximo = array[i];
            }
            if (array[i]<this.minimo) {
                this.minimo = array[i];
            }
        }
    }

    public int getMaximo() {
        return this.maximo;
    }

    public int getMinimo() {
        return this.minimo;
    }

}