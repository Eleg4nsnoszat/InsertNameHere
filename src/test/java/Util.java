import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Util {
    public static WebElement lookUpWebElementWithWait(WebDriver driver, String selector) {
        return new WebDriverWait(driver, Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));
    }

    public static WebElement lookUpWebElementWithWait(WebDriver driver, By element) {
        return new WebDriverWait(driver, Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement lookUpWebElementWithWait(WebDriver driver, int duration, String selector) {
        return new WebDriverWait(driver, Duration.ofMillis(duration)).until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));
    }

    public static By lookUpWebElementByRelativeLocator(String parent, String child) {
        return RelativeLocator.with(By.cssSelector(parent)).below(By.cssSelector(child));
    }

    public static void scrollToElement(WebDriver driver, WebElement element) {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].scrollIntoView()", element);
    }

    public static void logInWithUser(WebDriver driver, String username, String password) {

        WebElement usernameField = driver.findElement(By.id("login-form-username"));
        WebElement passwordField = driver.findElement(By.id("login-form-password"));
        WebElement logInButton = driver.findElement(By.id("login"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        logInButton.click();
    }

    public static void logOut(WebDriver driver) {
//        WebElement userProfile = driver.findElement(By.id("header-details-user-fullname"));
        WebElement userProfile = lookUpWebElementWithWait(driver, "#header-details-user-fullname");
        Util.scrollToElement(driver, userProfile);
        userProfile.click();
        WebElement logOutOption = driver.findElement(By.id("log_out"));
        logOutOption.click();
    }
}
