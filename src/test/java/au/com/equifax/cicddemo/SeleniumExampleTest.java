package au.com.equifax.cicddemo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("SystemTest")
public class SeleniumExampleTest {
    private WebDriver driver;

    @Test
    public void testOs() {
        System.out.println("APP_URL :" + System.getenv("APP_URL"));
        driver.get(System.getenv("APP_URL"));
        String bodyText = driver.findElement(By.tagName("body")).getText();
        assertTrue(bodyText.contains("Linux"));
        assertFalse(bodyText.contains("Windows"));
    }

    @BeforeEach
    public void beforeTest() throws MalformedURLException, URISyntaxException {
        driver = new RemoteWebDriver(new URI("http://selenium:4444/wd/hub").toURL(), new ChromeOptions());
    }

    @AfterEach
    public void afterTest() {
        driver.quit();
    }
}
