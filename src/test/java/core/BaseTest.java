package core;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.LoggerUtil;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * BaseTest — browser lifecycle for all tests (tracing on failure, video every run).
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
        if (playwrightFactory == null) {
            return;
        }
        try {
            playwrightFactory.stopTracing(failed, testName);
            // Close page/context first so Playwright finalizes the .webm
            playwrightFactory.closePageAndContext();
            Path videoPath = playwrightFactory.finalizeVideo(testName);
            attachVideoToAllure(videoPath);
        } finally {
            playwrightFactory.closeBrowser();
        }
    }

    private void attachVideoToAllure(Path videoPath) {
        if (videoPath == null || !Files.exists(videoPath)) {
            return;
        }
        try {
            byte[] bytes = Files.readAllBytes(videoPath);
            Allure.addAttachment("Video", "video/webm", new ByteArrayInputStream(bytes), ".webm");
            LoggerUtil.info("Attached video to Allure: " + videoPath);
        } catch (Exception exception) {
            LoggerUtil.error("Failed to attach video to Allure: " + videoPath, exception);
        }
    }

    public Page getPage() {
        return page;
    }
}
