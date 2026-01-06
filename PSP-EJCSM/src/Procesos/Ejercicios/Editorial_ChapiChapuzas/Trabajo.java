import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Trabajo {
    
    final private String rutaRelativa = "PSP-EJCSM/src/Procesos/Ejercicios/Editorial_ChapiChapuzas";
    final private File rutaBase = new File(System.getProperty("user.dir"), this.rutaRelativa);
    final private String sourcePath;
    final private String language;
    final private String destinationPath;
    final private String scriptCheck = "./check.sh";
    final private String scriptTranslate = "./translate.sh";
    final private List<ProcessBuilder> procesosEjecutables;

    public Trabajo(String sourcePath, String language, String destinationPath) {
        this.sourcePath = sourcePath;
        this.language = "-" + language;
        this.destinationPath = destinationPath;
        this.procesosEjecutables = new ArrayList<>();
    }

    public void ejecutarTarea() {
        try {
            ProcessBuilder pb1 = new ProcessBuilder("wsl", "bash", "-c", "cat " + this.sourcePath);
            ProcessBuilder pb2 = new ProcessBuilder("wsl", this.scriptCheck);
            ProcessBuilder pb3 = new ProcessBuilder("wsl", this.scriptTranslate, this.language);
            this.procesosEjecutables.add(pb1);
            this.procesosEjecutables.add(pb2);
            pb3.redirectOutput(new File(this.rutaRelativa, this.destinationPath));
            this.procesosEjecutables.add(pb3);
            pipe(this.procesosEjecutables);
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }

    }
    
    public void pipe(List<ProcessBuilder> procesosEjecutables) throws IOException, InterruptedException {
        for (ProcessBuilder pb : procesosEjecutables) {
            pb.directory(this.rutaBase);
        }
        List<Process> procesosEjecutados = ProcessBuilder.startPipeline(procesosEjecutables);
        Process pUltimo = procesosEjecutados.get(procesosEjecutados.size()-1);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(pUltimo.getErrorStream()))){
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println("Error: " + linea);
            }
        }
        int code = pUltimo.waitFor();
        if (code == 0) {
            System.out.println("La correción y traducción se han hecho correctamente.");
        } else {
            System.out.println("Fallo en la ejecución de algún script.");
        }
        procesosEjecutables.clear();
    }

}