package wenlin.demo.PasswordService.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

@Service
public class FileWatcher implements Runnable {

    @Value("${data.userFile}")
    private String input;

    @Override
    public void run() {
        File file = new File(input);
        try (WatchService watcher = FileSystems.getDefault().newWatchService()) {
            Path path = file.toPath().getParent();
            path.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
            while (true) {
                WatchKey watchKey;
                try {
                    watchKey = watcher.poll(1000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    return;
                }
                if (watchKey == null) {
                    Thread.yield();
                    continue;
                }
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    @SuppressWarnings("unchecked")
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();

                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        Thread.yield();
                        continue;
                    } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY
                            && fileName.toString().equals(file.getName())) {
                        // TODO : reload database and swith database
                        System.out.println("File has been changed!");
                    }
                }
            }
        } catch (Throwable e) {
            // TODO: Log out message Ignore for now
        }
    }
}
