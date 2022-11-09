import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

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
        Util.logOut(driver);
        driver.quit();
    }

    public void preConditionLogIn() {
        driver.get("https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa");
        performLogIn("automation37", "CCAutoTest19.");
        Util.lookUpWebElementWithWait(driver, "#header-details-user-fullname");
    }


    public void createIssueBase(String projectName, String issueType, String issueSummary) {
        // Find and click on Create button on the menubar
        WebElement createIssueButton = Util.lookUpWebElementWithWait(driver, "#create_link");
        createIssueButton.click();

        // Wait for the fields of the create issue modal to be usable
        WebElement projectField = Util.lookUpWebElementWithWait(driver, "#project-field");
        projectField.click();
        projectField.sendKeys(projectName + Keys.ENTER);

        WebElement issueTypeField = Util.lookUpWebElementWithWait(driver, "#issuetype-field");
        issueTypeField.click();
        issueTypeField.sendKeys(issueType + Keys.ENTER);

        WebElement summaryField = Util.lookUpWebElementWithWait(driver, "#summary");
        summaryField.sendKeys(issueSummary);

        WebElement createButton = Util.lookUpWebElementWithWait(driver, "#create-issue-submit");
        createButton.click();

        // Find and click on the link in the confirmation modal
        WebElement createdIssueInfoModal = Util.lookUpWebElementWithWait(driver, "a[class='issue-created-key issue-link']");
        createdIssueInfoModal.click();
    }

    public void validateIssueBase(String issueSummary) {
        WebElement issueSummaryTheSame = Util.lookUpWebElementWithWait(driver, "h1[id='summary-val']");
        assertEquals(issueSummary, issueSummaryTheSame.getText());
    }
    public void deleteIssue() {
        Actions actions = new Actions(driver);
        WebElement moreButton = Util.lookUpWebElementWithWait(driver, "#opsbar-operations_more");
        actions.moveToElement(moreButton).click().build().perform();
        WebElement deleteButton = Util.lookUpWebElementWithWait(driver, Util.lookUpWebElementByRelativeLocator("#delete-issue", "a"));
        deleteButton.click();
        WebElement confirmDeleteButton = Util.lookUpWebElementWithWait(driver, "#delete-issue-submit");
        confirmDeleteButton.click();
    }

    public void createSubtask(String subtaskSummary){
        Actions actions = new Actions(driver);
        WebElement moreButton = Util.lookUpWebElementWithWait(driver, "#opsbar-operations_more");
        actions.moveToElement(moreButton).click().build().perform();
        WebElement subtaskButton = Util.lookUpWebElementWithWait(driver, Util.lookUpWebElementByRelativeLocator("#create-subtask", "a"));
        subtaskButton.click();
        WebElement summaryField = Util.lookUpWebElementWithWait(driver, "#summary");
        summaryField.sendKeys(subtaskSummary);

        WebElement createButton = Util.lookUpWebElementWithWait(driver, "#create-issue-submit");
        createButton.click();
    }
    public void validateSubtask(String subtaskSummary){
        WebElement subtaskLink = Util.lookUpWebElementWithWait(driver, Util.lookUpWebElementByRelativeLocator(".stsummary", "a"));
        assertEquals(subtaskSummary, subtaskLink.getText());
    }

    //MTP TEST
    @Test
    public void createIssueMTPBugTest(){
        createIssueBase("Main Testing Project (MTP)","Bug","MTP test issue summary");
        validateIssueBase("MTP test issue summary");
        deleteIssue();
    }


    @Test
    public void createIssueMTPTaskTest(){
        createIssueBase("Main Testing Project (MTP)","Task","MTP test issue summary");
        validateIssueBase("MTP test issue summary");
        deleteIssue();
    }

    @Test
    public void createIssueMTPStoryTest(){
        createIssueBase("Main Testing Project (MTP)","Story","MTP test issue summary");
        validateIssueBase("MTP test issue summary");
        deleteIssue();
    }

    @Test
    public void createIssueMTPSubtaskTest(){
        createIssueBase("Main Testing Project (MTP)","Task","MTP test issue summary");
        createSubtask("MTP test subtask summary");
        validateSubtask("MTP test subtask summary");
        deleteIssue();
    }
    //TOUCAN TEST
    @Test
    public void createIssueTOUCANBugTest(){
        createIssueBase("TOUCAN project (TOUCAN)","Bug","TOUCAN test issue summary");
        validateIssueBase("TOUCAN test issue summary");
        deleteIssue();
    }

    @Test
    public void createIssueTOUCANTaskTest(){
        createIssueBase("TOUCAN project (TOUCAN)","Task","TOUCAN test issue summary");
        validateIssueBase("TOUCAN test issue summary");
        deleteIssue();
    }

    @Test
    public void createIssueTOUCANStoryTest(){
        createIssueBase("TOUCAN project (TOUCAN)","Story","TOUCAN test issue summary");
        validateIssueBase("TOUCAN test issue summary");
        deleteIssue();
    }

    @Test
    public void createIssueTOUCANSubtaskTest(){
        createIssueBase("TOUCAN project (TOUCAN)","Task","TOUCAN test issue summary");
        createSubtask("TOUCAN test subtask summary");
        validateSubtask("TOUCAN test subtask summary");
        deleteIssue();
    }
    // JETI TEST
    @Test
    public void createIssueJETIBugTest(){
        createIssueBase("JETI project (JETI)","Bug","JETI test issue summary");
        validateIssueBase("JETI test issue summary");
        deleteIssue();
    }

    @Test
    public void createIssueJETITaskTest(){
        createIssueBase("JETI project (JETI)","Task","JETI test issue summary");
        validateIssueBase("JETI test issue summary");
        deleteIssue();
    }

    @Test
    public void createIssueJETIStoryTest(){
        createIssueBase("JETI project (JETI)","Story","JETI test issue summary");
        validateIssueBase("JETI test issue summary");
        deleteIssue();
    }

    @Test
    public void createIssueJETISubtaskTest(){
        createIssueBase("JETI project (JETI)","Task","JETI test issue summary");
        createSubtask("JETI test subtask summary");
        validateSubtask("JETI test subtask summary");
        deleteIssue();
    }

    //COALA TEST

    @Test
    public void createIssueCOALABugTest(){
        createIssueBase("COALA project (COALA)","Bug","COALA test issue summary");
        validateIssueBase("COALA test issue summary");
        deleteIssue();
    }

    @Test
    public void createIssueCOALATaskTest(){
        createIssueBase("COALA project (COALA)","Task","COALA test issue summary");
        validateIssueBase("COALA test issue summary");
        deleteIssue();
    }


    @Test
    public void createIssueCOALAStoryTest(){
        createIssueBase("COALA project (COALA)","Story","COALA test issue summary");
        validateIssueBase("COALA test issue summary");
        deleteIssue();
    }

    @Test
    public void createIssueCOALASubtaskTest(){
        createIssueBase("COALA project (COALA)","Task","COALA test issue summary");
        createSubtask("COALA test subtask summary");
        validateSubtask("COALA test subtask summary");
        deleteIssue();
    }

    @Test
    public void cancelIssueTest(){
        // Find and click on Create button on the menubar
        WebElement createIssueButton = Util.lookUpWebElementWithWait(driver, "#create_link");
        createIssueButton.click();

        // Wait for the fields of the create issue modal to be usable
        WebElement projectField = Util.lookUpWebElementWithWait(driver, "#project-field");
        projectField.click();
        projectField.sendKeys("Main Testing Project (MTP)" + Keys.ENTER);

        WebElement issueTypeField = Util.lookUpWebElementWithWait(driver, "#issuetype-field");
        issueTypeField.click();
        issueTypeField.sendKeys("Bug" + Keys.ENTER);

        WebElement summaryField = Util.lookUpWebElementWithWait(driver, "#summary");
        summaryField.sendKeys("MTP test issue summary");

        // Find and click cancel button
        WebElement cancelButton = Util.lookUpWebElementWithWait(driver, "button[class='aui-button aui-button-link cancel']");
        cancelButton.click();

        // Handling Alert modal
        Alert alert = driver.switchTo().alert();
        alert.accept();

        //Validate that no new issue is created
        WebElement issueButton = Util.lookUpWebElementWithWait(driver, "#find_link");
        issueButton.click();
        WebElement myOpenIssueButton = Util.lookUpWebElementWithWait(driver, "#filter_lnk_reported_lnk");
        myOpenIssueButton.click();
        WebElement summaryFieldCheck = Util.lookUpWebElementWithWait(driver, "#summary-val");
        assertTrue(summaryFieldCheck.isDisplayed(), "MTP test issue summary");
    }
}
