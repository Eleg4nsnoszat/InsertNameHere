package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.Util;

public class LogInPage {

    WebDriver driver;
    Util utils;

    @FindBy(xpath = "//input[@id='login-form-username']")
    WebElement usernameInput;

    @FindBy(xpath = "//input[@id='login-form-password']")
    WebElement passwordInput;

    @FindBy(xpath = "//input[@id='login']")
    WebElement loginButton;

    @FindBy(xpath = "//*[@id='usernameerror']/")
    WebElement logInErrorMessage;




    public LogInPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }




    private void setUsernameInput(String username) {
        usernameInput.sendKeys(username);
    }

    private void setPasswordInput(String password) {
        passwordInput.sendKeys(password);
    }

    private void clickOnLoginButton() {
        loginButton.click();
    }




    public void logInWithUser(String url, String username, String password) {
        utils.navigateToUrl(driver, url);
        this.setUsernameInput(username);
        this.setPasswordInput(password);
        this.clickOnLoginButton();
    }

    public String getErrorMessage() {
        return logInErrorMessage.getText();
    }
}
