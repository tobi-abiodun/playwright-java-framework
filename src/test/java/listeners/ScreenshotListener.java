package listeners;

import core.BaseTest;
import io.qameta.allure.Allure;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.LoggerUtil;
import utils.ScreenshotUtil;

import java.io.ByteArrayInputStream;

/**
 * Captures a screenshot file on test failure and attaches it to Allure.
 */
public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object testInstance = result.getInstance();
        if (!(testInstance instanceof BaseTest baseTest)) {
            return;
        }
        if (baseTest.getPage() == null) {
            return;
        }
        try {
            String path = ScreenshotUtil.captureScreenshot(baseTest.getPage(), result.getName());
            byte[] bytes = ScreenshotUtil.readBytes(path);
            Allure.addAttachment("Failure screenshot", "image/png", new ByteArrayInputStream(bytes), ".png");
            LoggerUtil.info("Saved failure screenshot: " + path);
        } catch (Exception exception) {
            LoggerUtil.error("Failed to capture/attach screenshot for " + result.getName(), exception);
        }
    }
}
