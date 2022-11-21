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





    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public WebElement getUserProfile() {
        return userProfile;
    }

    public WebElement getLogOut() {
        return logOut;
    }

    public String checkUsername() {
        return Util.lookUpWebElementWithWait(driver, userProfile).getAttribute("data-username");
    }
}
