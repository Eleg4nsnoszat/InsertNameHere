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

    public void performLogIn(String username, String password) {

        WebElement usernameField = driver.findElement(By.id("login-form-username"));
        WebElement passwordField = driver.findElement(By.id("login-form-password"));
        WebElement logInButton = driver.findElement(By.id("login"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        logInButton.click();
    }

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

        performLogIn("automation35", "CCAutoTest19.");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement userProfile = driver.findElement(By.id("header-details-user-fullname"));
        String loggedInUsername = userProfile.getAttribute("data-username");

        assertEquals("automation35", loggedInUsername);
    }

    @Test
    public void testIncorrectUsername() {
        driver.get("https://jira-auto.codecool.metastage.net");

        performLogIn("username123", "CCAutoTest19.");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement alertMessageContainer = driver.findElement(By.id("usernameerror"));
        String alertMessage = alertMessageContainer.findElement(By.tagName("p")).getText();

        assertEquals("Sorry, your username and password are incorrect - please try again.", alertMessage);
    }


    @Test
    public void testIncorrectPassword() {
        driver.get("https://jira-auto.codecool.metastage.net");

        performLogIn("automation35", "password123");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement alertMessageContainer = driver.findElement(By.id("usernameerror"));
        String alertMessage = alertMessageContainer.findElement(By.tagName("p")).getText();

        assertEquals("Sorry, your username and password are incorrect - please try again.", alertMessage);
        performLogIn("automation35", "CCAutoTest19.");
    }


    @Test
    public void testEmptyFields() {
        driver.get("https://jira-auto.codecool.metastage.net");

        performLogIn("", "");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement alertMessageContainer = driver.findElement(By.id("usernameerror"));
        String alertMessage = alertMessageContainer.findElement(By.tagName("p")).getText();

        assertEquals("Sorry, your username and password are incorrect - please try again.", alertMessage);
    }

}
