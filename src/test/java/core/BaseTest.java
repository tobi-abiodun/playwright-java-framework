package core;

import com.microsoft.playwright.Page;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * BaseTest — browser lifecycle for all tests (tracing on failure).
 */
public abstract class BaseTest {

    protected Page page;
    private PlaywrightFactory playwrightFactory;

    @BeforeMethod
    public void baseSetUp() {
        playwrightFactory = new PlaywrightFactory();
        page = playwrightFactory.createPage();
    }

    @AfterMethod
    public void baseTearDown(ITestResult result) {
        boolean failed = result != null && result.getStatus() == ITestResult.FAILURE;
        String testName = result != null ? result.getName() : "unknown";
        if (playwrightFactory != null) {
            playwrightFactory.stopTracing(failed, testName);
            playwrightFactory.close();
        }
    }

    public Page getPage() {
        return page;
    }
}
