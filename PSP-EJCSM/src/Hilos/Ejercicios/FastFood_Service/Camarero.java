package FastFood_Service;

public class Camarero extends Thread {
    
    private Pedidos pedidosNuevos;
    private boolean parar;

    public Camarero(Pedidos pedidos) {
        this.pedidosNuevos = pedidos;
        this.parar = false;
    }

    public void run() {
        try {
            while (!this.parar) {
                int numeroPedido = this.pedidosNuevos.getNumeroPedido();
                System.out.println("Camarero tomando nota...");
                Thread.sleep((long)(Math.random()*5000));
                int numeroPedidoRegistrado = pedidosNuevos.añadir(numeroPedido, "Camarero");
                System.out.println("Pedido nº" + numeroPedidoRegistrado + " anotado.");
            }
        } catch (InterruptedException e) {
            System.err.println("El trabajo del camarero ha sido interrumpido.");
            Thread.currentThread().interrupt();
        }
    }

    public void pararHilo() {
        this.parar = true;
    }

}