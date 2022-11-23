package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.ReadFromConfig;
import util.Util;

public class LogInPage {

    WebDriver driver;
    DashboardPage dashboardPage;

    @FindBy(xpath = "//input[@id='login-form-username']")
    WebElement usernameInput;

    @FindBy(xpath = "//input[@id='login-form-password']")
    WebElement passwordInput;

    @FindBy(xpath = "//input[@id='login-form-submit']")
    WebElement loginButton;

    @FindBy(xpath = "//*[@class='aui-message aui-message-error']/child::p")
    WebElement logInErrorMessage;

    @FindBy(xpath = "//*[@id='login-form-os-captcha']")
    WebElement captchaInput;

    @FindBy(xpath = "//img[@class='captcha-image']")
    WebElement captchaImage;




    public LogInPage(WebDriver driver) {
        this.driver = driver;
        this.dashboardPage = new DashboardPage(driver);
        PageFactory.initElements(driver, this);
    }




    private void setUsernameInput(String username) {
        Util.lookUpWebElementWithWait(driver, usernameInput).sendKeys(username);
    }

    private void setPasswordInput(String password) {
        Util.lookUpWebElementWithWait(driver, passwordInput).sendKeys(password);
    }

    private void clickOnLoginButton() {
        Util.lookUpWebElementWithWait(driver, loginButton).click();
    }



    public void logInWithUser(String url, String username, String password) {
        Util.navigateToUrl(ReadFromConfig.readFromFile(url));
        this.setUsernameInput(ReadFromConfig.readFromFile(username));
        this.setPasswordInput(ReadFromConfig.readFromFile(password));
        this.clickOnLoginButton();
    }

    public String getErrorMessage() {
        return Util.lookUpWebElementWithWait(driver, logInErrorMessage).getText();
    }

    public WebElement getCaptchaInput() {
        return Util.lookUpWebElementWithWait(driver, captchaInput);
    }

    public WebElement getCaptchaImage() {
        return Util.lookUpWebElementWithWait(driver, captchaImage);
    }
}
