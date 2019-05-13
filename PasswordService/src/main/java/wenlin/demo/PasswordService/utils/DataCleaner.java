package wenlin.demo.PasswordService.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import wenlin.demo.PasswordService.dataobject.SystemGroup;
import wenlin.demo.PasswordService.dataobject.SystemUser;
import wenlin.demo.PasswordService.dataobject.UserInGroups;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DataCleaner cleaning data and convert read in data to
 * data object.
 *
 * @author wenlin
 */
@Component
public class DataCleaner {

    private static final String COMMENT_SIGN = "#";
    private static final String DELIMITER = ":";
    private static final int GROUP_FIELDS_LENGTH = 4;
    private static final int USER_FIELDS_LENGTH = 7;

    /**
     * isValidFile verify if the input file is valid or not.
     * @param fileName String
     * @return true if input file is valid
     * @throws IOException if input file is not readable or not a regular file.
     */
    public boolean isValidFile(String fileName) throws IOException {
        Path filePath = Paths.get(fileName);
        if (!Files.isRegularFile(filePath) || !Files.isReadable(filePath)) {
            throw new IOException("Cannot read input file!");
        }
        return true;
    }

    /**
     * isComment check if the line is comment or not, will skip comment
     * when load data into database.
     *
     * @param line String
     * @return true if line start with #; otherwise false
     */
    public boolean isComment(String line) {
        return line.startsWith(COMMENT_SIGN);
    }

    // TODO : Write error records into file
    /**
     * groupMapper map input line into a SystemGroup object
     *
     * @param line String
     * @return SystemGroup
     */
    public SystemGroup groupMapper(String line) {
        String[] fields = line.split(DELIMITER, -1);
        if (fields.length != GROUP_FIELDS_LENGTH) {
            return null;
        }
        List<String> members = StringUtils.isBlank(fields[3]) ?
                new ArrayList<>() : Arrays.asList(fields[3].split(DELIMITER));
        return new SystemGroup(fields[0], Integer.valueOf(fields[2]), members);
    }

    /**
     * userMapper map input line into a SystemUser object
     *
     * @param line String
     * @return SystemUser
     */
    public SystemUser userMapper(String line) {
        String[] fields = line.split(DELIMITER, -1);
        if (fields.length != USER_FIELDS_LENGTH) {
            return null;
        }
        SystemUser user = new SystemUser.Builder()
                .setName(fields[0])
                .setUid(Integer.valueOf(fields[2]))
                .setGid(Integer.valueOf(fields[3]))
                .setComment(fields[4])
                .setHome(fields[5])
                .setShell(fields[6])
                .build();
        return user;
    }

    /**
     * getUserGroups parse input to UserInGroups object
     *
     * @param input List<Object[]>
     * @return UserInGroups
     */
    public UserInGroups getUserGroups(List<Object[]> input) {
        if (input == null || input.size() == 0 || input.get(0).length == 0) {
            return null;
        }
        String name = (String) input.get(0)[0];
        int gid = (Integer) input.get(0)[1];
        List<String> groupMembers = new ArrayList<>();
        for (Object[] entry : input) {
            groupMembers.add((String) entry[2]);
        }

        return new UserInGroups(name, gid, groupMembers);
    }
}
