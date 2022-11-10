import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
        Util.logOut(driver);
        driver.quit();
    }

    public void preConditionLogIn() {
        driver.get("https://jira-auto.codecool.metastage.net");
        Util.logInWithUser(driver, "automation39", "CCAutoTest19.");
        Util.lookUpWebElementWithWait(driver, "#header-details-user-fullname");
    }

    public String browseIssue(String projectName, String issueNumber){
        driver.navigate().to("https://jira-auto.codecool.metastage.net/browse/"+projectName+"-"+issueNumber);
        Util.lookUpWebElementWithWait(driver, "#key-val");
        WebElement issueId = driver.findElement(By.id("key-val"));
        return issueId.getText();
    }

    public String browseProjectIssues(String projectName, String issueNumber){
        driver.navigate().to("https://jira-auto.codecool.metastage.net/projects/MTP/issues/"+projectName+"-"+issueNumber+"?filter=allissues");
        Util.lookUpWebElementWithWait(driver, "#key-val");
        WebElement issueId = driver.findElement(By.id("key-val"));
        return issueId.getText();
    }

    @Test
    public void browseExistingIssueTest(){
        assertEquals("MTP-1", browseProjectIssues("MTP","1"));
    }

    @Test
    public void browseNonExistingIssueTest(){
        assertNotEquals("MTP-0", browseProjectIssues("MTP","0"));
    }

    @Test
    public void browseTOUCANIssueTest() {
        assertEquals("TOUCAN-1", browseIssue("TOUCAN","1"));
        assertEquals("TOUCAN-2", browseIssue("TOUCAN","2"));
        assertEquals("TOUCAN-3", browseIssue("TOUCAN","3"));
    }

    @Test
    public void browseJETIIssueTest() {
        assertEquals("JETI-1", browseIssue("JETI","1"));
        assertEquals("JETI-2", browseIssue("JETI","2"));
        assertEquals("JETI-3", browseIssue("JETI","3"));
    }

    @Test
    public void browseCOALAIssueTest() {
        assertEquals("COALA-1", browseIssue("COALA","1"));
        assertEquals("COALA-2", browseIssue("COALA","2"));
        assertEquals("COALA-3", browseIssue("COALA","3"));
    }
}
