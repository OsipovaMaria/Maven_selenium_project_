import com.google.common.collect.Ordering;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

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
        boolean a = red == green;
        Assert.assertEquals((red.equals(green)) && (green.equals(blue)), true);
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
        Assert.assertEquals(paramsMainPage.length, paramsProductPage.length);
        for(int i=0;i<paramsMainPage.length;i++){
            Assert.assertEquals(paramsMainPage[i], paramsProductPage[i]);
        }
    }

}
