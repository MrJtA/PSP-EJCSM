package FastFood_Service;

import java.util.ArrayList;

public class Pedidos {
    
    private int numeroPedido;
    private int capacidadPedidos;
    private ArrayList<Integer> cola;

    public Pedidos() {
        this.numeroPedido = 1;
        this.capacidadPedidos = 5;
        this.cola = new ArrayList<>();
    }

    public synchronized int añadir(int pedido, String empleado) throws InterruptedException {
        while (this.cola.size() == this.capacidadPedidos) {
            System.out.println(empleado + " esperando...");
            wait();
        }
        int pedidoAsignado = this.numeroPedido; 
        this.cola.add(pedidoAsignado);
        this.numeroPedido++;
        notifyAll();
        return pedidoAsignado;
    }

    public synchronized int quitar(String empleado) throws InterruptedException {
        while (cola.isEmpty()) {
            System.out.println(empleado + " esperando...");
            wait();
        }
        int pedidoExtraido = cola.remove(0);
        notifyAll(); 
        return pedidoExtraido;
    }

    public synchronized int getNumeroPedido() {
        return this.numeroPedido;
    }

    public int getCapacidad() {
        return this.capacidadPedidos;
    }

}