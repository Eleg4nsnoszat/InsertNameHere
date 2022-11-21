package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProjectSummaryPage {

    WebDriver driver;


    @FindBy(xpath = "//dd[@class='project-meta-value' and not(descendant::*)]")
    WebElement projectKey;

    @FindBy(xpath = "//*[@id='main']/*[contains(@class, 'page-heading')]")
    WebElement cannotViewProjectMessage;

    @FindBy(xpath = "//*[@id='header-details-user-fullname']")
    WebElement userProfile;

    @FindBy(xpath = "//*[@id='log_out']")
    WebElement logOut;



    public ProjectSummaryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }




    public String getProjectKey() {
        return projectKey.getText();
    }

    public String getCannotViewProjectMessage() {
        return cannotViewProjectMessage.getText();
    }

    public WebElement getUserProfile() {
        return userProfile;
    }

    public WebElement getLogOut() {
        return logOut;
    }
}
