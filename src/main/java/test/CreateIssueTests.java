package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.*;
import util.Util;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CreateIssueTests {

    LogInPage logInPage;
    DashboardPage dashboardPage;
    CreateIssuePage createIssuePage;
    IssuePage issuePage;
    CreateSubtaskPage createSubtaskPage;


    @BeforeAll
    public static void setDriver() {
        Util.setChromeDriver();
    }

    @BeforeEach
    public void setup() {
        Util.getChromeDriver();
        logInPage = new LogInPage(Util.driver);
        logInPage.logInWithUser(Util.loginPageUrl, Util.correctUsername, Util.correctPassword);
        dashboardPage = new DashboardPage(Util.driver);
        createIssuePage = new CreateIssuePage(Util.driver);
        issuePage = new IssuePage(Util.driver);
        createSubtaskPage = new CreateSubtaskPage(Util.driver);
    }

    @AfterEach
    public void quit() {
        Util.logOut(dashboardPage.getUserProfileElement(), dashboardPage.getLogOut());
        Util.quitBrowser();
    }

    @Test
    public void createIssueMTPBugTest() {
        createIssuePage.createIssueBase("Main Testing Project (MTP)","Bug","MTP test issue summary");
        createIssuePage.clickOnCreatedIssueModalLink();
        issuePage.validateIssueBase("MTP test issue summary");
        issuePage.deleteIssue();
    }

    @Test
    public void createIssueMTPSubtaskTest(){
        createIssuePage.createIssueBase("Main Testing Project (MTP)","Task","MTP test issue summary");
        createIssuePage.clickOnCreatedIssueModalLink();
        createSubtaskPage.createSubtask("MTP test subtask summary");
        issuePage.validateSubtask("MTP test subtask summary");
        issuePage.deleteIssue();
    }

    @Test
    public void cancelIssueTest(){
        String summaryFieldCheck = createIssuePage.cancelIssue("Main Testing Project (MTP)", "Task", "MTP test issue summary");

        //Validate that no new issue is created
        assertEquals(summaryFieldCheck, "MTP test issue summary");
    }
}
