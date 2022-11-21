package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.Util;

public class IssuePage {

    WebDriver driver;

    @FindBy(xpath = "//*[@id='key-val']")
    WebElement issueID;

    @FindBy(xpath = "//*[@id='header-details-user-fullname']")
    WebElement userProfile;

    @FindBy(xpath = "//*[@id='log_out']")
    WebElement logOut;


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
}
