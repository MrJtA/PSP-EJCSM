package Ejercicio2;

import java.util.concurrent.Semaphore;

public class Profesor {
    
    private final Semaphore semaforo = new Semaphore(1);

    public void atender(Alumno alumno, int dificultad) throws InterruptedException {
        semaforo.acquire();
        int idAlumno = alumno.getIDAlumno();
        System.out.println("Hola, alumno " + idAlumno);
        switch (dificultad) {
            case 1 -> {
                Thread.sleep(1000);
                System.out.println("Alumno " + idAlumno + " es facil, solo tienes que darle una vuelta.");
            }
            case 2 -> {
                Thread.sleep(3000);
                int cuadrado = alumno.getPreguntasResueltas()^2;
                System.out.println("Alumno " + idAlumno + " ya llevo " + cuadrado + " dudas resueltas, dame un respiro.");
            }
            case 3 -> {
                Thread.sleep(5000);
                System.out.println("Alumno " + idAlumno + " ...");
            }
        }
    }
    
    public void dejar() {
        semaforo.release();
    }

}