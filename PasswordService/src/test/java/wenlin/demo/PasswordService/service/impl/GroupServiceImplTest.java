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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("test")
class GroupServiceImplTest {

    private GroupServiceImpl groupServiceImpl;
    private List<String> members;

    @BeforeEach
    @Transactional
    public void setUp() {
        members = new ArrayList<String>() {{
            add("A");
            add("B");
        }};
        SystemGroup root = new SystemGroup("root", 1, members);
        SystemGroup docker = new SystemGroup("docker", 2, members);
        groupServiceImpl.save(root);
        groupServiceImpl.save(docker);
    }

    @Test
    void whenFindByGidOne_ReturnGroupWithGidOne() {
        SystemGroup firstGroup = groupServiceImpl.findByGid(1);
        assertEquals(1, firstGroup.getGid());
    }

    @Test
    void whenFindByGidTwo_ReturnGroupWithGidTwo() {
        SystemGroup secondGroup = groupServiceImpl.findByGid(2);
        assertEquals(2, secondGroup.getGid());
    }

    @Test
    void whenFindByGidNotExist_ReturnNull() {
        SystemGroup notExist = groupServiceImpl.findByGid(0);
        assertNull(notExist);
    }

    @Test
    void whenFindAll_ReturnAllGroups() {
        List<SystemGroup> groups = groupServiceImpl.findAll();
        assertEquals(3, groups.size());
    }

    @Test
    @Transactional
    void whenSave_ThenSaveInputAndReturnSavedGroup() {
        SystemGroup thirdGroup = new SystemGroup("kubernetes", 3, members);
        SystemGroup result = groupServiceImpl.save(thirdGroup);
        assertNotNull(result);
        assertEquals(thirdGroup.getGid(), result.getGid());
        assertEquals(3, groupServiceImpl.findAll().size());
    }

    @Test
    void whenFindGroupByCriteria_ReturnGroupDocker() {
        List<SystemGroup> groups = groupServiceImpl.findGroupByCriteria("docker", 2, members);
        assertEquals(1, groups.size());
        assertEquals("docker", groups.get(0).getName());
    }

    @Autowired
    public void setGroupServiceImpl(GroupServiceImpl groupServiceImpl) {
        this.groupServiceImpl = groupServiceImpl;
    }
}