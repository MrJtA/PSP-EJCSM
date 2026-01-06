package Carrera_de_galgos_2;

import java.util.Scanner;

public class Carrera {
    
    final static Scanner sc = new Scanner(System.in);
    
    private int posicion = 1;

    public synchronized void incementarPosicion() {
        this.posicion++;
    }

    public synchronized int getPosicion() {
        return this.posicion;
    }

}
