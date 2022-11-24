package test;


import org.junit.jupiter.api.*;
import pages.DashboardPage;
import pages.LogInPage;
import pages.LogOutPage;
import util.ReadFromConfig;
import util.Util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogInTests {

    LogInPage logInPage;
    DashboardPage dashboardPage;
    LogOutPage logOutPage;


    @BeforeAll
    public static void setDriver() {
        Util.setChromeDriver();
    }

    @BeforeEach
    public void setup() {
        Util.getChromeDriver();
        logInPage = new LogInPage(Util.driver);
        dashboardPage = new DashboardPage(Util.driver);
        logOutPage = new LogOutPage(Util.driver);
    }

    @AfterEach
    public void quit() {
        Util.quitBrowser();
    }


    @Test
    public void testSuccessfulLogIn() {
        logInPage.logInWithUser("url","correctUsername", "correctPassword");
        Util.waitForDashboard();

        String loggedInUsername = dashboardPage.checkUsername();
        String correctUsername = ReadFromConfig.readFromFile("correctUsername");
        assertEquals(correctUsername, loggedInUsername);

        Util.logOut(dashboardPage.getUserProfileElement(), dashboardPage.getLogOut());
    }

    @Test
    public void testIncorrectUsername() {
        logInPage.logInWithUser("url", "incorrectUsername", "correctPassword");

        String alertMessage = logInPage.getErrorMessage();

        assertEquals(Util.loginErrorAlertMessage, alertMessage);
    }

    @Test
    public void testIncorrectPassword() {
        logInPage.logInWithUser("url", "correctUsername", "incorrectPassword");

        String alertMessage = logInPage.getErrorMessage();

        assertEquals(Util.loginErrorAlertMessage, alertMessage);

        logInPage.logInWithUser("url", "correctUsername", "correctPassword");
        Util.waitForDashboard();
        Util.logOut(dashboardPage.getUserProfileElement(), dashboardPage.getLogOut());
    }

    @Test
    public void testEmptyFields() {
        logInPage.logInWithUser("url", "emptyUsername", "emptyPassword");

        String alertMessage = logInPage.getErrorMessage();

        assertEquals(Util.loginErrorAlertMessage, alertMessage);
    }

    @Test
    public void testEmptyPassword() {
        logInPage.logInWithUser("url", "correctUsername", "emptyPassword");

        String alertMessage = logInPage.getErrorMessage();

        assertEquals(Util.loginErrorAlertMessage, alertMessage);

        logInPage.logInWithUser("url", "correctUsername", "correctPassword");
        Util.waitForDashboard();
        Util.logOut(dashboardPage.getUserProfileElement(), dashboardPage.getLogOut());
    }

    @Test
    public void testLogInWithCaptcha() {
        for (int i=0; i<3; i++) {
            Util.refreshPage();
            logInPage.logInWithUser("url", "usernameForCaptchaTest", "incorrectPassword");
        }

        String alertMessage = logInPage.getErrorMessage();

        assertEquals(Util.captchaErrorMessage, alertMessage);

        assertTrue(logInPage.getCaptchaInput().isDisplayed());
        assertTrue(logInPage.getCaptchaImage().isDisplayed());
    }

    @Test
    public void testLogOut() {
        logInPage.logInWithUser("url", "correctUsername", "correctPassword");

        Util.logOut(dashboardPage.getUserProfileElement(), dashboardPage.getLogOut());

        assertEquals(Util.successfullyLoggedOutMessage, logOutPage.getLogOutMessage());
    }
}
