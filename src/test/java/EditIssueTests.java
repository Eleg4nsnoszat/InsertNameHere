import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditIssueTests {

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
//        WebElement userProfile = driver.findElement(By.id("header-details-user-fullname"));
//        userProfile.click();
//        WebElement logOutOption = driver.findElement(By.id("log_out"));
//        logOutOption.click();
        driver.quit();
    }

    public void preConditionLogIn(){
        driver.get("https://jira-auto.codecool.metastage.net");
        performLogIn("automation39","CCAutoTest19.");
        new WebDriverWait(driver, Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.id("header-details-user-fullname")));
    }

    @Test
    public void editIssueTest() {
        Actions actions = new Actions(driver);
        WebElement createIssueButton = new WebDriverWait(driver,Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.id("create_link")));
        createIssueButton.click();

        new WebDriverWait(driver, Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.id("summary")));

        WebElement projectField = new WebDriverWait(driver,Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.id("project-field")));
        projectField.click();
        projectField.sendKeys("Main Testing Project (MTP)" + Keys.ENTER);

        WebElement issueTypeField = new WebDriverWait(driver,Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.id("issuetype-field")));
        issueTypeField.click();
        issueTypeField.sendKeys("Bug" + Keys.ENTER);

        WebElement summaryField = new WebDriverWait(driver,Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.id("summary")));
        summaryField.sendKeys("Bug in main testing project");

        WebElement createButton = new WebDriverWait(driver,Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.id("create-issue-submit")));
        createButton.click();

        WebElement createdIssueInfoModal = new WebDriverWait(driver,Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[class='issue-created-key issue-link']")));
        createdIssueInfoModal.click();

        new WebDriverWait(driver, Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.id("edit-issue"))).click();

        new WebDriverWait(driver, Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.id("summary")));

        WebElement summaryEditField = new WebDriverWait(driver,Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.id("summary")));
        summaryEditField.click();
        summaryEditField.clear();
        summaryEditField.sendKeys("Big-sub");

        WebElement updateIssueButton = new WebDriverWait(driver,Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.id("edit-issue-submit")));
        updateIssueButton.click();

        driver.navigate().refresh();
        Alert alert = driver.switchTo().alert();
        alert.accept();

        WebElement issueSummary = new WebDriverWait(driver, Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("h1[id='summary-val']")));

        assertEquals("Big-sub", issueSummary.getText());

        WebElement moreButton = new WebDriverWait(driver, Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.id("opsbar-operations_more")));
        actions.moveToElement(moreButton).click().build().perform();
        By deleteOption = RelativeLocator.with(By.id("delete-issue")).below(By.tagName("a"));
        WebElement deleteButton = new WebDriverWait(driver, Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(deleteOption));
        deleteButton.click();
        WebElement confirmDeleteButton = new WebDriverWait(driver, Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.id("delete-issue-submit")));
        confirmDeleteButton.click();
    }
}
