package test;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.DashboardPage;
import pages.LogInPage;
import util.Util;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogInTests {

    LogInPage logInPage;
    DashboardPage dashboardPage;


    static final String loginPageUrl = "https://jira-auto.codecool.metastage.net";
    static final String loginErrorAlertMessage = "Sorry, your username and password are incorrect - please try again.";
    static final String correctUsername = "automation35";
    static final String correctPassword = "CCAutoTest19.";

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
    }

    @Test
    public void testSuccessfulLogIn() {
        logInPage.logInWithUser(loginPageUrl, correctUsername, correctPassword);

        String loggedInUsername = dashboardPage.checkUsername();
        assertEquals("automation35", loggedInUsername);

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
}
