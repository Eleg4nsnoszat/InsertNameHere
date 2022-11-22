package test;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pages.*;
import util.Util;

import static org.junit.jupiter.api.Assertions.assertNotEquals;


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
        logInPage.logInWithUser("url", "correctUsername", "correctPassword");
        Util.waitForDashboard();
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

    @ParameterizedTest
    @CsvFileSource(resources = "/IssueTestData.csv", numLinesToSkip = 1)
    public void createIssuesTest(String projectName, String issueType, String issueSummary) throws Exception {
        try {
            createIssuePage.createIssueBase(projectName, issueType, issueSummary);
            createIssuePage.clickOnCreatedIssueModalLink();
            issuePage.validateIssueBase(issueSummary);
            issuePage.deleteIssue();
        } catch (Exception error) {
            Util.forceQuit();
            throw new Exception("No such element: " + projectName, error);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/SubTaskTestData.csv", numLinesToSkip = 1)
    public void createIssueSubtaskTest(String projectName, String issueType, String issueSummary, String subTask) throws Exception {
        try {
            createIssuePage.createIssueBase(projectName, issueType, issueSummary);
            createIssuePage.clickOnCreatedIssueModalLink();
            createSubtaskPage.createSubtask(subTask);
            issuePage.validateSubtask(subTask);
            issuePage.deleteIssue();
        } catch (Exception error) {
            Util.forceQuit();
            throw new Exception("No such element: " + projectName, error);
        }
    }

    @Test
    public void cancelIssueTest(){
        String summaryFieldCheck = createIssuePage.cancelIssue("Main Testing Project (MTP)", "Bug", "Cancel Issue");

        //Validate that no new issue is created
        assertNotEquals(summaryFieldCheck, "Cancel Issue");
    }
}
