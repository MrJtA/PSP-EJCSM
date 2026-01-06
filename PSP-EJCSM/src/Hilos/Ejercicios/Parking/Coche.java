package Parking;

public class Coche extends Thread {

    private Parking parking;
    private String matricula;

    public Coche(Parking parking, String matricula) {

        this.parking = parking;
        this.matricula = matricula;
        
    }

    public void run() {
        try {
            System.out.println("Coche '" + this.matricula + "' intentando entrar al parking...");
            parking.meterCoche(this);
            System.out.println("Coche '" + this.matricula + "' aparcado.");
            double tiempoMilis = Math.random()*4000+1000;
            Thread.sleep((long)(tiempoMilis));
            int tiempoSegundos = (int) tiempoMilis/1000;
            double costo = parking.cobrar(tiempoSegundos);
            System.out.println("Coche '" + this.matricula + "' salido del parking.");
            System.out.println("Ha pagado " + costo + "€.");
        } catch (InterruptedException e) {
            System.out.println("El estacionamiento del coche con matrícula " + this.matricula + " ha sido interrumpido.");
            Thread.currentThread().interrupt();
        } finally {
            try {
                parking.sacarCoche(this);
            } catch (InterruptedException e) {
                System.out.println("El estacionamiento del coche con matrícula " + this.matricula + " ha sido interrumpido.");
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public String toString() {
        return "Coche '" + this.matricula + "'";
    }

}