package au.com.equifax.cicddemo;

import au.com.equifax.cicddemo.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = CicdDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("IntegrationTest")
public class UserControllerIntTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    void testRetrieveStudentCourse() throws JsonProcessingException {

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/users"),
                HttpMethod.GET, entity, String.class);

        String body = response.getBody();
        Assertions.assertNotNull(body);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(body);
        int userCount;
        if (root.isArray()) {
            userCount = root.size();
        } else if (root.path("_embedded").path("users").isArray()) {
            userCount = root.path("_embedded").path("users").size();
        } else if (root.path("_embedded").path("user").isArray()) {
            userCount = root.path("_embedded").path("user").size();
        } else if (root.path("content").isArray()) {
            userCount = root.path("content").size();
        } else {
            userCount = root.isEmpty() || (root.size() == 0) ? 0 : -1;
        }
        Assertions.assertEquals(0, userCount, () -> "unexpected body: " + body);

        User usr = User.UserBuilder.anUser()
                .withEmail("another@gmail.com")
                .withLogin("another")
                .withId(10)
                .withName("another").build();
        HttpEntity<User> entityUsr = new HttpEntity<>(usr, headers);

        restTemplate.exchange(
                createURLWithPort("/users"),
                HttpMethod.POST, entityUsr, User.class);

        ResponseEntity<User> responseUsr = restTemplate.exchange(
                createURLWithPort("/users/" + usr.getId()),
                HttpMethod.GET, entityUsr, User.class);
        Assertions.assertEquals(usr, responseUsr.getBody());

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
