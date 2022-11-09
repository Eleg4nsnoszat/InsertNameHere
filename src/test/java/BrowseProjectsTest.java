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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


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
        Util.logOut(driver);
        driver.quit();
    }

    public void preConditionLogIn(){
        driver.get("https://jira-auto.codecool.metastage.net");
        performLogIn("automation39","CCAutoTest19.");
        Util.lookUpWebElementWithWait(driver, "#header-details-user-fullname");
    }

    private void checkProjectSummaryPage(String projectName) {
        driver.navigate().to("https://jira-auto.codecool.metastage.net/projects/" + projectName + "/summary");
        new WebDriverWait(driver,Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"summary-body\"]/div/div[2]/dl/dd[2]")));
        WebElement projectKey = driver.findElement(By.xpath("//*[@id=\"summary-body\"]/div/div[2]/dl/dd[2]"));
        assertEquals(projectName, projectKey.getText());
    }


    @Test
    public void browseMTPProjectTest(){
        driver.navigate().to("https://jira-auto.codecool.metastage.net/projects/MTP/summary");
        List<WebElement> projectMetaData = driver.findElements(By.className("project-meta-value"));
        String projectName = projectMetaData.get(1).getText();
        assertEquals("MTP", projectName);
    }

    @Test
    public void browseNonExistingProjectTest(){
        driver.navigate().to("https://jira-auto.codecool.metastage.net/projects/DUCK/summary");
        WebElement nonExistingProjectPage = driver.findElement(By.xpath("//*[@id=\"main\"]/h1"));
        assertEquals("You can't view this project",nonExistingProjectPage.getText());
    }

    @Test
    public void checkCOALAProjectDetailsTest(){
        checkProjectSummaryPage("COALA");
    }


    @Test
    public void checkTOUCANProjectDetailsTest(){
        checkProjectSummaryPage("TOUCAN");
    }

    @Test
    public void checkJETIProjectDetailsTest(){
        checkProjectSummaryPage("JETI");
    }

}
