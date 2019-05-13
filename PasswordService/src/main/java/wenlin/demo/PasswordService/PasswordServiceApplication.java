package wenlin.demo.PasswordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import wenlin.demo.PasswordService.service.DataLoader;

@SpringBootApplication
public class PasswordServiceApplication implements ApplicationRunner {

    private DataLoader dataLoader;

    public static void main(String[] args) {
        SpringApplication.run(PasswordServiceApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        dataLoader.load();
    }

    @Autowired
    public void setDataLoader(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }
}
