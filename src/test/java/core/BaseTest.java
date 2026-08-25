package core;

import com.microsoft.playwright.Page;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * BaseTest — browser lifecycle for all tests.
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
    public void baseTearDown() {
        if (playwrightFactory != null) {
            playwrightFactory.close();
        }
    }

    public Page getPage() {
        return page;
    }
}
