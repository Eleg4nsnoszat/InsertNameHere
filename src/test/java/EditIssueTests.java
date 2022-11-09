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
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    }

    @AfterEach
    public void quit(){
//        WebElement userProfile = driver.findElement(By.id("header-details-user-fullname"));
//        userProfile.click();
//        WebElement logOutOption = driver.findElement(By.id("log_out"));
//        logOutOption.click();
        driver.quit();
    }

    public void preConditionLogIn(String username){
        driver.get("https://jira-auto.codecool.metastage.net");
        performLogIn(username,"CCAutoTest19.");
        new WebDriverWait(driver, Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.id("header-details-user-fullname")));
    }

    public void checkIfEditButtonDisplayed(String url, String userName){
        preConditionLogIn(userName);
        driver.navigate().to(url);
        WebElement editButton = new WebDriverWait(driver, Duration.ofMillis(1000)).until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-issue")));
        assertTrue(editButton.isDisplayed());
    }


    @Test
    public void editIssueTest() {
        preConditionLogIn("automation35");
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
        summaryField.sendKeys("Bug in main testing project");

        WebElement createButton = new WebDriverWait(driver,Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("create-issue-submit")));
        createButton.click();

        // Find and click on the link in the confirmation modal
        WebElement createdIssueInfoModal = new WebDriverWait(driver,Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[class='issue-created-key issue-link']")));
        createdIssueInfoModal.click();

        // Find anc click on the edit issue button and perform the change of the summary text
        new WebDriverWait(driver, Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("edit-issue"))).click();

        WebElement summaryEditField = new WebDriverWait(driver,Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("summary")));
        summaryEditField.click();
        summaryEditField.clear();
        summaryEditField.sendKeys("Big-sub");

        WebElement updateIssueButton = new WebDriverWait(driver,Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("edit-issue-submit")));
        updateIssueButton.click();

        // Perform a page refresh
        driver.navigate().refresh();
        Alert alert = driver.switchTo().alert();
        alert.accept();

        // Check if the summary text remains the same after page refresh
        WebElement issueSummary = new WebDriverWait(driver, Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("h1[id='summary-val']")));

        assertEquals("Big-sub", issueSummary.getText());

        // Delete the created test issue
        WebElement moreButton = new WebDriverWait(driver, Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("opsbar-operations_more")));
        actions.moveToElement(moreButton).click().build().perform();
        By deleteOption = RelativeLocator.with(By.id("delete-issue")).below(By.tagName("a"));
        WebElement deleteButton = new WebDriverWait(driver, Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(deleteOption));
        deleteButton.click();
        WebElement confirmDeleteButton = new WebDriverWait(driver, Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("delete-issue-submit")));
        confirmDeleteButton.click();
    }

    @Test
    public void cancelIssueTest(){
        preConditionLogIn("automation35");
        driver.navigate().to("https://jira-auto.codecool.metastage.net/browse/MTP-2686?filter=-2");
        new WebDriverWait(driver, Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.id("edit-issue"))).click();
        WebElement summaryEditField = new WebDriverWait(driver,Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.id("summary")));
        summaryEditField.click();
        summaryEditField.clear();
        summaryEditField.sendKeys("Edited-sub");
        WebElement updateIssueButton = new WebDriverWait(driver,Duration.ofMillis(5000)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[class='aui-button aui-button-link cancel']")));
        updateIssueButton.click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        WebElement summaryField = new WebDriverWait(driver, Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.id("summary-val")));
        assertEquals("Big-sub", summaryField.getText());
    }

    @Test
    public void editCOALAIssueTest(){
        checkIfEditButtonDisplayed("https://jira-auto.codecool.metastage.net/projects/COALA/issues/COALA-180?filter=allissues","automation35");
    }

    @Test
    public void editTOUCANIssueTest(){
        checkIfEditButtonDisplayed("https://jira-auto.codecool.metastage.net/projects/TOUCAN/issues/TOUCAN-239?filter=allissues","automation35");
    }

    @Test
    public void editJETIIssueTest(){
        checkIfEditButtonDisplayed("https://jira-auto.codecool.metastage.net/projects/JETI/issues/JETI-123?filter=allissues","automation35");
    }
}
