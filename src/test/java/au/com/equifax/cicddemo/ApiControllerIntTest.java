package au.com.equifax.cicddemo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = CicdDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("IntegrationTest")
public class ApiControllerIntTest {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void homeReturnsEnvDetailWithExpectedFields() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/",
                HttpMethod.GET,
                null,
                String.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        String body = response.getBody();
        Assertions.assertNotNull(body);
        JsonNode root = new ObjectMapper().readTree(body);
        Assertions.assertTrue(root.has("hostname"), () -> "unexpected body: " + body);
        Assertions.assertTrue(root.has("ip"), () -> "unexpected body: " + body);
        Assertions.assertTrue(root.has("os"), () -> "unexpected body: " + body);
        Assertions.assertTrue(root.has("message"), () -> "unexpected body: " + body);
        Assertions.assertFalse(root.path("message").asText().isEmpty());
    }
}
