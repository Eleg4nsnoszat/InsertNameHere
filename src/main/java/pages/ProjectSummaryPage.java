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
}
