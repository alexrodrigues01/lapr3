package lapr.project.utils;

import lapr.project.ui.interacoes_ficheiro.WatchServiceFiles;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;



class WatchServiceFilesTest {
    @Test
    public void run() throws InterruptedException {
        try {
            WatchServiceFiles watchServiceFiles = new WatchServiceFiles();
            watchServiceFiles.start();
            TimeUnit.SECONDS.sleep(2);
            watchServiceFiles.interrupt();
        }catch (NullPointerException e){
            assertTrue(false);
        }
    }

    @Test
    public void run1(){
        WatchServiceFiles.run("a/b/c/d/e/f/g/wwww/s");
    }
}