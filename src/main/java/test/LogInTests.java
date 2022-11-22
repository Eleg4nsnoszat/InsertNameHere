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
        logInPage.logInWithUser(ReadFromConfig.readFromFile("url"),
                ReadFromConfig.readFromFile("correctUsername"),
                ReadFromConfig.readFromFile("correctPassword"));
        String loggedInUsername = dashboardPage.checkUsername();
        assertEquals(ReadFromConfig.readFromFile("correctUsername"), loggedInUsername);

        Util.logOut(dashboardPage.getUserProfileElement(), dashboardPage.getLogOut());
    }

    @Test
    public void testIncorrectUsername() {
        logInPage.logInWithIncorrectUser(ReadFromConfig.readFromFile("url"),
                ReadFromConfig.readFromFile("incorrectUsername"),
                ReadFromConfig.readFromFile("correctPassword"));

        String alertMessage = logInPage.getErrorMessage();

        assertEquals(Util.loginErrorAlertMessage, alertMessage);
    }

    @Test
    public void testIncorrectPassword() {
        logInPage.logInWithIncorrectUser(ReadFromConfig.readFromFile("url"),
                ReadFromConfig.readFromFile("correctUsername"),
                ReadFromConfig.readFromFile("incorrectPassword"));

        String alertMessage = logInPage.getErrorMessage();

        assertEquals(Util.loginErrorAlertMessage, alertMessage);

        logInPage.logInWithUser(ReadFromConfig.readFromFile("url"),
                ReadFromConfig.readFromFile("correctUsername"),
                ReadFromConfig.readFromFile("correctPassword"));
        Util.logOut(dashboardPage.getUserProfileElement(), dashboardPage.getLogOut());
    }

    @Test
    public void testEmptyFields() {
        logInPage.logInWithIncorrectUser(ReadFromConfig.readFromFile("url"),
                ReadFromConfig.readFromFile("emptyUsername"),
                ReadFromConfig.readFromFile("emptyPassword"));

        String alertMessage = logInPage.getErrorMessage();

        assertEquals(Util.loginErrorAlertMessage, alertMessage);
    }

    @Test
    public void testEmptyPassword() {
        logInPage.logInWithIncorrectUser(ReadFromConfig.readFromFile("url"),
                ReadFromConfig.readFromFile("correctUsername"),
                ReadFromConfig.readFromFile("emptyPassword"));

        String alertMessage = logInPage.getErrorMessage();

        assertEquals(Util.loginErrorAlertMessage, alertMessage);

        logInPage.logInWithUser(ReadFromConfig.readFromFile("url"),
                ReadFromConfig.readFromFile("correctUsername"),
                ReadFromConfig.readFromFile("correctPassword"));
        Util.logOut(dashboardPage.getUserProfileElement(), dashboardPage.getLogOut());
    }

    @Test
    public void testLogInWithCaptcha() {
        for (int i=0; i<3; i++) {
            Util.refreshPage();
            logInPage.logInWithIncorrectUser(ReadFromConfig.readFromFile("url"),
                    ReadFromConfig.readFromFile("usernameForCaptchaTest"),
                    ReadFromConfig.readFromFile("incorrectPassword"));
        }

        String alertMessage = logInPage.getErrorMessage();

        assertEquals(Util.captchaErrorMessage, alertMessage);

        assertTrue(logInPage.getCaptchaInput().isDisplayed());
    }

    @Test
    public void testLogOut() {
        logInPage.logInWithUser(ReadFromConfig.readFromFile("url"),
                ReadFromConfig.readFromFile("correctUsername"),
                ReadFromConfig.readFromFile("correctPassword"));

        Util.logOut(dashboardPage.getUserProfileElement(), dashboardPage.getLogOut());

        assertEquals(Util.successfullyLoggedOutMessage, logOutPage.getLogOutMessage());
    }
}
