package Calculo_de_numeros_primos;

public class Calculador implements Runnable {

    private int contador;
    private int inicioPorcion;
    private int finalPorcion;

    public Calculador(int inicioPorcion, int finalPorcion) {
        this.contador = 0;
        this.inicioPorcion = inicioPorcion;
        this.finalPorcion = finalPorcion;
    }

    public void run() {
        for (int i = this.inicioPorcion; i<this.finalPorcion; i++) {
            if (i == 1) {
                continue;
            }
            boolean esPrimo = true;
            for (int j = 2; j<i-1; j++) {
                if (i%j == 0) {
                    esPrimo = false;
                }
            }
            if (esPrimo) {
                this.contador++;
            }
        }
    }

    public int getContador() {
        return this.contador;
    }

}