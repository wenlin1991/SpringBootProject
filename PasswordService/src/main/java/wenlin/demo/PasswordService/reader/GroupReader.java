package wenlin.demo.PasswordService.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wenlin.demo.PasswordService.dataobject.SystemGroup;
import wenlin.demo.PasswordService.service.impl.GroupServiceImpl;
import wenlin.demo.PasswordService.utils.DataCleaner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * GroupReader read from input group file line by line and save object into
 * database. Log out error if line cannot be parsed into SystemGroup object.
 *
 * @author wenlin
 */
@Component
public class GroupReader {

    private static final Logger log = LoggerFactory.getLogger(GroupReader.class);

    @Value("${data.groupFile}")
    private String groupFile;

    private GroupServiceImpl groupServiceImpl;

    private DataCleaner dataCleaner;

    /**
     * readAndInsertGroups read in from input group file line by line, convert line
     * to SystemGroup object if line is valid and save into database.
     *
     * @throws IOException if input file is not valid
     */
    public void readAndInsertGroups() throws IOException {
        log.info(String.format("Will read groups from: %s", groupFile));
        BufferedReader reader;
        if (dataCleaner.isValidFile(groupFile)) {
            reader = new BufferedReader(new FileReader(groupFile));
            String line = reader.readLine();
            while (line != null) {
                if (!dataCleaner.isComment(line)) {
                    SystemGroup group = dataCleaner.groupMapper(line);
                    if (group != null) {
                        groupServiceImpl.save(group);
                    } else {
                        // TODO : Make log better, try to get line number
                        log.error("Fail to insert data!");
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        }
    }

    @Autowired
    public void setGroupServiceImpl(GroupServiceImpl groupServiceImpl) {
        this.groupServiceImpl = groupServiceImpl;
    }

    @Autowired
    public void setDataCleaner(DataCleaner dataCleaner) {
        this.dataCleaner = dataCleaner;
    }
}