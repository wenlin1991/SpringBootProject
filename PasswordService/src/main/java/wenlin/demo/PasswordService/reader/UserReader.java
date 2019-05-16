package wenlin.demo.PasswordService.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wenlin.demo.PasswordService.dataobject.SystemUser;
import wenlin.demo.PasswordService.service.impl.UserServiceImpl;
import wenlin.demo.PasswordService.utils.DataCleaner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * UserReader read from input passwd file line by line and convert every valid
 * line into SystemUser object, then save object into database.
 *
 * @author wenlin
 */
@Component
public class UserReader {

    private static final Logger log = LoggerFactory.getLogger(UserReader.class);

    @Value("${data.userFile}")
    private String userFile;

    private UserServiceImpl userServiceImpl;

    private DataCleaner dataCleaner;

    /**
     * readAndInsertUsers read from input passwd file line by line. If a line is
     * valid, convert it to SystemUser object and save into database. Log out
     * error if line is not valid.
     *
     * @throws IOException if input file is not valid
     */
    public void readAndInsertUsers() throws IOException {
        log.info(String.format("Will read users from: %s", userFile));
        BufferedReader reader;
        if (dataCleaner.isValidFile(userFile)) {
            reader = new BufferedReader(new FileReader(userFile));
            String line = reader.readLine();
            while (line != null) {
                if (!dataCleaner.isComment(line)) {
                    SystemUser user = dataCleaner.userMapper(line);
                    if (user != null) {
                        userServiceImpl.save(user);
                    } else {
                        log.error("Fail to insert user!");
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        }
    }

    @Autowired
    public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Autowired
    public void setDataCleaner(DataCleaner dataCleaner) {
        this.dataCleaner = dataCleaner;
    }
}
