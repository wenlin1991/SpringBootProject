package wenlin.demo.PasswordService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import wenlin.demo.PasswordService.reader.GroupReader;
import wenlin.demo.PasswordService.reader.UserReader;

import java.io.IOException;

/**
 * DataLoader load passwd and group file into database by calling
 * GroupReader and UserReader.
 *
 * @author wenlin
 */
@Service
@Profile("!test")
public class DataLoader implements ApplicationRunner {

    private GroupReader groupReader;
    private UserReader userReader;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        load();
    }

    /**
     * load load data into database for SystemGroup and SystemUser
     * @throws IOException if any input file is not valid
     */
    public void load() throws IOException {
        groupReader.readAndInsertGroups();
        userReader.readAndInsertUsers();
    }

    @Autowired
    public void setGroupReader(GroupReader groupReader) {
        this.groupReader = groupReader;
    }

    @Autowired
    public void setUserReader(UserReader userReader) {
        this.userReader = userReader;
    }

}
