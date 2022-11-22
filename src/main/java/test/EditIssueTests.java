package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.*;
import util.Util;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditIssueTests {

    LogInPage logInPage;
    DashboardPage dashboardPage;
    CreateIssuePage createIssuePage;
    IssuePage issuePage;
    EditIssuePage editIssuePage;


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
        editIssuePage = new EditIssuePage(Util.driver);
    }

    @AfterEach
    public void quit() {
        Util.logOut(dashboardPage.getUserProfileElement(), dashboardPage.getLogOut());
        Util.quitBrowser();
    }

    @Test
    public void editIssueTest() {
        // Create a new test issue
        createIssuePage.createIssueBase("Main Testing Project (MTP)","Bug","MTP test issue summary");
        createIssuePage.clickOnCreatedIssueModalLink();

        // Edit summary text
        issuePage.clickOnEditButton();
        editIssuePage.editIssueSummaryField("Big-sub");
        editIssuePage.clickOnUpdate();

        // Check if the summary text remains the same after page refresh
        Util.refreshPage();
        Util.acceptAlert();
        String issueSummary = issuePage.getIssueSummaryHeader();
        assertEquals("Big-sub", issueSummary);

        // Delete the created test issue
        issuePage.deleteIssue();
    }
}
