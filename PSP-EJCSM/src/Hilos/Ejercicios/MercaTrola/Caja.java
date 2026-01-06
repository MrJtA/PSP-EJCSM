package MercaTrola;

public class Caja extends Thread {
    
    private final int numeroCaja; // ID de la caja.
    private final Cola cola; // Cola de clientes correspondiente para atender.
    private long tiempoEsperaTotal; // Tiempo total que tarda una caja en atender a todos los clientes que le corresponden en milisegundos.

    public Caja(int numeroCaja, Cola cola) {
        this.numeroCaja = numeroCaja;
        this.cola = cola;
        this.tiempoEsperaTotal = 0;
    }

    // La ejecución del hilo realiza un bucle infinito en el que trata de atender al primer cliente de la cola, eliminándolo de la misma.
    @Override
    public void run() {
        try {
            while (true) {
                Cliente cliente = this.cola.atender();
                // El bucle terminará si el cliente es nulo, que se activará en el main con un método de la clase Cola.
                if (cliente == null) {
                    break;
                }
                int numeroCliente = cliente.getNumeroCliente();
                // Verifica si la cola es única para todas las cajas, para avisar a qué caja se le ha asignado al cliente a la hora de ser atendido.
                boolean colaUnica = this.cola.getTipoCola();
                if (colaUnica) {
                    System.out.println("Cliente " + numeroCliente + " asignado a caja " + this.numeroCaja);
                }
                System.out.println("Cliente " + numeroCliente + " realizando compra.");
                // Se registrará el tiempo que tarda el cliente en llegar y ser atendido, para a su vez sumarlo al tiempo total de la caja y simuar un tiempo de atención al cliente.
                long tiempoEsperaCliente = System.currentTimeMillis()-cliente.getTiempoLlegada();
                this.tiempoEsperaTotal += tiempoEsperaCliente;
                long tiempoAtencion = (long)(Math.random()*1000);
                Thread.sleep(tiempoAtencion);
                System.out.println("Cliente " + numeroCliente + " atendido. Tiempo de espera: " + tiempoEsperaCliente + "ms.");
            }
        } catch (InterruptedException e) {
            System.err.println("La caja " + this.numeroCaja + " ha sido interrumpida.");
            Thread.currentThread().interrupt();
        }
    }

    public int getNumeroCaja() {
        return this.numeroCaja;
    }

    public long getTiempoEsperaTotal() {
        return this.tiempoEsperaTotal;
    }

}