public class Main {
    
    public static void main(String[] args) {
        
        HiloExtendsThread hilo1 = new HiloExtendsThread("Hola");
        Thread hilo2 = new Thread(new HiloImplementsRunnable("Que tal boy"));
        hilo1.start();
        hilo2.start();

    }

}