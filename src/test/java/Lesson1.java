import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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