// El hilo se crea a partir de implementando la interfaz Runnable. Esta práctica es mejor que la de crear el hilo heredando la clase Thread,
// ya que al hacer eso, no puedes heredar otra clase si quisieras. Eso sí, esta clase no es en sí un Thread, y se tiene que pasar por parámetro a un objeto Thread.

public class HiloImplementsRunnable implements Runnable {
    
    private String nombre;

    public HiloImplementsRunnable(String nombre) {
        this.nombre = nombre;
    }

    public void run() {
        try {
            Thread.sleep((long)(Math.random()*1000));
            System.out.println(this.nombre);
        } catch (InterruptedException e) {
            System.out.println("Hilo interrumpido.");
            Thread.currentThread().interrupt();
        }
    }

}