package La_cena_de_los_Filosofos;

import java.util.Scanner;

// Clase "Mesa" que ejecutará el código y actuará como mesa de los filósofos.

public class Mesa {
    
    final static Scanner sc = new Scanner(System.in);

    // Método para pedir por teclado un número mayor que 0, que se usará para indicar el número de filósofos que se desea incluir en la cena.
    // Se incluirá la disponibilidad del 1 si es que se desea que haya solamente un filósofo y este coma solo.
    public static int pedirNumeroFilosofos() {
        int numero;
        while (true) {
            System.out.print("Introduce el número de filósofos para la cena: ");
            String entrada = sc.nextLine();
            try {
                numero = Integer.parseInt(entrada);
                if (numero <= 0) {
                    System.out.println("Error: Introduce un número mayor que 0.");
                } else {
                    return numero;
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: Introduce un valor válido.");
            }
        }
    }

    // Método que inicia el código principal y representa la cena de los filósofos.
    public static void empezarCena(int numeroFilosofos) {
        System.out.println("La cena de los filósofos comienza.");
        // Al comformarse la actividad de los hilos por un bucle infinito, se decidirá cuando acabar con el código pulsando ENTER.
        System.out.println("Pulsa ENTER para finalizarla.");
        // El número de tenedores siguiendo la lógica de una mesa circular, será el mismo que el número de filósofos.
        // En caso de que solo haiga un filósofo, se pondrán dos tenedores en vez de uno.
        int numeroTenedores;
        if (numeroFilosofos == 1) {
            numeroTenedores = 2;
        } else {
            numeroTenedores = numeroFilosofos;
        }
        Tenedor[] tenedores = new Tenedor[numeroTenedores]; // Lista de tenedores.
        Filosofo[] filosofos = new Filosofo[numeroFilosofos]; // Lista de filósofos.
        // Bucle para la creación de los tenedores.
        for (int i = 0; i<numeroTenedores; i++) {
            tenedores[i] = new Tenedor();
        }
        // Bucle para la creación y ejecución de los filósofos.
        for (int i = 0; i<numeroFilosofos; i++) {
            // Se asigna el tenedor izquierdo y derecho del filósofo a crear, con el coincidente del índice del bucle y el del índice del siguiente, de tal manera que el tenedor derecho sea el izquierdo del siguiente filósofo.
            Tenedor tenedorIzquierdo = tenedores[i];
            Tenedor tenedorDerecho = tenedores[(i+1)%numeroTenedores];
            Filosofo filosofo;
            // Si todos los filósofos cogiesen primero el tenedor izquierdo, ocurriría un bloqueo mutuo ya que todos quedarían a la espera de su derecho, pues estará cogido por el filósofo anterior con su tenedor izquierdo.
            // Para evitar ese bloqueo mutuo, se insta al último filósofo de la lista a coger primero el tenedor derecho y luego el izquierdo.
            // De esa manera, quedará libre a competir por usar el tenedor derecho del último filósofo, con el izquierdo del primero, y así se romperá dicho problema.
            if (numeroFilosofos>1 && i == numeroFilosofos-1) {
                filosofo = new Filosofo(i+1, tenedorDerecho, tenedorIzquierdo);
            } else {
                filosofo = new Filosofo(i+1, tenedorIzquierdo, tenedorDerecho);
            }
            filosofos[i] = filosofo;
            filosofo.start();
        }
        // La actividad de cada filósofo parará cuando se pulse ENTER.
        sc.nextLine();
        System.out.print("La cena ha terminado. Esperando a que todos los filósofos terminen de comer...");
        System.out.println();
        for (int i = 0; i<numeroFilosofos; i++) {
            filosofos[i].parar();
        }
    }

    // Método main.
    public static void main(String[] args) {
        
        int numeroFilosofos = pedirNumeroFilosofos();
        empezarCena(numeroFilosofos);

    }

}