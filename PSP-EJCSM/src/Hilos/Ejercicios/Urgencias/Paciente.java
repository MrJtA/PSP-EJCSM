package Urgencias;

public class Paciente extends Thread {
    
    private String nombre;
    private long tiempoEspera;

    public Paciente(String nombre) {
        this.nombre = nombre;
        this.tiempoEspera = 0;
    }
 
    public void run() {
        Medico.atender(this);
    }

    public String getNombre() {
        return this.nombre;
    }

    public long getTiempo() {
        return this.tiempoEspera;
    }

    public void setTiempo(long tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

}