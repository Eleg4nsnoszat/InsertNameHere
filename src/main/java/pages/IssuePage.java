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

    @FindBy(xpath = "//*[@id='header-details-user-fullname']")
    WebElement userProfile;

    @FindBy(xpath = "//*[@id='log_out']")
    WebElement logOut;

    @FindBy(xpath = "//*[@id='summary-val']")
    WebElement issueSummaryHeader;

    @FindBy(xpath = "//*[@id='opsbar-operations_more']")
    WebElement moreOptionsDropdown;

    @FindBy(xpath = "//*[@id='delete-issue']/a")
    WebElement deleteOption;

    @FindBy(xpath = "//*[@id='delete-issue-submit']")
    WebElement confirmDelete;


    public IssuePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public String getIssueId() {
        return Util.lookUpWebElementWithWait(driver, issueID).getText();
    }

    public WebElement getUserProfile() {
        return userProfile;
    }

    public WebElement getLogOut() {
        return logOut;
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
}
