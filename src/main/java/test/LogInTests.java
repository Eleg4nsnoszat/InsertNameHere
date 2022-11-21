package test;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.DashboardPage;
import pages.LogInPage;
import util.Util;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogInTests {

    LogInPage logInPage;
    DashboardPage dashboardPage;


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

        logInPage.logInWithUser("https://jira-auto.codecool.metastage.net", "automation35", "CCAutoTest19.");

        String loggedInUsername = dashboardPage.checkUsername();
        assertEquals("automation35", loggedInUsername);
    }
}
