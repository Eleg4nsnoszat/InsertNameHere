package util;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Util {

    public static WebDriver driver;

    public static WebElement lookUpWebElementWithWait(WebDriver driver, WebElement element) {
        return new WebDriverWait(driver, Duration.ofMillis(1000)).until(ExpectedConditions.elementToBeClickable(element));
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


    public static void logOut(WebElement userProfile, WebElement logOut) {
        WebElement userDropdown = lookUpWebElementWithWait(driver, userProfile);
        Util.scrollToElement(driver, userDropdown);
        userDropdown.click();
        WebElement logOutOption = lookUpWebElementWithWait(driver, logOut);
        logOutOption.click();
    }

    public static void navigateToUrl(WebDriver driver, String url) {
        driver.get(url);
    }

    public static void refreshPage() {
        driver.navigate().refresh();
    }


    public static void setChromeDriver() {
        WebDriverManager.chromedriver().setup();
    }

    public static void getChromeDriver() {
        driver = new ChromeDriver();
    }

    public static void quitBrowser() {
        driver.quit();
    }
}
