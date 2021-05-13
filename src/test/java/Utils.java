import com.google.common.collect.Ordering;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;


public class Utils {
    private static final By LINK_CREATE_ACCOUNT = By.xpath("//div[@class='columns']//a[contains(@href,'create_account')]");
    private static final By INPUT_FIRSTNAME = By.xpath("//input[@name='firstname']");
    private static final By INPUT_LASTNAME = By.xpath("//input[@name='lastname']");
    private static final By INPUT_EMAIL_DIV_CLASS_ROW = By.xpath("//div[@class='row']//input[@name='email']");
    private static final By INPUT_PASSWORD = By.xpath("//div[@class='row']//input[@name='password']");
    private static final By INPUT_CONFIRMED_PASSWORD = By.xpath("//div[@class='row']//input[@name='confirmed_password']");
    private static final By INPUT_NEWSLRTTER = By.xpath("//input[@name='newsletter']");
    private static final By BUTTON_CREATE_ACCOUNT = By.xpath("//button[@name='create_account']");
    private static final By LINK_SIGN_IN = By.xpath("//a[text()=' Sign In ']");
    private static final By INPUT_EMAIL_DIV_CLASS_DROPDOWN_MENU = By.xpath("//ul[@class='dropdown-menu']//input[@name='email']");
    private static final By INPUT_PASSWORD_DIV_CLASS_DROPDOWN_MENU = By.xpath("//ul[@class='dropdown-menu']//input[@name='password']");
    private static final By BUTTON_UL_CLASS_DROPDOWN_MENU = By.xpath("//ul[@class='dropdown-menu']//button");
    private static final String XPATH_LINK_HREF_CONTAINS_NUMERIC = "//a[contains(@href,'numeric')]";
    private static final String XPATH_LINK_HREF_CONTAINS_ALPHA_2 = "//a[contains(@href,'alpha-2')]";
    private static final String XPATH_LINK_HREF_CONTAINS_ALPHA_3 = "//a[contains(@href,'alpha-3')]";
    private static final String XPATH_LINK_HREF_CONTAINS_ADDRESS = "//a[contains(@href,'Address')]";
    private static final String XPATH_LINK_HREF_CONTAINS_REGULAR_LABEL_TAX = "//label[contains(text(),'Tax')]//a[contains(@href,'Regular')]";
    private static final String XPATH_LINK_HREF_CONTAINS_REGULAR_LABEL_POSTCODE = "//label[contains(text(),'Postcode')]//a[contains(@href,'Regular')]";
    private static final String XPATH_LINK_HREF_CONTAINS_LIST_OF_ISO = "//a[contains(@href,'List_of_ISO')]";
    private static final String XPATH_LINK_HREF_CONTAINS_LIST_OF_COUNTRIES = "//a[contains(@href,'List_of_countries')]";
    private static final String XPATH_LINK_HREF_CONTAINS_CALLING_CODES = "//a[contains(@href,'calling_codes')]";
    private static final String userNameElementXpath = "//input[@name='username']";
    private static final String passwordElementXpath = "//input[@name='password']";
    private static final String loginButtonXpath = "//button[@name='login']";
    private static final String logoutLinkXpath = "//a[@title='Logout']";
    private static final String loginValue = "admin";
    private static final String mariaValue = "Maria";

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
        driver.findElement(LINK_CREATE_ACCOUNT).click();
        driver.findElement(INPUT_FIRSTNAME).sendKeys(mariaValue);
        driver.findElement(INPUT_LASTNAME).sendKeys(mariaValue);
        driver.findElement(INPUT_EMAIL_DIV_CLASS_ROW).sendKeys(email);
        driver.findElement(INPUT_PASSWORD).sendKeys(mariaValue);
        driver.findElement(INPUT_CONFIRMED_PASSWORD).sendKeys(mariaValue);
        driver.findElement(INPUT_NEWSLRTTER).click();
        driver.findElement(BUTTON_CREATE_ACCOUNT).click();
    }

    public static void loginViaDropDownMenu(WebDriver driver, String email) {
        driver.findElement(LINK_SIGN_IN).click();
        driver.findElement(INPUT_EMAIL_DIV_CLASS_DROPDOWN_MENU).sendKeys(email);
        driver.findElement(INPUT_PASSWORD_DIV_CLASS_DROPDOWN_MENU).sendKeys("Maria");
        driver.findElement(BUTTON_UL_CLASS_DROPDOWN_MENU).click();
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
        linksToCheck.add(XPATH_LINK_HREF_CONTAINS_NUMERIC);
        linksToCheck.add(XPATH_LINK_HREF_CONTAINS_ALPHA_2);
        linksToCheck.add(XPATH_LINK_HREF_CONTAINS_ALPHA_3);
        linksToCheck.add(XPATH_LINK_HREF_CONTAINS_ADDRESS);
        linksToCheck.add(XPATH_LINK_HREF_CONTAINS_REGULAR_LABEL_TAX);
        linksToCheck.add(XPATH_LINK_HREF_CONTAINS_REGULAR_LABEL_POSTCODE);
        linksToCheck.add(XPATH_LINK_HREF_CONTAINS_LIST_OF_ISO);
        linksToCheck.add(XPATH_LINK_HREF_CONTAINS_LIST_OF_COUNTRIES);
        linksToCheck.add(XPATH_LINK_HREF_CONTAINS_CALLING_CODES);
        return linksToCheck;
    }
}
