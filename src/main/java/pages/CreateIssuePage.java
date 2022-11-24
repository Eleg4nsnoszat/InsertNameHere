package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.Util;

public class CreateIssuePage {

    WebDriver driver;
    DashboardPage dashboardPage;
    IssuePage issuePage;


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

    @FindBy(xpath = "//*[@class='aui-button aui-button-link cancel']")
    WebElement cancelButton;




    public CreateIssuePage(WebDriver driver) {
        this.driver = driver;
        dashboardPage = new DashboardPage(driver);
        issuePage = new IssuePage(driver);
        PageFactory.initElements(driver, this);
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
        dashboardPage.clickOnCreateButton();
        setProjectField(projectName);
        setIssueTypeField(issueType);
        setSummaryField(issueSummary);
        clickOnSubmitIssue();
    }

    public void clickOnCreatedIssueModalLink() {
        Util.lookUpWebElementWithWait(driver, createdIssueLink).click();
    }

    public String cancelIssue(String projectName, String issueType, String issueSummary) {
        // Initiate new issue creation
        dashboardPage.clickOnCreateButton();
        this.setProjectField(projectName);
        this.setIssueTypeField(issueType);
        this.setSummaryField(issueSummary);

        // Find and click on cancel button
        Util.lookUpWebElementWithWait(driver, cancelButton).click();

        // Handling Alert modal
        Util.acceptAlert();

        // Get issue header text to validate that no new issue is created
        Util.lookUpWebElementWithWait(driver, dashboardPage.getIssuesOption()).click();
        Util.lookUpWebElementWithWait(driver, dashboardPage.getReportedByMe()).click();
        return Util.lookUpWebElementWithWait(driver, issuePage.getIssueSummaryHeader()).getText();
    }
}
