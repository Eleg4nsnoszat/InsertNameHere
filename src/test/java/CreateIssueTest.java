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

import static org.junit.jupiter.api.Assertions.*;


public class CreateIssueTest {

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
        driver.quit();
    }

    public void preConditionLogIn() {
        driver.get("https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa");
        performLogIn("automation35", "CCAutoTest19.");
        new WebDriverWait(driver, Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.id("header-details-user-fullname")));
    }


    public void createIssueBase(String projectName, String issueType, String issueSummary){
        Actions actions = new Actions(driver);

        // Find and click on Create button on the menubar
        WebElement createIssueButton = new WebDriverWait(driver,Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("create_link")));
        createIssueButton.click();

        // Wait for the fields of the create issue modal to be useable
        WebElement projectField = new WebDriverWait(driver,Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("project-field")));
        projectField.click();
        projectField.sendKeys(projectName + Keys.ENTER);

        WebElement issueTypeField = new WebDriverWait(driver,Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("issuetype-field")));
        issueTypeField.click();
        issueTypeField.sendKeys(issueType + Keys.ENTER);

        WebElement summaryField = new WebDriverWait(driver,Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("summary")));
        summaryField.sendKeys(issueSummary);

        WebElement createButton = new WebDriverWait(driver,Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("create-issue-submit")));
        createButton.click();

        // Find and click on the link in the confirmation modal
        WebElement createdIssueInfoModal = new WebDriverWait(driver,Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[class='issue-created-key issue-link']")));
        createdIssueInfoModal.click();

        // Validate that the issue is created
        WebElement issueSummaryTheSame = new WebDriverWait(driver, Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("h1[id='summary-val']")));
        assertEquals(issueSummary, issueSummaryTheSame.getText());

        // Delete the created test issue
        WebElement moreButton = new WebDriverWait(driver, Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("opsbar-operations_more")));
        actions.moveToElement(moreButton).click().build().perform();
        By deleteOption = RelativeLocator.with(By.id("delete-issue")).below(By.tagName("a"));
        WebElement deleteButton = new WebDriverWait(driver, Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(deleteOption));
        deleteButton.click();
        WebElement confirmDeleteButton = new WebDriverWait(driver, Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("delete-issue-submit")));
        confirmDeleteButton.click();
    }
    //MTP TEST
    @Test
    public void createIssueMTPBugTest(){
        createIssueBase("Main Testing Project (MTP)","Bug","MTP test issue summary");
    }

    @Test
    public void createIssueMTPTaskTest(){
        createIssueBase("Main Testing Project (MTP)","Task","MTP test issue summary");
    }

    @Test
    public void createIssueMTPStoryTest(){
        createIssueBase("Main Testing Project (MTP)","Story","MTP test issue summary");
    }
    //TOUCAN TEST
    @Test
    public void createIssueTOUCANBugTest(){
        createIssueBase("TOUCAN project (TOUCAN)","Bug","TOUCAN test issue summary");
    }

    @Test
    public void createIssueTOUCANTaskTest(){
        createIssueBase("TOUCAN project (TOUCAN)","Task","TOUCAN test issue summary");
    }

    @Test
    public void createIssueTOUCANStoryTest(){
        createIssueBase("TOUCAN project (TOUCAN)","Story","TOUCAN test issue summary");
    }
    // JETI TEST
    @Test
    public void createIssueJETIBugTest(){
        createIssueBase("JETI project (JETI)","Bug","JETI test issue summary");
    }

    @Test
    public void createIssueJETITaskTest(){
        createIssueBase("JETI project (JETI)","Task","JETI test issue summary");
    }

    @Test
    public void createIssueJETIStoryTest(){
        createIssueBase("JETI project (JETI)","Story","JETI test issue summary");
    }

    //COALA TEST

    @Test
    public void createIssueCOALABugTest(){
        createIssueBase("COALA project (COALA)","Bug","COALA test issue summary");
    }

    @Test
    public void createIssueCOALATaskTest(){
        createIssueBase("COALA project (COALA)","Task","COALA test issue summary");
    }

    @Test
    public void createIssueCOALAStoryTest(){
        createIssueBase("COALA project (COALA)","Story","COALA test issue summary");
    }

    @Test
    public void cancelIssueTest(){
        Actions actions = new Actions(driver);

        // Find and click on Create button on the menubar
        WebElement createIssueButton = new WebDriverWait(driver,Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("create_link")));
        createIssueButton.click();

        // Wait for the fields of the create issue modal to be useable
        WebElement projectField = new WebDriverWait(driver,Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("project-field")));
        projectField.click();
        projectField.sendKeys("Main Testing Project (MTP)" + Keys.ENTER);

        WebElement issueTypeField = new WebDriverWait(driver,Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("issuetype-field")));
        issueTypeField.click();
        issueTypeField.sendKeys("Bug" + Keys.ENTER);

        WebElement summaryField = new WebDriverWait(driver,Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("summary")));
        summaryField.sendKeys("MTP test issue summary");

        // Find and click cancel button
        WebElement cancelButton = new WebDriverWait(driver, Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[class='aui-button aui-button-link cancel']")));
        cancelButton.click();

        // Handling Alert modal
        Alert alert = driver.switchTo().alert();
        alert.accept();

        //Validate that no new issue is created
        WebElement issueButton = new WebDriverWait(driver,Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("find_link")));
        issueButton.click();
        WebElement myOpenIssueButton = new WebDriverWait(driver, Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("filter_lnk_reported_lnk")));
        myOpenIssueButton.click();
        WebElement summaryFieldCheck = new WebDriverWait(driver,Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("summary-val")));
        assertTrue(summaryFieldCheck.isDisplayed(), "MTP test issue summary");
    }
}
