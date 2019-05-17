package wenlin.demo.PasswordService.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import wenlin.demo.PasswordService.dataobject.SystemGroup;
import wenlin.demo.PasswordService.dataobject.SystemUser;
import wenlin.demo.PasswordService.dataobject.UserInGroups;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Rollback
class UserServiceImplTest {

    private UserServiceImpl userServiceImpl;
    private GroupServiceImpl groupServiceImpl;

    @BeforeEach
    public void setUp() {
        userServiceImpl.findAll();
        SystemUser root = new SystemUser.Builder()
                .setUid(1)
                .setGid(1)
                .setComment("root user")
                .setHome("/bin")
                .setName("root")
                .setShell("/bin/bash")
                .build();

        SystemUser docker = new SystemUser.Builder()
                .setUid(2)
                .setGid(2)
                .setComment("docker user")
                .setHome("/bin")
                .setName("docker")
                .setShell("/bin/bash")
                .build();

        userServiceImpl.save(root);
        userServiceImpl.save(docker);
    }

    @Test
    void whenFindByUid_ReturnUserWithExpectedUid() {
        SystemUser firstUser = userServiceImpl.findByUid(1);
        SystemUser secondUser = userServiceImpl.findByUid(2);
        assertEquals(1, firstUser.getUid());
        assertEquals(2, secondUser.getUid());
    }

    @Test
    void whenFindByUidNotExist_ReturnNull() {
        SystemUser nullUser = userServiceImpl.findByUid(-1);
        assertNull(nullUser);
    }

    @Test
    void whenFindAll_ReturnAllUsers() {
        List<SystemUser> users = userServiceImpl.findAll();
        assertEquals(3, users.size());
    }

    @Test
    @Transactional
    void whenSave_ThenSaveInputAndReturnSavedUser() {
        SystemUser kubernetes = new SystemUser.Builder()
                .setUid(3)
                .setGid(3)
                .setComment("k8s user")
                .setHome("/bin")
                .setName("kubernetes")
                .setShell("/bin/bash")
                .build();

        SystemUser saved = userServiceImpl.save(kubernetes);
        assertNotNull(saved);
        assertEquals(3, saved.getUid());
        assertEquals(3, userServiceImpl.findAll().size());
    }

    @Test
    void whenFindByAllDockerFields_ReturnUserDocker() {
        List<SystemUser> result = userServiceImpl.findByFields("docker", 2, 2,
                "docker user", "/bin", "/bin/bash");
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getUid());
    }

    @Test
    void whenFindByNullFields_ReturnAllUsers() {
        List<SystemUser> result = userServiceImpl.findByFields(null, null, null,
                null, null, null);
        assertEquals(3, result.size());
    }

    @Test
    @Transactional
    void findUserGroups() {
        SystemGroup docker = new SystemGroup("docker", 2,
                Arrays.asList(new String[]{"docker", "k8s"}));
        SystemGroup saveDocker = groupServiceImpl.save(docker);
        assertNotNull(saveDocker);
        SystemGroup root = new SystemGroup("root", 1,
                Arrays.asList(new String[]{"root", "docker"}));
        SystemGroup saveRoot = groupServiceImpl.save(root);
        assertNotNull(saveRoot);

        List<UserInGroups> userInGroups = userServiceImpl.findUserGroups("docker");
        assertNotNull(userInGroups);
        assertEquals(2, userInGroups.get(0).getGroupMembers().size());
    }

    @Autowired
    public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Autowired
    public void setGroupServiceImpl(GroupServiceImpl groupServiceImpl) {
        this.groupServiceImpl = groupServiceImpl;
    }

}