package base;
import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.common.HomePage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class BaseTest {
    public static final String baseURL = "https://www.amazon.com/";

    public WebDriver driver;
    protected HomePage homePage;

    @BeforeMethod

    public void setUp() {
        try {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--disable-dev-shm-usage");
            driver = new RemoteWebDriver(new URL("http://localhost:4444"), chromeOptions);
            driver.manage().window().maximize();
            goHome();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void goHome(){
        driver.get(baseURL);
        homePage = new HomePage(driver);
    }



    @AfterClass
    public void tearDown(){
        driver.quit();
    }


    @AfterMethod
    public void recordFailure(ITestResult result){
        if(ITestResult.FAILURE == result.getStatus())
        {
            var camera = (TakesScreenshot)driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            try{
                Files.move(screenshot, new File("src/screenshots" +  result.getName() + ".png"));
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }






}
