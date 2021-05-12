import com.google.common.collect.Ordering;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
    private static final String userNameElementXpath = "//input[@name='username']";
    private static final String passwordElementXpath = "//input[@name='password']";
    private static final String loginButtonXpath = "//button[@name='login']";
    private static final String logoutLinkXpath = "//a[@title='Logout']";
    private static final String loginValue = "admin";

    public static void openPage(WebDriver driver, String link) {
        driver.get(link);
    }

    public static void loginAsAdminUser(WebDriver driver, String link) {
        driver.get(link);
        driver.findElement(By.xpath(userNameElementXpath)).sendKeys(loginValue);
        driver.findElement(By.xpath(passwordElementXpath)).sendKeys(loginValue);
        driver.findElement(By.xpath(loginButtonXpath)).click();
        assert (driver.findElement(By.xpath(logoutLinkXpath)).getText()).matches("Logout");
    }

    public static WebElement getMenuLocator(WebDriver driver, String dataCode) {
        return driver.findElement(By.xpath("//*[@data-code='" + dataCode + "']"));
    }

    public static void clickOnMenuItem(WebDriver driver, String dataCode) {
        getMenuLocator(driver, dataCode).click();
    }

    public static List<String> getCountriesNameWithZones(WebDriver driver, String xPath) {
        List<WebElement> allCountries = driver.findElements(By.xpath(xPath));
        List<String> countriesWithZones = new ArrayList();
        allCountries.forEach(country -> {
            if (!country.getText().matches("0")) {
                WebElement link = country.findElement(By.xpath("./../td[a]"));
                countriesWithZones.add(link.getText());
            }
        });
        return countriesWithZones;
    }

    public static WebElement findElementByXPath(WebDriver driver, String xPath) {
        return driver.findElement(By.xpath(xPath));
    }

    public static String getTextByXpath(WebDriver driver, String xPath) {
        return findElementByXPath(driver, xPath).getText();
    }

    public static String getStyleValueByXpath(WebDriver driver, String xPath, String styleValue) {
        return findElementByXPath(driver, xPath).getCssValue(styleValue);
    }

    public static void checkOrder(WebDriver driver, String xPath, boolean expectedResult) {
        List<WebElement> elements = driver.findElements(By.xpath(xPath));
        List<String> listOfValues = new ArrayList();
        elements.forEach(element -> listOfValues.add(element.getText()));
        boolean sorted = Ordering.usingToString().isOrdered(listOfValues);
        Assert.assertEquals(sorted, expectedResult);
    }

    public static void compareElementsSize(WebDriver driver, String regularPriceLokator, String campaignPriceLokator) {
        String regularPriceSize = getStyleValueByXpath(driver, regularPriceLokator, "font-size");
        String campaignPriceSize = getStyleValueByXpath(driver, campaignPriceLokator, "font-size");
        String[] regularPriceSizeSplitted = regularPriceSize.split("\\.");
        String[] campaignPriceSizeSplitted = campaignPriceSize.split("\\.");
        Assert.assertEquals(Integer.parseInt(regularPriceSizeSplitted[0]) < Integer.parseInt(campaignPriceSizeSplitted[0]), true);
    }

    public static void checkRedColor(String expectedRedColor) {
        String[] color = expectedRedColor.split("\\(");
        color = color[1].split("\\,");
        String red = color[0].trim();
        String green = color[1].trim();
        String blue = color[2].trim();
        Assert.assertEquals(green, "0");
        Assert.assertEquals(blue, "0");
    }

    public static void checkGreyColor(String expectedGreyColor) {
        String[] color = expectedGreyColor.split("\\(");
        color = color[1].split("\\,");
        String red = color[0].trim();
        String green = color[1].trim();
        String blue = color[2].trim();
        // The digital value of red, green, and blue must match to produce gray color
        Assert.assertTrue((red.equals(green)) && (green.equals(blue)));
    }

    public static String[] getAndCheckParams(WebDriver driver, String regularPrice, String campaignPrice) {
        String regularPriceTextMainPage = Utils.getTextByXpath(driver, regularPrice);
        String campaignPriceTextMainPage = Utils.getTextByXpath(driver, campaignPrice);
        Utils.checkGreyColor(Utils.getStyleValueByXpath(driver, regularPrice, "color"));
        Utils.checkRedColor(Utils.getStyleValueByXpath(driver, campaignPrice, "color"));
        Utils.compareElementsSize(driver, regularPrice, campaignPrice);
        String[] params = new String[2];
        params[0] = regularPriceTextMainPage;
        params[1] = campaignPriceTextMainPage;
        return params;
    }

    public static void compareParams(String[] paramsMainPage, String[] paramsProductPage) {
        int lengthMainPage = paramsMainPage.length;
        int lengthProductPage = paramsProductPage.length;
        if (lengthMainPage == lengthProductPage) {
            for (int i = 0; i < paramsMainPage.length; i++) {
                Assert.assertEquals(paramsMainPage[i], paramsProductPage[i]);
            }
        } else {
            System.out.println("Parameters length for main page is not the same as product page. Please check it");
        }
    }

    public static void createNewUser(WebDriver driver, String email) {
        driver.findElement(By.xpath("//div[@class='columns']//a[contains(@href,'create_account')]")).click();
        driver.findElement(By.xpath("//input[@name='firstname']")).sendKeys("Maria");
        driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys("Maria");
        driver.findElement(By.xpath("//div[@class='row']//input[@name='email']")).sendKeys(email);
        driver.findElement(By.xpath("//div[@class='row']//input[@name='password']")).sendKeys("Maria");
        driver.findElement(By.xpath("//div[@class='row']//input[@name='confirmed_password']")).sendKeys("Maria");
        driver.findElement(By.xpath("//input[@name='newsletter']")).click();
        driver.findElement(By.xpath("//button[@name='create_account']")).click();
    }

    public static void loginViaDropDownMenu(WebDriver driver, String email) {
        driver.findElement(By.xpath("//a[text()=' Sign In ']")).click();
        driver.findElement(By.xpath("//ul[@class='dropdown-menu']//input[@name='email']")).sendKeys(email);
        driver.findElement(By.xpath("//ul[@class='dropdown-menu']//input[@name='password']")).sendKeys("Maria");
        driver.findElement(By.xpath("//ul[@class='dropdown-menu']//button")).click();
    }

    public static String generateRandomEmail() {
        DateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
        Date today = Calendar.getInstance().getTime();
        String todayDate = df.format(today);
        return "maria" + todayDate + "@mail.ru";
    }

    public static void clickToLinkAndCloseNewWindow(WebDriver driver, String currentWindowHandle, String xPathNewWindow) {
        driver.findElement(By.xpath(xPathNewWindow)).click();
        Set<String> newWindowsSet = driver.getWindowHandles();
        newWindowsSet.remove(currentWindowHandle);
        driver.switchTo().window(newWindowsSet.iterator().next());
        driver.close();
        driver.switchTo().window(currentWindowHandle);
    }

    public static List<String> getLinksToCheck() {
        List<String> linksToCheck = new ArrayList<>();
        linksToCheck.add("//a[contains(@href,'numeric')]");
        linksToCheck.add("//a[contains(@href,'alpha-2')]");
        linksToCheck.add("//a[contains(@href,'alpha-3')]");
        linksToCheck.add("//a[contains(@href,'Address')]");
        linksToCheck.add("//label[contains(text(),'Tax')]//a[contains(@href,'Regular')]");
        linksToCheck.add("//label[contains(text(),'Postcode')]//a[contains(@href,'Regular')]");
        linksToCheck.add("//a[contains(@href,'List_of_ISO')]");
        linksToCheck.add("//a[contains(@href,'List_of_countries')]");
        linksToCheck.add("//a[contains(@href,'calling_codes')]");
        return linksToCheck;
    }
}
