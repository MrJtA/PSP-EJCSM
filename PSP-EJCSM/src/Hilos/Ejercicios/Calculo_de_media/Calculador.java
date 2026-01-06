package Calculo_de_media;

public class Calculador implements Runnable {
    
    private int[] array;
    private int sumaParcial;

    public Calculador(int[] array) {
        this.array = array;
        this.sumaParcial = 0;
    }

    public void run() {
        for (int i = 0; i<this.array.length; i++) {
            this.sumaParcial += this.array[i];
        }
    }

    public int getSumaParcial() {
        return this.sumaParcial;
    }

}