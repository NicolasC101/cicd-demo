package au.com.equifax.cicddemo.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.InetAddress;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ApiController.class)
public class ApiControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void homeReturnsEnvDetail() throws Exception {
        InetAddress fake = mock(InetAddress.class);
        when(fake.getHostName()).thenReturn("test-host");
        when(fake.getHostAddress()).thenReturn("10.0.0.2");
        try (MockedStatic<InetAddress> inet = mockStatic(InetAddress.class)) {
            inet.when(InetAddress::getLocalHost).thenReturn(fake);
            mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.hostname").value("test-host"))
                    .andExpect(jsonPath("$.ip").value("10.0.0.2"))
                    .andExpect(jsonPath("$.os").exists())
                    .andExpect(jsonPath("$.message").value("Taller Jenkins - HOLAS"));
        }
    }
}
