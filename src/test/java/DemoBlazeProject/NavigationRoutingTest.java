package DemoBlazeProject;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NavigationRoutingTest extends BaseClass {

    WebDriverWait wait;

 // ============================================
 // TEST 1: HEADER NAVIGATION – HOME (FIXED)
 // ============================================
 @Test(priority = 1)
 public void headerNavigationHome() {

     wait = new WebDriverWait(driver, Duration.ofSeconds(10));
     System.out.println("\nTEST 1: Header Navigation – Home");

     // Click PRODUCT STORE logo (acts as Home)
     wait.until(ExpectedConditions.elementToBeClickable(By.id("nava"))).click();
     wait.until(ExpectedConditions.titleContains("STORE"));

     System.out.println("PASS: Home navigation works correctly via logo");
     Assert.assertTrue(driver.getTitle().contains("STORE"));
 }

    // ============================================
    // TEST 2: HEADER NAVIGATION – CART
    // ============================================
    @Test(priority = 2)
    public void headerNavigationCart() {

        System.out.println("\nTEST 2: Header Navigation – Cart");

        driver.findElement(By.id("cartur")).click();
        wait.until(ExpectedConditions.urlContains("cart"));

        System.out.println("PASS: Cart page loaded successfully");
        Assert.assertTrue(driver.getCurrentUrl().contains("cart"));
    }

    // ============================================
    // TEST 3: LOGO NAVIGATION
    // ============================================
    @Test(priority = 3)
    public void logoNavigationToHome() {

        System.out.println("\nTEST 3: Logo Navigation to Home");

        driver.findElement(By.id("nava")).click();
        wait.until(ExpectedConditions.titleContains("STORE"));

        System.out.println("PASS: Clicking logo returns to Home page");
        Assert.assertTrue(driver.getTitle().contains("STORE"));
    }

    // ============================================
    // TEST 4: URL BASED NAVIGATION
    // ============================================
    @Test(priority = 4)
    public void urlBasedNavigation() {

        System.out.println("\nTEST 4: URL Based Navigation");

        driver.navigate().to("https://demoblaze.com/cart.html");
        wait.until(ExpectedConditions.urlContains("cart"));

        System.out.println("PASS: Cart page opened via direct URL");
        Assert.assertTrue(driver.getCurrentUrl().contains("cart"));
    }

    // ============================================
    // TEST 5: BROWSER BACK NAVIGATION
    // ============================================
    @Test(priority = 5)
    public void browserBackNavigation() {

        System.out.println("\nTEST 5: Browser Back Navigation");

        driver.navigate().back();
        wait.until(ExpectedConditions.titleContains("STORE"));

        System.out.println("PASS: Browser back navigation works");
        Assert.assertTrue(driver.getTitle().contains("STORE"));
    }

    // ============================================
    // TEST 6: BROWSER FORWARD NAVIGATION
    // ============================================
    @Test(priority = 6)
    public void browserForwardNavigation() {

        System.out.println("\nTEST 6: Browser Forward Navigation");

        driver.navigate().forward();
        wait.until(ExpectedConditions.urlContains("cart"));

        System.out.println("PASS: Browser forward navigation works");
        Assert.assertTrue(driver.getCurrentUrl().contains("cart"));
    }

    // ============================================
    // TEST 7: BROKEN LINKS CHECK
    // ============================================
    @Test(priority = 7)
    public void brokenLinksCheck() {

        System.out.println("\nTEST 7: Broken Links Check");

        List<WebElement> links = driver.findElements(By.tagName("a"));
        int brokenLinks = 0;

        for (WebElement link : links) {
            String href = link.getAttribute("href");

            if (href == null || href.isEmpty()) {
                System.out.println("Broken link found (empty href)");
                brokenLinks++;
            }
        }

        if (brokenLinks == 0) {
            System.out.println("PASS: No broken links found");
        } else {
            System.out.println("FAIL: Broken links count = " + brokenLinks);
        }

        Assert.assertEquals(brokenLinks, 0);
        System.out.println("\n===== NAVIGATION & ROUTING TEST COMPLETED =====");
    }
}