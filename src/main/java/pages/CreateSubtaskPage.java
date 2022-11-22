package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.Util;

public class CreateSubtaskPage {

    WebDriver driver;

    @FindBy(xpath = "//*[@id='opsbar-operations_more']")
    WebElement moreOptionsDropdown;

    @FindBy(xpath = "//*[@id='create-subtask']/a")
    WebElement createSubtaskOption;

    @FindBy(xpath = "//*[@id='summary']")
    WebElement summaryInput;

    @FindBy(xpath = "//*[@id='create-issue-submit']")
    WebElement createSubtaskSubmitButton;



    public CreateSubtaskPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }




    public void createSubtask(String subtaskSummary){
        Actions actions = new Actions(driver);
        WebElement moreButton = Util.lookUpWebElementWithWait(driver, moreOptionsDropdown);
        actions.moveToElement(moreButton).click().build().perform();
        Util.lookUpWebElementWithWait(driver, createSubtaskOption).click();
        Util.lookUpWebElementWithWait(driver, summaryInput).sendKeys(subtaskSummary);
        Util.lookUpWebElementWithWait(driver, createSubtaskSubmitButton).click();
    }

}
