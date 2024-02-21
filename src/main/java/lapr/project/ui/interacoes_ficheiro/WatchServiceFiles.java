package lapr.project.ui.interacoes_ficheiro;
import lapr.project.data.DroneBD;
import lapr.project.data.ScooterBD;
import lapr.project.controller.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static lapr.project.utils.Constantes.PATH;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;

/**
 * The type Watch service files.
 */
public class WatchServiceFiles extends Thread{


    /**
     * Este metodo quando iniciado vai chamar o metodo "run" passando o path dos ficheiros po parametro.
     */
    @Override
    public void run() {
        WatchServiceFiles.run(PATH);
    }


    /**
     * Este metodo verifica se o ficheiro lock ou o estimativa são criados.
     * Caso sejam criados o metodo chama as respitavas funções para tratar dos ficheiros.
     *
     * @param path the path
     */
    public static void run(String path) {
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path dir = Paths.get(path);
            dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

            System.out.println("Watch Service registered for dir: " + dir.getFileName());

            while (true) {
                WatchKey key;
                try {
                    key = watcher.take();
                } catch (InterruptedException ex) {
                    return;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    @SuppressWarnings("unchecked")
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();

                    System.out.println(kind.name() + ": " + fileName);

                    if (kind == ENTRY_MODIFY || kind == ENTRY_CREATE || kind == ENTRY_DELETE ) {
                        System.out.println("My source file has changed!!!");
                    }

                    if (kind == ENTRY_CREATE) {
                        if(fileName.toString().endsWith(".data.flag.txt") && fileName.toString().startsWith("lock")){
                            TimeUnit.SECONDS.sleep(2);
                            EnviarEmailSegurancaTask.sendEmail(fileName.toString(),new ScooterBD(),new DroneBD());
                        }

                        if(fileName.toString().endsWith(".data.flag.txt") && fileName.toString().startsWith("estimate")){
                            TimeUnit.SECONDS.sleep(2);
                          EnviarEmailEstimativaTask.sendEmail(fileName.toString(),new ScooterBD(),new DroneBD());
                        }
                    }
                }

                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }

        } catch (IOException | InterruptedException ex) {
            System.err.println(ex);
        }
    }
}
