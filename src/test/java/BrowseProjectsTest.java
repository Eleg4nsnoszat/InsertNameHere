import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class BrowseProjectsTest {

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
    public static void setDriver(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp(){
        driver = new ChromeDriver();
        preConditionLogIn();

    }

    @AfterEach
    public void quit(){
        WebElement userProfile = driver.findElement(By.id("header-details-user-fullname"));
        userProfile.click();
        WebElement logOutOption = driver.findElement(By.id("log_out"));
        logOutOption.click();
        driver.quit();
    }

    public void preConditionLogIn(){
        driver.get("https://jira-auto.codecool.metastage.net");
        performLogIn("automation39","CCAutoTest19.");
        new WebDriverWait(driver, Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.id("header-details-user-fullname")));
    }


    @Test
    public void BrowseMTPProjectTest(){
        driver.navigate().to("https://jira-auto.codecool.metastage.net/projects/MTP/summary");
        WebElement isLoaded =  new WebDriverWait(driver,Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"summary-body\"]/div/div[2]/dl/dd[3]")));
        String mtpText = isLoaded.getText();
        assertEquals("MTP", mtpText);
    }
}
