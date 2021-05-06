import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class Lesson1 {
    private WebDriver driver;
    private String userNameElementXpath = "//input[@name='username']";
    private String passwordElementXpath = "//input[@name='password']";
    private String loginButtonXpath = "//button[@name='login']";
    private String logoutLinkXpath = "//a[@title='Logout']";
    private String loginValue = "admin";
    private int timeout = 15;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
    }

    @Test
    public void test1() {
        driver.get("http://www.google.com/");
    }

    @Test
    public void test3() {
        loginToAdmin();
        assert (driver.findElement(By.xpath(logoutLinkXpath)).getText()).matches("Logout");
    }

    @Test
    public void test7() {
        loginToAdmin();
        clickAppAndDoc("appearance", "logotype");
        clickAppAndDoc("catalog", "attribute_groups", "manufacturers", "suppliers", "delivery_statuses", "sold_out_statuses", "quantity_units", "csv");
        clickApps("countries", "currencies");
        clickAppAndDoc("customers", "csv", "newsletter");
        clickApps("geo_zones");
        clickAppAndDoc("languages", "storage_encoding");
        clickAppAndDoc("modules", "shipping", "payment", "order", "order_total", "jobs");
        clickAppAndDoc("orders", "order_statuses");
        clickAppAndDoc("pages", "csv");
        clickAppAndDoc("reports", "most_sold_products", "most_shopping_customers");
        clickAppAndDoc("settings", "defaults", "email", "listings", "customer_details", "legal", "images", "checkout", "advanced", "security");
        clickApps("slides");
        clickAppAndDoc("tax", "tax_classes");
        clickAppAndDoc("translations", "scan", "csv");
        clickApps("users", "vqmods");
    }

    public void loginToAdmin() {
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.xpath(userNameElementXpath)).sendKeys(loginValue);
        driver.findElement(By.xpath(passwordElementXpath)).sendKeys(loginValue);
        driver.findElement(By.xpath(loginButtonXpath)).click();
    }

    public void clickApps(String... params) {
        List<String> list = Arrays.stream(params).toList();
        list.forEach(element -> driver.findElement(By.xpath("//*[@class='app' and @data-code='" + element + "']")).click());
    }

    public void clickAppAndDoc(String classElement, String... docParams) {
        List<String> list = Arrays.stream(docParams).toList();
        driver.findElement(By.xpath("//*[@class='app' and @data-code='" + classElement + "']")).click();
        list.forEach(element -> driver.findElement(By.xpath("//*[@class='doc' and @data-code='" + element + "']")).click());
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}