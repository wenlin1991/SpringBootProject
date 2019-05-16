package wenlin.demo.PasswordService.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import wenlin.demo.PasswordService.dataobject.SystemGroup;
import wenlin.demo.PasswordService.dataobject.SystemUser;
import wenlin.demo.PasswordService.service.impl.GroupServiceImpl;
import wenlin.demo.PasswordService.service.impl.UserServiceImpl;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private GroupServiceImpl groupServiceImpl;

    @BeforeEach
    @Transactional
    public void setUp() {
        SystemUser firstUser = new SystemUser.Builder()
                .setName("root").setComment("root user")
                .setUid(1).setGid(0)
                .setHome("/root/bin").setShell("/root/bash")
                .build();
        SystemUser secondUser = new SystemUser.Builder()
                .setName("docker").setComment("docker user")
                .setUid(2).setGid(1)
                .setHome("/docker/bin").setShell("/docker/bash")
                .build();
        SystemUser thirdUser = new SystemUser.Builder()
                .setName("kubernetes").setComment("k8s user")
                .setUid(3).setGid(2)
                .setHome("/k8s/bin").setShell("/k8s/bash")
                .build();
        userServiceImpl.save(firstUser);
        userServiceImpl.save(secondUser);
        userServiceImpl.save(thirdUser);

        List<String> members = Arrays.asList(new String[]{"docker", "root"});
        groupServiceImpl.save(new SystemGroup("centusers", 1, members));
        groupServiceImpl.save(new SystemGroup("nobody", 2, members));
    }

    @Test
    void whenGetByUidOne_ReturnRootBody() {
        userServiceImpl.findAll();
        webTestClient.get()
                .uri("/users/1")
                .exchange().expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("{\"name\":\"root\",\"uid\":1,\"gid\":0,\"comment\":\"root user\"," +
                        "\"home\":\"/root/bin\",\"shell\":\"/root/bash\"}");
    }

    @Test
    void whenGetByFullFields_ReturnDockerBody() {
        webTestClient.get()
                .uri("/users/query?name=docker&uid=2&gid=1&comment=docker user&" +
                        "home=/docker/bin&shell=/docker/bash")
                .exchange().expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("[{\"name\":\"docker\",\"uid\":2,\"gid\":1,\"comment\":\"docker user\"," +
                        "\"home\":\"/docker/bin\",\"shell\":\"/docker/bash\"}]");
    }

    @Test
    void whenGetByPartOfFields_ReturnDockerBody() {
        webTestClient.get()
                .uri("/users/query?name=docker&uid=2&gid=1&comment=docker user&")
                .exchange().expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("[{\"name\":\"docker\",\"uid\":2,\"gid\":1,\"comment\":\"docker user\"," +
                        "\"home\":\"/docker/bin\",\"shell\":\"/docker/bash\"}]");
    }

    @Test
    void whenGetUserGroups_ReturnUserInGroupsList() {
        webTestClient.get()
                .uri("/users/2/groups")
                .exchange().expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("[{\"name\":\"docker\",\"gid\":1,\"groupMembers\":[\"centusers\",\"nobody\"]}]");
    }
}