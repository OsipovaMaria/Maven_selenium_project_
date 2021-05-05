import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class Lesson1 {
    private WebDriver driver;
    private String userNameElementXpath = "//input[@name='username']";
    private String passwordElementXpath = "//input[@name='password']";
    private String loginButtonXpath = "//button[@name='login']";
    private String logoutLinkXpath = "//a[@title='Logout']";
    private String loginValue = "admin";
    private int timeout = 5;

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
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.xpath(userNameElementXpath)).sendKeys(loginValue);
        driver.findElement(By.xpath(passwordElementXpath)).sendKeys(loginValue);
        driver.findElement(By.xpath(loginButtonXpath)).click();
        assert(driver.findElement(By.xpath(logoutLinkXpath)).getText()).matches("Logout");
    }
    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}