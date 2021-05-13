import com.google.common.collect.Ordering;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
    private static final By LINK_CATALOG_PAGE = By.xpath("//a[contains(@href,'app=catalog&doc=catalog')]");
    private static final By LINK_WITH_TEXT = By.xpath("//tbody//tr//a[text()]");
    private static final By BUTTON_ADD_NEW_PRODUCT = By.xpath("//*[contains(text(),'Add New Product')]");
    private static final By LINK_HREF_CONTAINS_GENERAL = By.xpath("//a[contains(@href,'general')]");
    private static final By ENABLE_BUTTON = By.xpath("//input[@type='radio' and @value='1']/..");
    private static final By INPUT_NAME = By.xpath("//input[contains(@name,'name')]");
    private static final By INPUT_CODE = By.xpath("//input[contains(@name,'code')]");
    private static final By INPUT_SKU = By.xpath("//input[contains(@name,'sku')]");
    private static final By INPUT_MPN = By.xpath("//input[contains(@name,'mpn')]");
    private static final By INPUT_GTIN = By.xpath("//input[contains(@name,'gtin')]");
    private static final By INPUT_TARIC = By.xpath("//input[contains(@name,'taric')]");
    private static final By INPUT_KEYWORDS = By.xpath("//input[contains(@name,'keywords')]");
    private static final By INPUT_DATE_VALID_FROM = By.xpath("//input[@name='date_valid_from']");
    private static final By INPUT_DATE_VALID_TO = By.xpath("//input[@name='date_valid_to']");
    private static final By LINK_HREF_CONTAINS_INFORMATION = By.xpath("//a[contains(@href,'information')]");
    private static final By LINK_HREF_CONTAINS_SHORT_DESCRIPTION = By.xpath("//input[contains(@name,'short_description')]");
    private static final By INPUT_CONTAINS_META_DESCRIPTION = By.xpath("//input[contains(@name,'meta_description')]");
    private static final By INPUT_CONTAINS_HEAD_TITLE = By.xpath("//input[contains(@name,'head_title')]");
    private static final By LINK_ATTRIBUTES = By.xpath("//a[text()='Attributes']");
    private static final By INPUT_CUSTOM_VALUE = By.xpath("//input[contains(@name,'custom_value')]");
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

    public static void checkProductAdded(WebDriver driver, List<String> nameProductsBeforeAddingNewProduct) {
        List<String> nameProductsAfterAddingNewProduct = Utils.getProductsNames(driver);
        nameProductsAfterAddingNewProduct.removeAll(nameProductsBeforeAddingNewProduct);
        Assert.assertNotNull(nameProductsAfterAddingNewProduct);
    }

    public static List<String> getProductsNames(WebDriver driver) {
        driver.findElement(LINK_CATALOG_PAGE).click();
        List<WebElement> products = driver.findElements(LINK_WITH_TEXT);
        List<String> nameProducts = new ArrayList<>();
        products.forEach(el -> nameProducts.add(el.getText()));
        return nameProducts;
    }

    public static void openAddProductPage(WebDriver driver, WebDriverWait wait) {
        WebElement addButton = driver.findElement(BUTTON_ADD_NEW_PRODUCT);
        wait.until(ExpectedConditions.visibilityOf(addButton));
        addButton.click();
    }

    public static void comliteCatalogPage(WebDriver driver, WebDriverWait wait) {
        driver.findElement(LINK_HREF_CONTAINS_GENERAL).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(INPUT_CODE));
        driver.findElement(ENABLE_BUTTON).click();
        driver.findElement(INPUT_NAME).sendKeys("Name");
        driver.findElement(INPUT_CODE).sendKeys("Code");
        driver.findElement(INPUT_SKU).sendKeys("sku");
        driver.findElement(INPUT_MPN).sendKeys("mpn");
        driver.findElement(INPUT_GTIN).sendKeys("gtin");
        driver.findElement(INPUT_TARIC).sendKeys("taric");
        driver.findElement(INPUT_KEYWORDS).sendKeys("keywords");
        driver.findElement(INPUT_DATE_VALID_FROM).sendKeys("05/02/2021");
        driver.findElement(INPUT_DATE_VALID_TO).sendKeys("05/19/2021");
        //driver.findElement(By.xpath("//input[contains(@name,'new_images')]")).sendKeys("C:\\xampp\\htdocs\\litecart\\images\\products\\Pen.jpg");
    }

    public static void comliteCatalogInformationPage(WebDriver driver, WebDriverWait wait) {
        driver.findElement(LINK_HREF_CONTAINS_INFORMATION).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(LINK_HREF_CONTAINS_SHORT_DESCRIPTION));
        driver.findElement(LINK_HREF_CONTAINS_SHORT_DESCRIPTION).sendKeys("Short description");
        driver.findElement(INPUT_CONTAINS_META_DESCRIPTION).sendKeys("Meta description");
        driver.findElement(INPUT_CONTAINS_HEAD_TITLE).sendKeys("Head title");
    }

    public static void comliteCatalogAttributesPage(WebDriver driver, WebDriverWait wait) {
        driver.findElement(LINK_ATTRIBUTES).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(INPUT_CUSTOM_VALUE));
        driver.findElement(INPUT_CUSTOM_VALUE).sendKeys("Code");
    }


}
