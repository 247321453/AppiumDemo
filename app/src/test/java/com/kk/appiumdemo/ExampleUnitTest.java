package com.kk.appiumdemo;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.URL;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import static org.junit.Assert.*;

/**
 * 编写稍微复杂，但是不需要依赖源码，支持跨进程和webview
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testLogin() throws Exception {
        File apk = new File("app/build/outputs/apk/app-debug.apk");
        if (!apk.exists()) {
            System.err.println("no find apk:" + apk.getAbsolutePath());
            return;
        }
        System.out.println("use apk:" + apk.getAbsolutePath());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0.1");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "071ad3b80059be56");
        capabilities.setCapability(MobileCapabilityType.APP, apk.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, BuildConfig.APPLICATION_ID);
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetkeyboard", true);
//        capabilities.setCapability("app", "/path/to/some.apk");
        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        AppiumDriver driver = new AppiumDriver(url, capabilities);
        if(!driver.isAppInstalled(BuildConfig.APPLICATION_ID)){
            driver.installApp(apk.getAbsolutePath());
        }
        // 切换到最新的web视图
//        driver.switchTo().window("WEBVIEW");
        System.out.println("wait ide");
        Thread.sleep(5000);
        try {
            WebElement webElement = driver.findElementByAndroidUIAutomator("text(\"Hello World!\")");
            if (webElement != null) {
                webElement.click();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(3000);
        try {
            //R.id.fab
            WebElement webElement = driver.findElementById(BuildConfig.APPLICATION_ID+":id/fab");
            if (webElement != null) {
                webElement.click();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(3000);
        try {
            //R.id.fab
            WebElement webElement = driver.findElementById(BuildConfig.APPLICATION_ID+":id/edt01");
            if (webElement != null) {
                webElement.sendKeys("你好");
                Thread.sleep(1000);
                assertEquals("你好", webElement.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(5000);
        //关闭应用。
        driver.quit();
    }
}