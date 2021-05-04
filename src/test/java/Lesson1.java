import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Lesson1 {
    private static WebDriver driver;

    @BeforeClass
    public void startDriver(){
        driver = new ChromeDriver();
    }
    @Test
    public void test1(){
        driver.get("http://www.google.com/");
        System.out.println("Chrome open successfully ");
    }

    @AfterClass
    public void stopDriver(){
        driver.quit();
    }
}