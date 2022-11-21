package test;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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


    static final String loginPageUrl = "https://jira-auto.codecool.metastage.net";
    static final String loginErrorAlertMessage = "Sorry, your username and password are incorrect - please try again.";
    static final String captchaErrorMessage = "Sorry, your userid is required to answer a CAPTCHA question correctly.";
    static final String successfullyLoggedOutMessage = "You are now logged out. Any automatic login has also been stopped.";
    static final String correctUsername = "automation39";
    static final String correctPassword = "CCAutoTest19.";
    static final String usernameForCaptchaTest = "automation35";
    static final String incorrectUsername = "username123";
    static final String incorrectPassword = "password123";

    static final String emptyUsername = "";
    static final String emptyPassword = "";


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

    @Test
    public void testSuccessfulLogIn() {
        logInPage.logInWithUser(loginPageUrl, correctUsername, correctPassword);

        String loggedInUsername = dashboardPage.checkUsername();
        assertEquals(correctUsername, loggedInUsername);

        Util.logOut(dashboardPage.getUserProfileElement(), dashboardPage.getLogOut());
    }

    @Test
    public void testIncorrectUsername() {
        logInPage.logInWithUser(loginPageUrl, incorrectUsername, correctPassword);

        String alertMessage = logInPage.getErrorMessage();

        assertEquals(loginErrorAlertMessage, alertMessage);
    }

    @Test
    public void testIncorrectPassword() {
        logInPage.logInWithUser(loginPageUrl, correctUsername, incorrectPassword);

        String alertMessage = logInPage.getErrorMessage();

        assertEquals(loginErrorAlertMessage, alertMessage);

        logInPage.logInWithUser(loginPageUrl, correctUsername, correctPassword);
        Util.logOut(dashboardPage.getUserProfileElement(), dashboardPage.getLogOut());
    }

    @Test
    public void testEmptyFields() {
        logInPage.logInWithUser(loginPageUrl, emptyUsername, emptyPassword);

        String alertMessage = logInPage.getErrorMessage();

        assertEquals(loginErrorAlertMessage, alertMessage);
    }

    @Test
    public void testEmptyPassword() {
        logInPage.logInWithUser(loginPageUrl, correctUsername, emptyPassword);

        String alertMessage = logInPage.getErrorMessage();

        assertEquals(loginErrorAlertMessage, alertMessage);

        logInPage.logInWithUser(loginPageUrl, correctUsername, correctPassword);
        Util.logOut(dashboardPage.getUserProfileElement(), dashboardPage.getLogOut());
    }

    @Test
    public void testLogInWithCaptcha() {
        for (int i=0; i<3; i++) {
            Util.refreshPage();
            logInPage.logInWithUser(loginPageUrl, usernameForCaptchaTest, incorrectPassword);
        }

        String alertMessage = logInPage.getErrorMessage();

        assertEquals(captchaErrorMessage, alertMessage);

        assertTrue(logInPage.getCaptchaInput().isDisplayed());
    }

    @Test
    public void testLogOut() {
        logInPage.logInWithUser(loginPageUrl, correctUsername, correctPassword);

        Util.logOut(dashboardPage.getUserProfileElement(), dashboardPage.getLogOut());

        assertEquals(successfullyLoggedOutMessage, logOutPage.getLogOutMessage());
    }
}
