import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Lesson1 {
    private WebDriver driver;
    private final int timeout = 30;
    private final String BOX_CAMPAIGN_PRODUCTS = "//*[@id='box-campaign-products']";
    private final String BOX_PRODUCT = "//article[@id='box-product']";
    private final String CAMPAIGN_PRICE = "//strong[@class='campaign-price']";
    private final String REGULAR_PRICE = "//del[@class='regular-price']";
    private final By LOGOUT_LINK = By.xpath("//footer[@id='footer']//a[contains(@href,'logout')]");

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

    @Test
    public void test8() {
        Utils.openPage(driver, "http://localhost/litecart/");
        List<WebElement> imageBoxes = driver.findElements(By.xpath("//div[@class='image-wrapper']"));
        imageBoxes.forEach(box -> {
                    List<WebElement> stickers = box.findElements(By.xpath("./div[starts-with(@class,'sticker')]"));
                    Assert.assertEquals(stickers.size(), 1);
                }
        );
    }

    @Test
    public void test9() {
        Utils.loginAsAdminUser(driver, "http://localhost/litecart/admin/?app=countries&doc=countries");
        Utils.checkOrder(driver, "//div[@class='panel-body']//a[text()]", false);
        List<String> countriesToCheck = Utils.getCountriesNameWithZones(driver, "//td[@class='text-center']");
        countriesToCheck.forEach(name -> {
                    driver.findElement(By.xpath("//a[text()='" + name + "']")).click();
                    Utils.checkOrder(driver, "//input[contains(@name,'country_code')]/..", true);
                    driver.navigate().back();
                }
        );
        driver.findElement(By.xpath("//a[contains(@href,'geo_zones')]")).click();
        driver.findElement(By.xpath("//tbody//a")).click();
        Utils.checkOrder(driver, "//input[contains(@name,'country_code')]/..", true);
    }

    @Test
    public void test10() {
        Utils.openPage(driver, "http://localhost/litecart/");
        String productNameFromMainPage = Utils.getTextByXpath(driver, BOX_CAMPAIGN_PRODUCTS + "//h4");
        String[] paramsMainPage = Utils.getAndCheckParams(driver, BOX_CAMPAIGN_PRODUCTS + REGULAR_PRICE, BOX_CAMPAIGN_PRODUCTS + CAMPAIGN_PRICE);
        driver.findElement(By.xpath(BOX_CAMPAIGN_PRODUCTS + "//a")).click();
        String productNameFromProductPage = Utils.getTextByXpath(driver, BOX_PRODUCT + "//h1");
        String[] paramsProductPage = Utils.getAndCheckParams(driver, REGULAR_PRICE, CAMPAIGN_PRICE);
        Assert.assertEquals(productNameFromMainPage, productNameFromProductPage);
        Utils.compareParams(paramsMainPage, paramsProductPage);
    }

    @Test
    public void test11() {
        Utils.openPage(driver, "http://localhost/litecart/");
        String email = Utils.generateRandomEmail();
        Utils.createNewUser(driver, email);
        driver.findElement(LOGOUT_LINK).click();
        Utils.loginViaDropDownMenu(driver, email);
        driver.findElement(LOGOUT_LINK).click();
    }

    @Test
    public void test14() {
        Utils.loginAsAdminUser(driver, "http://localhost/litecart/admin/login.php");
        Utils.clickOnMenuItem(driver, MenuPage.COUNTRIES.getDataCode());
        driver.findElement(By.xpath("//tbody//td//a[@title='Edit']")).click();
        String oldWindowsSet = driver.getWindowHandle();
        List<String> linksToCheck = Utils.getLinksToCheck();
        linksToCheck.forEach(link -> Utils.clickToLinkAndCloseNewWindow(driver, oldWindowsSet, link));
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}