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
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        Util.logInWithUser(driver, "automation35", "CCAutoTest19.");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement userProfile = driver.findElement(By.id("header-details-user-fullname"));
        String loggedInUsername = userProfile.getAttribute("data-username");

        assertEquals("automation35", loggedInUsername);
        Util.logOut(driver);
    }

    @Test
    public void testIncorrectUsername() {
        driver.get("https://jira-auto.codecool.metastage.net");

        Util.logInWithUser(driver, "username123", "CCAutoTest19.");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement alertMessageContainer = driver.findElement(By.id("usernameerror"));
        String alertMessage = alertMessageContainer.findElement(By.tagName("p")).getText();

        assertEquals("Sorry, your username and password are incorrect - please try again.", alertMessage);
    }


    @Test
    public void testIncorrectPassword() {
        driver.get("https://jira-auto.codecool.metastage.net");

        Util.logInWithUser(driver, "automation35", "password123");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement alertMessageContainer = driver.findElement(By.id("usernameerror"));
        String alertMessage = alertMessageContainer.findElement(By.tagName("p")).getText();

        assertEquals("Sorry, your username and password are incorrect - please try again.", alertMessage);
        Util.logInWithUser(driver, "automation35", "CCAutoTest19.");
        Util.logOut(driver);
    }


    @Test
    public void testEmptyFields() {
        driver.get("https://jira-auto.codecool.metastage.net");

        Util.logInWithUser(driver, "", "");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement alertMessageContainer = driver.findElement(By.id("usernameerror"));
        String alertMessage = alertMessageContainer.findElement(By.tagName("p")).getText();

        assertEquals("Sorry, your username and password are incorrect - please try again.", alertMessage);
    }


    @Test
    public void testEmptyPassword() {
        driver.get("https://jira-auto.codecool.metastage.net");

        Util.logInWithUser(driver, "automation36", "");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement alertMessageContainer = driver.findElement(By.id("usernameerror"));
        String alertMessage = alertMessageContainer.findElement(By.tagName("p")).getText();

        assertEquals("Sorry, your username and password are incorrect - please try again.", alertMessage);
        Util.logInWithUser(driver, "automation36", "CCAutoTest19.");
        Util.logOut(driver);
    }


    @Test
    public void testLogInWithCaptcha() {
        driver.get("https://jira-auto.codecool.metastage.net");

        for (int i=0; i<3; i++) {
            driver.navigate().refresh();
            Util.logInWithUser(driver, "automation39", "password123");
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement alertMessageContainer = driver.findElement(By.id("usernameerror"));
        String alertMessage = alertMessageContainer.findElement(By.tagName("p")).getText();

        assertEquals("Sorry, your userid is required to answer a CAPTCHA question correctly.", alertMessage);

        WebElement captchaInputField = driver.findElement(By.id("login-form-captcha"));
        assertTrue(captchaInputField.isDisplayed());
    }


    @Test
    public void testLogOut() {
        driver.get("https://jira-auto.codecool.metastage.net");

        Util.logInWithUser(driver, "automation35", "CCAutoTest19.");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        Util.logOut(driver);

        WebElement logOutMessage = driver.findElement(By.className("title"));
        assertEquals("You are now logged out. Any automatic login has also been stopped.", logOutMessage.getText());
    }
}
