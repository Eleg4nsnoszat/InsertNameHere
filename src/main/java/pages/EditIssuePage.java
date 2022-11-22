package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.Util;

public class EditIssuePage {

    WebDriver driver;

    @FindBy(xpath = "//*[@id='summary']")
    WebElement issueSummary;

    @FindBy(xpath = "//*[@id='edit-issue-submit']")
    WebElement updateButton;


    public EditIssuePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void editIssueSummaryField(String newSummary) {
        WebElement summaryEditField = Util.lookUpWebElementWithWait(driver, issueSummary);
        summaryEditField.click();
        summaryEditField.clear();
        summaryEditField.sendKeys(newSummary);
    }

    public void clickOnUpdate() {
        updateButton.click();
    }
}
