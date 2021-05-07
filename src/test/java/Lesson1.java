import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Lesson1 {
    private WebDriver driver;
    private final int timeout = 5;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
    }

    @Test
    public void test1() {
        Utils.openPage(driver, "http://www.google.com/");
    }

    @Test
    public void test3() {
        Utils.loginAsAdminUser(driver, "http://localhost/litecart/admin/login.php");
    }

    @Test
    public void test5() {
        WebDriver driverESR;
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(FirefoxDriver.MARIONETTE, false);
        driverESR = new FirefoxDriver(caps);
        Utils.loginAsAdminUser(driverESR, "http://localhost/litecart/admin/login.php");
    }

    @Test
    public void test6() {
        WebDriver driverESR;
        System.setProperty("webdriver.gecko.driver", "C:\\Tools\\geckodriver.exe");
        driverESR = new FirefoxDriver(new FirefoxOptions().setBinary("C:\\Program Files\\Firefox Nightly\\firefox.exe"));
        Utils.loginAsAdminUser(driverESR, "http://localhost/litecart/admin/login.php");
    }

    @Test
    public void test7() {
        Utils.loginAsAdminUser(driver, "http://localhost/litecart/admin/login.php");
        MenuPage.menuItemsList().forEach(item -> {
            Utils.clickOnMenuItem(driver, item.getDataCode());
            if (item.isSubMenuPresent()) {
                List<MenuPage> subMenuItems = MenuPage.getSubMenuItems(item);
                subMenuItems.forEach(subMenuItem -> Utils.clickOnMenuItem(driver, subMenuItem.getDataCode()));
            }
        });
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}