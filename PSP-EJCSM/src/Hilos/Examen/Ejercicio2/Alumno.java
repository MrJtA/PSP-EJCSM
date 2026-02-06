package Ejercicio2;

public final class Alumno extends Thread {
    
    private final int idAlumno;
    private final int[] preguntas;
    private final Profesor profesor;
    private int preguntasResueltas = 0;

    public Alumno(int idAlumno, Profesor profesor) {
        this.idAlumno = idAlumno;
        this.preguntas = crearPreguntas();
        this.profesor = profesor;
    }

    public int[] crearPreguntas() {
        int[] aux = new int[(int)(Math.random()*3)+1];
        for (int i = 0; i<aux.length; i++) {
            aux[i] = (int)(Math.random()*3)+1;
        }
        return aux;
    }

    @Override
    public void run() {
        try {
            System.out.println("Alumno " + this.idAlumno + " tiene " + this.preguntas.length + " preguntas.");
            for (int i = 0; i<this.preguntas.length; i++) {
                switch (this.preguntas[i]) {
                    case 1 -> System.out.println("Alumno " + this.idAlumno + " esperando a preguntar una pregunta FÁCIL...");
                    case 2 -> System.out.println("Alumno " + this.idAlumno + " esperando a preguntar una pregunta MEDIA...");
                    case 3 -> System.out.println("Alumno " + this.idAlumno + " esperando a preguntar una pregunta DIFÍCIL...");
                }
                this.profesor.atender(this, this.preguntas[i]);
                this.preguntasResueltas++;
                this.profesor.dejar();
            }
            System.out.println("Alumno " + this.idAlumno + ": ¡He terminado y solo he necesitado " + this.preguntas.length + " preguntas!");
        } catch (InterruptedException e) {
            System.out.println("Error: Alumno " + this.idAlumno + " interrumpido.");
            Thread.currentThread().interrupt();
        }
    }

    public int getIDAlumno() {
        return this.idAlumno;
    }

    public int getPreguntasResueltas() {
        return this.preguntasResueltas;
    }

}