import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditIssueTests {

    public WebDriver driver;

    @BeforeAll
    public static void setDriver(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp(){
        driver = new ChromeDriver();
    }

    @AfterEach
    public void quit() {
        Util.logOut(driver);
        driver.quit();
    }

    public void preConditionLogIn(String username){
        driver.get("https://jira-auto.codecool.metastage.net");
        Util.logInWithUser(driver, username,"CCAutoTest19.");
        Util.lookUpWebElementWithWait(driver, "#header-details-user-fullname");
    }

    public void checkIfEditButtonDisplayed(String url, String userName){
        preConditionLogIn(userName);
        driver.navigate().to(url);
        WebElement editButton = Util.lookUpWebElementWithWait(driver, "#edit-issue");
        assertTrue(editButton.isDisplayed());
    }

    @Test
    public void editIssueTest() {
        preConditionLogIn("automation35");
        Actions actions = new Actions(driver);

        // Find and click on Create button on the menubar
        WebElement createIssueButton = Util.lookUpWebElementWithWait(driver, "#create_link");
        createIssueButton.click();

        // Wait for the fields of the create issue modal to be useable
        WebElement projectField = Util.lookUpWebElementWithWait(driver, "#project-field");
        projectField.click();
        projectField.sendKeys("Main Testing Project (MTP)" + Keys.ENTER);

        WebElement issueTypeField = Util.lookUpWebElementWithWait(driver, "#issuetype-field");
        issueTypeField.click();
        issueTypeField.sendKeys("Bug" + Keys.ENTER);

        WebElement summaryField = Util.lookUpWebElementWithWait(driver, "#summary");
        summaryField.sendKeys("Bug in main testing project");

        WebElement createButton = Util.lookUpWebElementWithWait(driver, "#create-issue-submit");
        createButton.click();

        // Find and click on the link in the confirmation modal
        WebElement createdIssueInfoModal = Util.lookUpWebElementWithWait(driver, "a[class='issue-created-key issue-link']");
        createdIssueInfoModal.click();

        // Find anc click on the edit issue button and perform the change of the summary text
        Util.lookUpWebElementWithWait(driver, "#edit-issue").click();

        WebElement summaryEditField = Util.lookUpWebElementWithWait(driver, "#summary");
        summaryEditField.click();
        summaryEditField.clear();
        summaryEditField.sendKeys("Big-sub");

        WebElement updateIssueButton = Util.lookUpWebElementWithWait(driver, "#edit-issue-submit");
        updateIssueButton.click();

        // Perform a page refresh
        driver.navigate().refresh();
        Alert alert = driver.switchTo().alert();
        alert.accept();

        // Check if the summary text remains the same after page refresh
        WebElement issueSummary = Util.lookUpWebElementWithWait(driver, "h1[id='summary-val']");

        assertEquals("Big-sub", issueSummary.getText());

        // Delete the created test issue
        WebElement moreButton = Util.lookUpWebElementWithWait(driver, "#opsbar-operations_more");
        actions.moveToElement(moreButton).click().build().perform();

        WebElement deleteButton = Util.lookUpWebElementWithWait(driver, Util.lookUpWebElementByRelativeLocator("#delete-issue", "a"));
        deleteButton.click();

        WebElement confirmDeleteButton = Util.lookUpWebElementWithWait(driver, "#delete-issue-submit");
        confirmDeleteButton.click();
    }

    @Test
    public void cancelIssueTest(){
        preConditionLogIn("automation35");
        driver.navigate().to("https://jira-auto.codecool.metastage.net/browse/MTP-2686?filter=-2");
        Util.lookUpWebElementWithWait(driver, "#edit-issue").click();
        WebElement summaryEditField = Util.lookUpWebElementWithWait(driver, "#summary");
        summaryEditField.click();
        summaryEditField.clear();
        summaryEditField.sendKeys("Edited-sub");
        WebElement updateIssueButton = Util.lookUpWebElementWithWait(driver, "button[class='aui-button aui-button-link cancel']");
        updateIssueButton.click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        WebElement summaryField = Util.lookUpWebElementWithWait(driver, "#summary-val");
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
