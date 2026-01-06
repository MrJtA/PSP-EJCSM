import java.util.*;

public class Main {
    
    final static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        
        String language = pedirIdioma();
        String ficheroOrigen = "ficheroOrigen.txt";
        String ficheroDestino = "ficheroDestino.txt";
        Trabajo t = new Trabajo(ficheroOrigen, language, ficheroDestino);
        t.ejecutarTarea();
        
    }
    
    public static String pedirIdioma() {
        String language;
        while (true) {
            System.out.print("Introduce el idioma al que traducir el contenido del fichero ('en' para inglés, 'fr' para francés o 'it' para italiano): ");
            language = sc.nextLine().trim();
            if (language.equals("en") || language.equals("fr") || language.endsWith("it")) {
                return language;
            } else {
                System.out.println("Introduce una opción válida por favor.");
            }
        }
    }

}