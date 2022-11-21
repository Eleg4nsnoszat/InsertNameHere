package test;


import org.junit.jupiter.api.*;
import pages.DashboardPage;
import pages.LogInPage;
import pages.LogOutPage;
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
        logInPage.logInWithUser(Util.loginPageUrl, Util.correctUsername, Util.correctPassword);

        String loggedInUsername = dashboardPage.checkUsername();
        assertEquals(Util.correctUsername, loggedInUsername);

        Util.logOut(dashboardPage.getUserProfileElement(), dashboardPage.getLogOut());
    }

    @Test
    public void testIncorrectUsername() {
        logInPage.logInWithUser(Util.loginPageUrl, Util.incorrectUsername, Util.correctPassword);

        String alertMessage = logInPage.getErrorMessage();

        assertEquals(Util.loginErrorAlertMessage, alertMessage);
    }

    @Test
    public void testIncorrectPassword() {
        logInPage.logInWithUser(Util.loginPageUrl, Util.correctUsername, Util.incorrectPassword);

        String alertMessage = logInPage.getErrorMessage();

        assertEquals(Util.loginErrorAlertMessage, alertMessage);

        logInPage.logInWithUser(Util.loginPageUrl, Util.correctUsername, Util.correctPassword);
        Util.logOut(dashboardPage.getUserProfileElement(), dashboardPage.getLogOut());
    }

    @Test
    public void testEmptyFields() {
        logInPage.logInWithUser(Util.loginPageUrl, Util.emptyUsername, Util.emptyPassword);

        String alertMessage = logInPage.getErrorMessage();

        assertEquals(Util.loginErrorAlertMessage, alertMessage);
    }

    @Test
    public void testEmptyPassword() {
        logInPage.logInWithUser(Util.loginPageUrl, Util.correctUsername, Util.emptyPassword);

        String alertMessage = logInPage.getErrorMessage();

        assertEquals(Util.loginErrorAlertMessage, alertMessage);

        logInPage.logInWithUser(Util.loginPageUrl, Util.correctUsername, Util.correctPassword);
        Util.logOut(dashboardPage.getUserProfileElement(), dashboardPage.getLogOut());
    }

    @Test
    public void testLogInWithCaptcha() {
        for (int i=0; i<3; i++) {
            Util.refreshPage();
            logInPage.logInWithUser(Util.loginPageUrl, Util.usernameForCaptchaTest, Util.incorrectPassword);
        }

        String alertMessage = logInPage.getErrorMessage();

        assertEquals(Util.captchaErrorMessage, alertMessage);

        assertTrue(logInPage.getCaptchaInput().isDisplayed());
    }

    @Test
    public void testLogOut() {
        logInPage.logInWithUser(Util.loginPageUrl, Util.correctUsername, Util.correctPassword);

        Util.logOut(dashboardPage.getUserProfileElement(), dashboardPage.getLogOut());

        assertEquals(Util.successfullyLoggedOutMessage, logOutPage.getLogOutMessage());
    }
}
