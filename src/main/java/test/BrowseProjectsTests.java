package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.LogInPage;
import pages.ProjectSummaryPage;
import util.Util;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class BrowseProjectsTests {

    LogInPage logInPage;
    ProjectSummaryPage projectSummaryPage;


    @BeforeAll
    public static void setDriver() {
        Util.setChromeDriver();
    }

    @BeforeEach
    public void setup() {
        Util.getChromeDriver();
        logInPage = new LogInPage(Util.driver);
        logInPage.logInWithUser(Util.loginPageUrl, Util.correctUsername, Util.correctPassword);
        projectSummaryPage = new ProjectSummaryPage(Util.driver);
    }

    @AfterEach
    public void quit() {
        Util.quitBrowser();
    }

    @Test
    public void browseMTPProjectTest(){
        Util.navigateToUrl("https://jira-auto.codecool.metastage.net/projects/MTP/summary");
        String projectName = projectSummaryPage.getProjectKey();
        assertEquals("MTP", projectName);
    }

    @Test
    public void browseNonExistingProjectTest(){
        Util.navigateToUrl("https://jira-auto.codecool.metastage.net/projects/DUCK/summary");
        String nonExistingProjectPage = projectSummaryPage.getCannotViewProjectMessage();
        assertEquals("You can't view this project", nonExistingProjectPage);
    }
}
