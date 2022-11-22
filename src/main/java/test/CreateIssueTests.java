package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.CreateIssuePage;
import pages.IssuePage;
import pages.LogInPage;
import util.Util;


public class CreateIssueTests {

    LogInPage logInPage;
    CreateIssuePage createIssuePage;
    IssuePage issuePage;


    @BeforeAll
    public static void setDriver() {
        Util.setChromeDriver();
    }

    @BeforeEach
    public void setup() {
        Util.getChromeDriver();
        logInPage = new LogInPage(Util.driver);
        logInPage.logInWithUser(Util.loginPageUrl, Util.correctUsername, Util.correctPassword);
        createIssuePage = new CreateIssuePage(Util.driver);
        issuePage = new IssuePage(Util.driver);
    }

    @AfterEach
    public void quit() {
        Util.logOut(issuePage.getUserProfile(), issuePage.getLogOut());
        Util.quitBrowser();
    }

    @Test
    public void createIssueMTPBugTest() {
        createIssuePage.createIssueBase("Main Testing Project (MTP)","Bug","MTP test issue summary");
        createIssuePage.clickOnCreatedIssueModalLink();
        issuePage.validateIssueBase("MTP test issue summary");
        issuePage.deleteIssue();
    }
}
