package DemoBlazeProject;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseClass {

	public static WebDriver driver;
    public static JavascriptExecutor js;

    @BeforeClass
    public void launch() {
        System.out.println("===== Test Initialization Started =====");
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        driver.get("https://demoblaze.com/index.html");
        System.out.println("Browser Launched & URL Loaded");
    }

    @AfterClass
    public void close() {
        driver.quit();
        System.out.println("===== Browser Closed Successfully =====");
    }
}
