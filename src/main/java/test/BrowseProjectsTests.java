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


    private void checkProjectSummaryPage(String projectName) {
        Util.navigateToUrl("https://jira-auto.codecool.metastage.net/projects/" + projectName + "/summary");
        assertEquals(projectName, projectSummaryPage.getProjectKey());
    }


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
        Util.logOut(projectSummaryPage.getUserProfile(), projectSummaryPage.getLogOut());
        Util.quitBrowser();
    }

    @Test
    public void browseMTPProjectTest(){
        checkProjectSummaryPage("MTP");
    }

    @Test
    public void browseNonExistingProjectTest(){
        Util.navigateToUrl("https://jira-auto.codecool.metastage.net/projects/DUCK/summary");
        String nonExistingProjectPage = projectSummaryPage.getCannotViewProjectMessage();
        assertEquals("You can't view this project", nonExistingProjectPage);
    }

    @Test
    public void checkCOALAProjectDetailsTest(){
        checkProjectSummaryPage("COALA");
    }

    @Test
    public void checkTOUCANProjectDetailsTest(){
        checkProjectSummaryPage("TOUCAN");
    }

    @Test
    public void checkJETIProjectDetailsTest(){
        checkProjectSummaryPage("JETI");
    }
}
