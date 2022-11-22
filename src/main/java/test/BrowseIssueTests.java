package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.DashboardPage;
import pages.IssuePage;
import pages.LogInPage;
import util.ReadFromConfig;
import util.Util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BrowseIssueTests {

    LogInPage logInPage;
    DashboardPage dashboardPage;
    IssuePage issuePage;


    public String browseProjectIssues(String projectName, String issueNumber) {
        Util.navigateToUrl("https://jira-auto.codecool.metastage.net/projects/" + projectName + "/issues/"+projectName+"-"+issueNumber+"?filter=allissues");
        return issuePage.getIssueId();
    }


    @BeforeAll
    public static void setDriver() {
        Util.setChromeDriver();
    }

    @BeforeEach
    public void setup() {
        Util.getChromeDriver();
        logInPage = new LogInPage(Util.driver);
        logInPage.logInWithUser(ReadFromConfig.readFromFile("url"),
                ReadFromConfig.readFromFile("correctUsername"),
                ReadFromConfig.readFromFile("correctPassword"));
        dashboardPage = new DashboardPage(Util.driver);
        issuePage = new IssuePage(Util.driver);
    }

    @AfterEach
    public void quit() {
        Util.logOut(dashboardPage.getUserProfileElement(), dashboardPage.getLogOut());
        Util.quitBrowser();
    }

    @Test
    public void browseExistingIssueTest(){
        assertEquals("MTP-1", browseProjectIssues("MTP","1"));
    }

    @Test
    public void browseNonExistingIssueTest(){
        assertNotEquals("MTP-0", browseProjectIssues("MTP","0"));
    }

    @Test
    public void browseTOUCANIssueTest() {
        assertEquals("TOUCAN-1", browseProjectIssues("TOUCAN","1"));
        assertEquals("TOUCAN-2", browseProjectIssues("TOUCAN","2"));
        assertEquals("TOUCAN-3", browseProjectIssues("TOUCAN","3"));
    }

    @Test
    public void browseJETIIssueTest() {
        assertEquals("JETI-1", browseProjectIssues("JETI","1"));
        assertEquals("JETI-2", browseProjectIssues("JETI","2"));
        assertEquals("JETI-3", browseProjectIssues("JETI","3"));
    }

    @Test
    public void browseCOALAIssueTest() {
        assertEquals("COALA-1", browseProjectIssues("COALA","1"));
        assertEquals("COALA-2", browseProjectIssues("COALA","2"));
        assertEquals("COALA-3", browseProjectIssues("COALA","3"));
    }
}
