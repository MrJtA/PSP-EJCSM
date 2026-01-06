package FastFood_Service;

public class Cocinero extends Thread{
    
    private Pedidos pedidosNuevos;
    private Pedidos pedidosPreparados;
    private boolean parar;

    public Cocinero (Pedidos pedidosNuevos, Pedidos pedidosPreparados) {
        this.pedidosNuevos = pedidosNuevos;
        this.pedidosPreparados = pedidosPreparados;
        this.parar = false;
    }

    public void run() {
        try {
            while (!this.parar) {
                int numeroPedido = this.pedidosNuevos.quitar("Cocinero para preparar");
                System.out.println("Cocinando pedido nº " + numeroPedido + "...");
                Thread.sleep((long)(Math.random()*10000));
                System.out.println("Pedido nº" + numeroPedido + " cocinado.");
                this.pedidosPreparados.añadir(numeroPedido, "Cocinero para entregar");
            }
        } catch (InterruptedException e) {
            System.err.println("El trabajo del cocinero ha sido interrumpido.");
            Thread.currentThread().interrupt();
        }
    }

    public void pararHilo() {
        this.parar = true;
    }

}