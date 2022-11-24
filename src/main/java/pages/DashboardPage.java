package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.Util;

public class DashboardPage {

    WebDriver driver;

    @FindBy(xpath = "//*[@id='header-details-user-fullname']")
    WebElement userProfile;

    @FindBy(xpath = "//*[@id='log_out']")
    WebElement logOut;

    @FindBy(xpath = "//*[@id='find_link']")
    WebElement issuesOption;

    @FindBy(xpath = "//*[@id='filter_lnk_reported_lnk']")
    WebElement reportedByMe;

    @FindBy(xpath = "//*[@id='create_link']")
    WebElement createButton;





    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public WebElement getUserProfileElement() {
        return userProfile;
    }

    public WebElement getLogOut() {
        return logOut;
    }

    public WebElement getIssuesOption() {
        return issuesOption;
    }

    public WebElement getReportedByMe() {
        return reportedByMe;
    }

    public String checkUsername() {
        return Util.lookUpWebElementWithWait(driver, userProfile).getAttribute("data-username");
    }

    public void clickOnCreateButton() {
        Util.lookUpWebElementWithWait(driver, createButton).click();
    }
}
