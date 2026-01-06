package MercaTrola;

// Clase "Cola" que actúa como cola para almacenar clientes, ya sea en el modelo de N colas o cola única, y que se pasará como parámetro en la creación de los clientes y la caja.

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Cola {

    private final BlockingQueue<Cliente> cola = new LinkedBlockingQueue<>(); // Estructura de datos concurrente tipo FIFO para hilos oportuna para ejercer de función de cola y almacenamiento de hilos, que puede bloquear las operaciones de inserción o extracción hasta que la operación sea posible.
    private final Cliente clienteFinal = new Cliente(0, null); // Atributo "Cliente" con número 0, que ejercerá de finalizador de la actividad de la cola y actuará como último de esta si se introduce en ella, para evitar un deadlock y/o inanición cuando se acaben los clientes en la cola al atenderlos todos, debido a la función del método .take() de la cola.
    private final boolean colaUnica; // Atributo booleano pasado como parámetro para indicar con true y saber si la cola es la única para todas las cajas del supermercado en el respectivo modelo.


    public Cola(boolean colaUnica) {
        this.colaUnica = colaUnica;
    }

    // Método para activar en el main que añade a la cola el cliente que ejerce de finalizador.
    public void acabar() {
        this.cola.add(this.clienteFinal);
    }

    // Método que corresponde su ejecución a la clase Cliente para meter al mismo cliente a la cola.
    public void meter(Cliente cliente) {
        this.cola.add(cliente);
    }

    // Método que corresponde su ejecución a la clase Caja para atender al último cliente de la cola.
    // Se verificará si el cliente a atender en cuestión es el que ejerce de finalizador. En tal caso, devolverá un valor nulo que servirá al bucle de la clase Caja para pararlo.
    // En caso contrario, devolverá el cliente en cuestión.
    public Cliente atender() throws InterruptedException {
        Cliente cliente = this.cola.take();
        if (cliente == this.clienteFinal) {
            return null;
        }
        return cliente;
    }

    public boolean getTipoCola() {
        return this.colaUnica;
    }

}