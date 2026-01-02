package DemoBlazeProject;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ContactAboutUsTest  extends BaseClass {

    WebDriverWait wait;

    // ============================================
    // TEST 1: CONTACT MODAL OPENS
    // ============================================
    @Test(priority = 1)
    public void contactModalOpens() {

        System.out.println("\nTEST 1: Contact Modal Opens");

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.findElement(By.linkText("Contact")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("exampleModal")));

        System.out.println("PASS: Contact modal opened successfully");
    }

    // ============================================
    // TEST 2: CONTACT FORM FIELDS PRESENT
    // ============================================
    @Test(priority = 2)
    public void contactFieldsPresence() {

        System.out.println("\nTEST 2: Contact Fields Presence");

        Assert.assertTrue(driver.findElement(By.id("recipient-email")).isDisplayed(),
                "Email field not visible");
        Assert.assertTrue(driver.findElement(By.id("recipient-name")).isDisplayed(),
                "Name field not visible");
        Assert.assertTrue(driver.findElement(By.id("message-text")).isDisplayed(),
                "Message field not visible");

        System.out.println("PASS: Email, Name & Message fields are visible");
    }

    // ============================================
    // TEST 3: SEND CONTACT MESSAGE
    // ============================================
    @Test(priority = 3)
    public void sendContactMessage() {

        System.out.println("\nTEST 3: Send Contact Message");

        driver.findElement(By.id("recipient-email")).sendKeys("test@mail.com");
        driver.findElement(By.id("recipient-name")).sendKeys("Christina Joy");
        driver.findElement(By.id("message-text")).sendKeys("Test contact message");

        driver.findElement(By.xpath("//button[text()='Send message']")).click();

        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();

        System.out.println("Alert Message: " + alert.getText());
        alert.accept();

        System.out.println("PASS: Contact message sent successfully");
    }

    // ============================================
    // TEST 4: ABOUT US MODAL OPENS
    // ============================================
    @Test(priority = 4)
    public void aboutUsModalOpens() {

        System.out.println("\nTEST 4: About Us Modal Opens");

        driver.findElement(By.linkText("About us")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("videoModal")));

        System.out.println("PASS: About Us modal opened successfully");
    }

    // ============================================
    // TEST 5: ABOUT US VIDEO LOADS
    // ============================================
    @Test(priority = 5)
    public void aboutUsVideoLoads() {

        System.out.println("\nTEST 5: About Us Video Loads");

        WebElement video = driver.findElement(By.id("example-video"));

        Assert.assertTrue(video.isDisplayed(), "Video not displayed");

        System.out.println("PASS: About Us video element is visible");
    }
}