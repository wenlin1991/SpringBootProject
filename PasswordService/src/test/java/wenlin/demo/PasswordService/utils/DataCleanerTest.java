package wenlin.demo.PasswordService.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wenlin.demo.PasswordService.dataobject.SystemGroup;
import wenlin.demo.PasswordService.dataobject.SystemUser;
import wenlin.demo.PasswordService.dataobject.UserInGroups;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class DataCleanerTest {

    private DataCleaner dataCleaner;

    @Test
    void whenFileisNotValid_returnFalse() {
        String fakeFile = "/I/am/fake/file";
        Assertions.assertThrows(IOException.class,
                () -> {
                    dataCleaner.isValidFile(fakeFile);
                });
    }

    @Test
    void whenFileIsValid_returnTrue() throws IOException {
        String validFile = "src/main/resources/static/validFileForTest";
        assertTrue(dataCleaner.isValidFile(validFile));
    }

    @Test
    void whenInputIsComment_returnTrue() {
        String commentLine = "# I am a comment line.";
        assertTrue(dataCleaner.isComment(commentLine));
    }

    @Test
    void whenInputIsNotComment_returnFalse() {
        String regularLine = "I am not comment.";
        assertFalse(dataCleaner.isComment(regularLine));
    }

    @Test
    void whenInputValid_MapInputToSystemGroup() {
        String input = "nobody::-100:";
        SystemGroup group = dataCleaner.groupMapper(input);
        assertEquals("nobody", group.getName());
        assertEquals(-100, group.getGid());
        assertTrue(group.getMembers().isEmpty());
    }

    @Test
    void whenInputValid_MapInputToNullSystemGroup() {
        String input = "nobody::-100::";
        SystemGroup group = dataCleaner.groupMapper(input);
        assertNull(group);
    }

    @Test
    void whenInputValid_MapInputToSystemUser() {
        String input = "nobody:*:-100:100:Unprivileged User:/any/empty:/any/bin/you/like";
        SystemUser user = dataCleaner.userMapper(input);
        assertEquals(-100, user.getUid());
        assertEquals(100, user.getGid());
        assertEquals("nobody", user.getName());
        assertEquals("Unprivileged User", user.getComment());
        assertEquals("/any/empty", user.getHome());
        assertEquals("/any/bin/you/like", user.getShell());
    }

    @Test
    void whenInputValid_MapInputToNullSystemUser() {
        String input = "-100:100:Unprivileged User:/any/empty:/any/bin/you/like";
        SystemUser user = dataCleaner.userMapper(input);
        assertNull(user);
    }

    // TODO : fix test
    @Test
    void getUserGroups() {
        List<Object[]> input = new ArrayList<>();
    }

    @Autowired
    public void setDataCleaner(DataCleaner dataCleaner) {
        this.dataCleaner = dataCleaner;
    }
}