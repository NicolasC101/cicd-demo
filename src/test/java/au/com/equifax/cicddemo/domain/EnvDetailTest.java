package au.com.equifax.cicddemo.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnvDetailTest {

    @Test
    void accessorsRoundTrip() {
        EnvDetail env = new EnvDetail();
        env.setIp("1.2.3.4");
        env.setHostname("host");
        env.setOs("Linux");
        env.setMessage("msg");
        Assertions.assertEquals("1.2.3.4", env.getIp());
        Assertions.assertEquals("host", env.getHostname());
        Assertions.assertEquals("Linux", env.getOs());
        Assertions.assertEquals("msg", env.getMessage());
    }
}
