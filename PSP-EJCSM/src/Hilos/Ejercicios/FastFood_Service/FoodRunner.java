package FastFood_Service;

public class FoodRunner extends Thread {
    
    private Pedidos pedidosPreparados;
    private boolean parar;

    public FoodRunner(Pedidos pedidosPreparados) {
        this.pedidosPreparados = pedidosPreparados;
        this.parar = false;
    }

    public void run() {
        while (!this.parar) {
            try {
                int numeroPedido = this.pedidosPreparados.quitar("Food runner");
                System.out.println("Repartiendo pedido nº " + numeroPedido + "...");
                Thread.sleep((long)(Math.random()*5000));
                System.out.println("Pedido nº" + numeroPedido + " entregado.");
            } catch (InterruptedException e) {
                System.err.println("El trabajo del food runner ha sido interrumpido.");
                Thread.currentThread().interrupt();
            }
        }
    }

    public void pararHilo() {
        this.parar = true;
    }

}