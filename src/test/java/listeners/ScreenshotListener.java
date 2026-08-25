package listeners;

import core.BaseTest;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ScreenshotUtil;

/**
 * Captures a screenshot file on test failure (no Allure dependency).
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
        ScreenshotUtil.captureScreenshot(baseTest.getPage(), result.getName());
    }
}
