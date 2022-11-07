import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogInTests {

    public WebDriver driver;

    @BeforeAll
    public static void setDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
    }

    @AfterEach
    public void quit() {
        driver.quit();
    }

    @Test
    public void testSuccessfulLogIn() {
        driver.get("https://jira-auto.codecool.metastage.net");

        WebElement usernameField = driver.findElement(By.id("login-form-username"));
        WebElement passwordField = driver.findElement(By.id("login-form-password"));
        WebElement logInButton = driver.findElement(By.id("login"));

        usernameField.sendKeys("automation35");
        passwordField.sendKeys("CCAutoTest19.");
        logInButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement userProfile = driver.findElement(By.id("header-details-user-fullname"));
        String loggedInUsername = userProfile.getAttribute("data-username");

        assertEquals("automation35", loggedInUsername);
    }
}
