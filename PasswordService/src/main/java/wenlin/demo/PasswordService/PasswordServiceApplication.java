package wenlin.demo.PasswordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import wenlin.demo.PasswordService.service.FileWatcher;

import java.nio.file.WatchService;
import java.sql.SQLOutput;

@SpringBootApplication
public class PasswordServiceApplication implements ApplicationRunner {

    @Autowired
    public FileWatcher fileWatcher;

    public static void main(String[] args) {
        SpringApplication.run(PasswordServiceApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) {
        System.out.println("Watcher is running");
        new Thread(fileWatcher).start();
    }
}

