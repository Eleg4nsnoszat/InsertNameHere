package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.Util;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IssuePage {

    WebDriver driver;

    @FindBy(xpath = "//*[@id='key-val']")
    WebElement issueID;

    @FindBy(xpath = "//*[@id='summary-val']")
    WebElement issueSummaryHeader;

    @FindBy(xpath = "//*[@id='opsbar-operations_more']")
    WebElement moreOptionsDropdown;

    @FindBy(xpath = "//*[@id='create-subtask']/a")
    WebElement createSubtaskOption;

    @FindBy(xpath = "//*[@id='delete-issue']/a")
    WebElement deleteOption;

    @FindBy(xpath = "//*[@id='delete-issue-submit']")
    WebElement confirmDelete;

    @FindBy(xpath = "//*[@class='stsummary']/a")
    WebElement subtaskLink;

    @FindBy(xpath = "//*[@id='edit-issue']")
    WebElement editButton;


    public IssuePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public String getIssueId() {
        return Util.lookUpWebElementWithWait(driver, issueID).getText();
    }

    public String getIssueSummaryHeader() {
        return issueSummaryHeader.getText();
    }

    public WebElement getMoreOptionsDropdown() {
        return moreOptionsDropdown;
    }

    public WebElement getCreateSubtaskOption() {
        return createSubtaskOption;
    }

    public void validateIssueBase(String issueSummary) {
        WebElement issueSummaryTheSame = Util.lookUpWebElementWithWait(driver, issueSummaryHeader);
        assertEquals(issueSummary, issueSummaryTheSame.getText());
    }

    public void deleteIssue() {
        Actions actions = new Actions(driver);
        WebElement moreButton = Util.lookUpWebElementWithWait(driver, moreOptionsDropdown);
        actions.moveToElement(moreButton).click().build().perform();
        WebElement deleteButton = Util.lookUpWebElementWithWait(driver, deleteOption);
        deleteButton.click();
        WebElement confirmDeleteButton = Util.lookUpWebElementWithWait(driver, confirmDelete);
        confirmDeleteButton.click();
    }

    public void validateSubtask(String subtaskSummary){
        WebElement subtask = Util.lookUpWebElementWithWait(driver, subtaskLink);
        assertEquals(subtaskSummary, subtask.getText());
    }

    public void clickOnEditButton() {
        editButton.click();
    }
}
