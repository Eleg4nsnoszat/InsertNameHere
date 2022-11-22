package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.Util;

public class CreateIssuePage {

    WebDriver driver;

    @FindBy(xpath = "//*[@id='create_link']")
    WebElement createButton;

    @FindBy(xpath = "//*[@id='project-field']")
    WebElement projectField;

    @FindBy(xpath = "//*[@id='issuetype-field']")
    WebElement issueTypeField;

    @FindBy(xpath = "//*[@id='summary']")
    WebElement summaryField;

    @FindBy(xpath = "//*[@id='create-issue-submit']")
    WebElement createIssueSubmitButton;

    @FindBy(xpath = "//a[@class='issue-created-key issue-link']")
    WebElement createdIssueLink;




    public CreateIssuePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }




    private void clickOnCreateButton() {
        Util.lookUpWebElementWithWait(driver, createButton).click();
    }

    private void setProjectField(String projectName) {
        WebElement projectInput = Util.lookUpWebElementWithWait(driver, projectField);
        projectInput.click();
        projectInput.sendKeys(projectName + Keys.ENTER);
    }

    private void setIssueTypeField(String issueType) {
        WebElement issueTypeInput = Util.lookUpWebElementWithWait(driver, issueTypeField);
        issueTypeInput.click();
        issueTypeInput.sendKeys(issueType + Keys.ENTER);
    }

    private void setSummaryField(String issueSummary) {
        WebElement summaryInput = Util.lookUpWebElementWithWait(driver, summaryField);
        summaryInput.click();
        summaryInput.sendKeys(issueSummary);
    }

    private void clickOnSubmitIssue() {
        Util.lookUpWebElementWithWait(driver, createIssueSubmitButton).click();
    }




    public void createIssueBase(String projectName, String issueType, String issueSummary) {
        clickOnCreateButton();
        setProjectField(projectName);
        setIssueTypeField(issueType);
        setSummaryField(issueSummary);
        clickOnSubmitIssue();
    }

    public void clickOnCreatedIssueModalLink() {
        Util.lookUpWebElementWithWait(driver, createdIssueLink).click();
    }
}
