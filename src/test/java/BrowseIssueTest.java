import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class BrowseIssueTest {

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
    public void setUp() {
        driver = new ChromeDriver();
        preConditionLogIn();

    }

    @AfterEach
    public void quit() {
        logOut();
        driver.quit();
    }

    public void preConditionLogIn() {
        driver.get("https://jira-auto.codecool.metastage.net");
        performLogIn("automation39", "CCAutoTest19.");
        new WebDriverWait(driver, Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.id("header-details-user-fullname")));
    }

    public void logOut() {
        WebElement userProfile = driver.findElement(By.id("header-details-user-fullname"));
        userProfile.click();
        WebElement logOutOption = driver.findElement(By.id("log_out"));
        logOutOption.click();
    }

    public String BrowseIssue(String projectName, String issueNumber){
        driver.navigate().to("https://jira-auto.codecool.metastage.net/browse/"+projectName+"-"+issueNumber);
        new WebDriverWait(driver,Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("key-val")));
        WebElement issueId = driver.findElement(By.id("key-val"));
        return issueId.getText();
    }

    public String BrowseProjectIssues(String projectName,String issueNumber){
        driver.navigate().to("https://jira-auto.codecool.metastage.net/projects/MTP/issues/"+projectName+"-"+issueNumber+"?filter=allissues");
        new WebDriverWait(driver,Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("key-val")));
        WebElement issueId = driver.findElement(By.id("key-val"));
        return issueId.getText();
    }

    @Test
    public void BrowseExistingIssueTest(){
        assertEquals("MTP-1",BrowseProjectIssues("MTP","1"));
    }

    @Test
    public void BrowseNonExistingIssueTest(){
        assertNotEquals("MTP-0",BrowseProjectIssues("MTP","0"));
    }

    @Test
    public void BrowseTOUCANIssueTest() {
        assertEquals("TOUCAN-1", BrowseIssue("TOUCAN","1"));
        assertEquals("TOUCAN-2",BrowseIssue("TOUCAN","2"));
        assertEquals("TOUCAN-3",BrowseIssue("TOUCAN","3"));
    }

    @Test
    public void BrowseJETIIssueTest() {
        assertEquals("JETI-1", BrowseIssue("JETI","1"));
        assertEquals("JETI-2",BrowseIssue("JETI","2"));
        assertEquals("JETI-3",BrowseIssue("JETI","3"));
    }

    @Test
    public void BrowseCOALAIssueTest() {
        assertEquals("COALA-1", BrowseIssue("COALA","1"));
        assertEquals("COALA-2",BrowseIssue("COALA","2"));
        assertEquals("COALA-3",BrowseIssue("COALA","3"));
    }
}
