/*
* Se presenta una aplicación java para simular el comportamiento básico y la vista de la terminal de un sistema operativo, en este caso, Windows,
* en donde se ejecutará cada entrada introducida y se mostrará por pantalla el resultado a esperar de cada operación, según la terminal.
*
* @author Ethan Joaquín Castro San Miguel
*/

import java.io.*;
import java.util.*;
import tokenizer.*;

public class Minishell {

    final static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        
        // Establece el directorio de usuario del usuario del programa como directorio actual por defecto del prompt.
        File directorioActual = new File(System.getProperty("user.home"));
        /* Inicia un bucle para realizar las entradas del prompt, en el que cada iteración será mismamente una entrada.
         * El bucle y el programa terminará si se introduce el comando exit, o generará otra iteración si no se introduce ninguna entrada, en simulación de la terminal. */
        while (true) {
            // Presenta en pantalla la línea del prompt para que seguido de este, se introduzca la secuencia de comandos deseada por teclado.
            String input = "";
            System.out.print(directorioActual.getAbsolutePath()+">"+input);
            input = sc.nextLine().trim();
            if (input.equals("exit")) break;
            if (input.isEmpty()) continue;
            try {
                // La entrada se convierte a una linea de la clase TLine, para poder identificar y manipular los comandos y argumentos introducidos con una lista de comandos, de la clase TCommand.
                TLine linea = Tokenizer.tokenize(input);
                List<TCommand> comandos = linea.getCommands();
                // Crea una lista para almacenar los procesos de cada comando de la entrada, para después ejecutarlos.
                List<Process> procesosEjecutados = new ArrayList<>();
                // Si la entrada contiene un solo comando realiza el siguiente código.
                if (linea.getNcommands() == 1) {
                    // Recoge los argumentos del único comando y crea una lista de cadenas de texto para que se pueda introducir en un ProcessBuilder.
                    TCommand c = comandos.get(0);
                    List<String> argumentos = new ArrayList<>();
                    argumentos.add("cmd");
                    argumentos.add("/c");
                    argumentos.addAll(c.getArgv());
                    /* En caso de que el comando sea un cambio del directorio del prompt, 
                     * comprobando si contiene el argumento "cd" seguido de otro argumento que se espera ser el directorio a cambiar,
                     * el prompt cambia a dicho directorio indicado, nombrando el nuevo como el actual.
                     * A su vez, fuerza una nueva iteración ya que el comando no representa ninguna ejecución. */
                    if (c.getArgv().get(0).equals("cd") && c.getArgc() > 1) {
                        File directorioNuevo = new File(directorioActual, c.getArgv().get(1)).getCanonicalFile();
                        if (directorioNuevo.isDirectory()) {
                            directorioActual = directorioNuevo;
                        }
                        continue;
                    }
                    ProcessBuilder pb = new ProcessBuilder(argumentos);
                    pb.directory(directorioActual);
                    // El proceso hijo hereda la conexiones de entrada y salida del proceso padre, si el comando debe ser ejecutado en primer plano y no tiene redirecciones.
                    if (!linea.isBackground() && linea.getRedirectOutput() == null && linea.getRedirectError() == null) {
                        pb.inheritIO();
                    }
                    // Comprueba si el comando contiene redirecciones, y las establece.
                    redireccion(pb, linea, directorioActual, 0, 1);
                    Process p = pb.start();
                    procesosEjecutados.add(p);
                    // Ejecuta el comando en función de si debe ser ejectuado en primer o segundo plano.
                    comprobarBackground(linea, procesosEjecutados);
                // Si la entrada contiene más de un comando, se entiende que se trata de comandos conectados por pipes y realiza el siguiente código.
                } else if (linea.getNcommands() > 1) {
                    // Crea una lista de los ProcessBuilders de cada proceso en potencia para almaecenar cada uno de ellos y realizar las conexiones por pipe.
                    List<ProcessBuilder> procesosEjecutables = new ArrayList<>();
                    // Recoge los argumentos todos los comandos y crea una lista de cadenas de texto para cada uno de ellos para que se pueda introducir en un ProcessBuilder.
                    for (int i = 0; i < linea.getNcommands(); i++) {
                        TCommand c = comandos.get(i);
                        List<String> argumentos = new ArrayList<>();
                        argumentos.add("cmd");
                        argumentos.add("/c");
                        argumentos.addAll(c.getArgv());
                        ProcessBuilder pb = new ProcessBuilder(argumentos);
                        pb.directory(directorioActual);
                        // Comprueba si el comando contiene redirecciones, y las establece.
                        redireccion(pb, linea, directorioActual, i, linea.getNcommands());
                        procesosEjecutables.add(pb);
                    }
                    /* El proceso hijo hereda la conexiones de entrada y salida del proceso padre, si el comando debe ser ejecutado en primer plano y no tiene redirecciones.
                     * En este caso al contener la entrada más de un comando conectados por pipes, solo va a heredar las conexiones entrada el primero comando, y de salida el último comando. */
                    ProcessBuilder primero = procesosEjecutables.get(0);
                    if (!linea.isBackground() && linea.getRedirectInput() == null) {
                        primero.redirectInput(ProcessBuilder.Redirect.INHERIT);
                    }
                    ProcessBuilder ultimo = procesosEjecutables.get(procesosEjecutables.size() - 1);
                    if (!linea.isBackground() && linea.getRedirectOutput() == null && linea.getRedirectError() == null) {
                        ultimo.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                        ultimo.redirectError(ProcessBuilder.Redirect.INHERIT);
                    }
                    // Ejecuta los procesos si la entrada contiene más de un comando conectados por pipe.
                    comandosPipe(procesosEjecutables, linea);
                }
            } catch (IOException | MissingFileException e) {
                System.err.println(e.getMessage());
            }
            System.out.println();
        }

    }

    // Método para comprobar si un comando contiene redirecciones, y establecerlas. Lanza la excepcion MissingFileException.
    public static void redireccion(ProcessBuilder pb, TLine linea, File directorioActual, int indice, int nCommands) throws MissingFileException {
        /* La clase TLine contiene atributos que refieren a la redirección de entrada, salida y error, y que por defecto son nulos.
         * La clase Tokenizer comprueba si la entrada contiene carácteres de redirección. En tal caso, asigna los atributos de redirección en cuestión al fichero indicado.
         * A partir de esa información establece la redirección del ProcessBuilder pasado por parámetro del proceso al archivo en cuestión.
         * Se pasan por parámetros el índice del bucle que itera sobre los múltiples comandos si es que los hay, y el número de comandos, para rescatar la posición adecuada del comando en función de su tipo de redireción*/
        // Comprueba si el carácter de redirección es de entrada (<).
         if (indice == 0 && linea.getRedirectInput() != null) {
            pb.redirectInput(new File(directorioActual, linea.getRedirectInput()));
        }
        // Comprueba si el carácter de redirección es de salida (>).
        if (indice == nCommands - 1 && linea.getRedirectOutput() != null) {
            // Comprueba si el carácter de redirección de salida es en modo append/concanetado (>>).
            if (linea.isAppendOutput()) {
                pb.redirectOutput(ProcessBuilder.Redirect.appendTo(new File(directorioActual, linea.getRedirectOutput())));
            } else {
                pb.redirectOutput(new File(directorioActual, linea.getRedirectOutput()));
            }
        }
        // Comprueba si el carácter de redirección es de error (2>).
        if (indice == nCommands - 1 && linea.getRedirectError() != null) {
            // Comprueba si el carácter de redirección de error es en modo append (2>>).
            if (linea.isAppendError()) {
                pb.redirectError(ProcessBuilder.Redirect.appendTo(new File(directorioActual, linea.getRedirectError())));
            }
            else {
                pb.redirectError(new File(directorioActual, linea.getRedirectError()));
            }
        }
    }

    // Método para ejecutar la entrada si contiene más de un comando conectados por pipes. Lanza la excepcion MissingFileException.
    public static void comandosPipe(List<ProcessBuilder> procesosEjecutables, TLine linea) throws MissingFileException {
        try {
            // Pone en marcha el método .startPipeline, que automatiza la creación de tuberías encadenadas (pipeline) a partir de la lista de procesos a ejecutar pasada por parámetro, haciendo que la salida de cada proceso esté enlazada con la entrada del siguiente.
            List<Process> procesosEjecutados = ProcessBuilder.startPipeline(procesosEjecutables);
            // Nombra al último proceso de la lista.
            Process pUltimo = procesosEjecutados.get(procesosEjecutados.size() - 1);
            // Se analiza la salida de dicho proceso y se imprime en consola.
            InputStream isSalida = pUltimo.getInputStream();
            BufferedReader brSalida = new BufferedReader(new InputStreamReader(isSalida));
            String lineaSalida;
            while ((lineaSalida = brSalida.readLine()) != null) {
                System.out.println(">" + lineaSalida);
            }
            // Se analiza la salida de error de dicho proceso y se imprime en consola .
            InputStream isError = pUltimo.getErrorStream();
            BufferedReader brError = new BufferedReader(new InputStreamReader(isError));
            String lineaError;
            while ((lineaError = brError.readLine()) != null) {
                System.err.println(lineaError);
            }
            // Comprueba en última instancia si la entrada con comandos conectados por pipes debe ser ejecutado en primer o segundo plano.
            comprobarBackground(linea, procesosEjecutados);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    // Método para ejecutar la entrada en función de si debe ser ejectuado en primer o segundo plano.
    public static void comprobarBackground(TLine linea, List<Process> procesos) {
        try {
            /* La clase Tokenizer comprueba si al final de la línea se encuentra el signo de &, que indica que se debe ejecutar en segundo plano.
             * La clase TLine contiene un método para devolver true o false si se ha encontrado dicho signo. 
             * Al referir el signo siempre el plano de ejecucción para toda la entrada, globalizando esta, se debe tener en cuenta si se encuentra más de un comando. 
             * Imprime en ese caso el PID de todos los procesos de la entrada. */
            if (linea.isBackground()){
                System.out.print("Procesos en segundo plano con PIDs: ");
                for (Process p : procesos) {
                    System.out.print(p.pid());
                }
                System.out.println();
            // Si el proceso no debe ser ejecutado en segundo plano, es decir, en primer plano, pausa el programa y no vuelve a mostrar el prompt hasta que se ejecute al completo.
            } else {
                // En caso de que haya más de un proceso en la lista, es decir, que una entrada como comandos conectados por pipe deba ser ejectuado en primer plano, se hará el waitFor() al último de los procesos.
                procesos.get(procesos.size()-1).waitFor();
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

}