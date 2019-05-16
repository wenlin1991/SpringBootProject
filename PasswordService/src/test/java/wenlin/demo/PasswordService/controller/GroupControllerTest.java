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
import wenlin.demo.PasswordService.service.impl.GroupServiceImpl;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class GroupControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private GroupServiceImpl groupServiceImpl;

    @BeforeEach
    @Transactional
    public void setUp() {
        List<String> members = Arrays.asList(new String[]{"A", "B"});
        groupServiceImpl.save(new SystemGroup("root", 1, members));
        groupServiceImpl.save(new SystemGroup("docker", 2, members));

        List<String> member = Arrays.asList(new String[]{"A"});
        groupServiceImpl.save(new SystemGroup("k8s", 3, member));
    }

    @Test
    void whenGetByGidOne_ReturnRootBody() {
        webTestClient.get()
                .uri("/groups/3")
                .exchange().expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("{\"name\":\"k8s\",\"gid\":3,\"members\":[\"A\"]}");
    }

    @Test
    void whenGetByNameAndGid_ReturnDockerBody() {
        webTestClient.get()
                .uri("/groups/query?name=docker&gid=2")
                .exchange().expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("[{\"name\":\"docker\",\"gid\":2,\"members\":[\"A\",\"B\"]}]");
    }

    @Test
    void whenGetByNameGidAndMultipleMembers_ReturnRootBody() {
        webTestClient.get()
                .uri("groups/query?name=root&gid=1&member=A&member=B")
                .exchange().expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("[{\"name\":\"root\",\"gid\":1,\"members\":[\"A\",\"B\"]}]");
    }

    @Test
    void whenGetAll_ReturnAllGroups() {
        webTestClient.get()
                .uri("/groups")
                .exchange().expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("[{\"name\":\"root\",\"gid\":1,\"members\":[\"A\",\"B\"]}," +
                        "{\"name\":\"docker\",\"gid\":2,\"members\":[\"A\",\"B\"]}," +
                        "{\"name\":\"k8s\",\"gid\":3,\"members\":[\"A\"]}]");
    }
}