package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.Util;

public class LogOutPage {

    WebDriver driver;

    @FindBy(xpath = "//*[@class='title']/child::*[1]")
    WebElement logOutMessage;


    public LogOutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getLogOutMessage() {
        return Util.lookUpWebElementWithWait(driver, logOutMessage).getText();
    }
}
