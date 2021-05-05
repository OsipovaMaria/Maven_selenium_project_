import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;


public class Lesson1 {
    private WebDriver driver;
    private int timeout = 10;

    @BeforeClass
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
    }
    @Test
    public void test1(){
        driver.get("http://www.google.com/");
    }
    @Test
    public void test3(){
        loginToAdmin();
        String logoutLinkXpath = "//a[@title='Logout']";
        assert(driver.findElement(By.xpath(logoutLinkXpath)).getText()).matches("Logout");
    }
    @Test
    public void test7(){
        loginToAdmin();

        clickApps("appearance");
        clickDocs("logotype");
        clickApps("catalog");
        clickDocs("attribute_groups","manufacturers","suppliers","delivery_statuses","sold_out_statuses","quantity_units","csv");
        clickApps("countries", "currencies", "customers");
        clickDocs("csv", "newsletter");
        clickApps("geo_zones", "languages");
        clickDocs("storage_encoding");
        clickApps("modules");
        clickDocs("shipping","payment","order","order_total","jobs");
        clickApps("orders");
        clickDocs("order_statuses");
        clickApps("pages");
        clickDocs("csv");
        clickApps("reports");
        clickDocs("most_sold_products", "most_shopping_customers");
        clickApps("settings");
        clickDocs("defaults","email","listings","customer_details","legal","images","checkout","advanced","security");
        clickApps("slides", "tax");
        clickDocs("tax_classes");
        clickApps("translations");
        clickDocs("scan", "csv");
        clickApps("users", "vqmods");
    }

    public void loginToAdmin(){
        String userNameElementXpath = "//input[@name='username']";
        String passwordElementXpath = "//input[@name='password']";
        String loginButtonXpath = "//button[@name='login']";
        String loginValue = "admin";
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.xpath(userNameElementXpath)).sendKeys(loginValue);
        driver.findElement(By.xpath(passwordElementXpath)).sendKeys(loginValue);
        driver.findElement(By.xpath(loginButtonXpath)).click();
    }
    public void clickApps(String ... params){
        List<String> list = Arrays.stream(params).toList();
        list.forEach(element -> driver.findElement(By.xpath("//*[@class='app' and @data-code='"+element+"']")).click());
    }

    public void clickDocs(String ... params){
        List<String> list = Arrays.stream(params).toList();
        list.forEach(element -> driver.findElement(By.xpath("//*[@class='doc' and @data-code='"+element+"']")).click());
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}