package DemoBlazeProject;

import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlaceOrderTest extends BaseClass {


    WebDriverWait wait;
    String confirmationText = "";

    // ===============================
    // 1. ADD PRODUCT TO CART
    // ===============================
    @Test(priority = 1)
    public void addProductToCart() {

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        System.out.println("\n===== PLACE ORDER MODULE TEST STARTED =====");

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Phones"))).click();

        WebElement product =
                wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".card-title a")));
        System.out.println("Selected Product: " + product.getText());
        product.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Add to cart"))).click();
        wait.until(ExpectedConditions.alertIsPresent()).accept();

        System.out.println("✔ Product added to cart");
    }

    // ===============================
    // 2. OPEN CART
    // ===============================
    @Test(priority = 2)
    public void openCart() {

        wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));

        System.out.println("✔ Cart page opened");
    }

    // ===============================
    // 3. OPEN PLACE ORDER MODAL
    // ===============================
    @Test(priority = 3)
    public void openPlaceOrderModal() {

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Place Order']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("orderModal")));

        System.out.println("✔ Place Order modal opened");
    }

    // ===============================
    // 4. VALID ORDER (PASS CASE)
    // ===============================
    @Test(priority = 4)
    public void validOrderPlacement() {

        driver.findElement(By.id("name")).sendKeys("Christina Joy");
        driver.findElement(By.id("country")).sendKeys("India");
        driver.findElement(By.id("city")).sendKeys("Kochi");
        driver.findElement(By.id("card")).sendKeys("12235654");
        driver.findElement(By.id("month")).sendKeys("11");
        driver.findElement(By.id("year")).sendKeys("2025");

        driver.findElement(By.xpath("//button[text()='Purchase']")).click();

        WebElement confirmBox =
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".sweet-alert.showSweetAlert.visible")));

        confirmationText = confirmBox.getText();
        System.out.println("\n✔ Order placed successfully");
        System.out.println(confirmationText);

        Assert.assertTrue(confirmationText.contains("Id:"), "Order confirmation not displayed");
    }

    // ===============================
    // 5. EXTRACT ORDER DETAILS
    // ===============================
    @Test(priority = 5)
    public void extractOrderDetails() {

        Pattern pattern = Pattern.compile(
                "Id: (\\d+)\\s+Amount: (.*?) USD\\s+Card Number: (.*?)\\s+Name: (.*?)\\s+Date: (.*)");

        Matcher matcher = pattern.matcher(confirmationText);

        if (matcher.find()) {
            System.out.println("Order ID: " + matcher.group(1));
            System.out.println("Amount: " + matcher.group(2));
            System.out.println("Card: " + matcher.group(3));
            System.out.println("Name: " + matcher.group(4));
            System.out.println("Date: " + matcher.group(5));
        } else {
            Assert.fail("❌ Unable to extract order details");
        }
    }

    // ===============================
    // 6. COMPLETE ORDER
    // ===============================
    @Test(priority = 6)
    public void completeOrder() {

        driver.findElement(By.xpath("//button[text()='OK']")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur"))).click();
        List<WebElement> items = driver.findElements(By.cssSelector("#tbodyid tr"));

        Assert.assertEquals(items.size(), 0, "❌ Cart not cleared after order");

        System.out.println("✔ Cart cleared after purchase");
    }

    // =====================================================
    // FAILURE CASES (DEFECT-BASED TESTING)
    // =====================================================

    // DF_CO_01 – Missing mandatory fields
    @Test(priority = 7)
    public void purchaseWithoutMandatoryFields() {

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Place Order']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("orderModal")));

        driver.findElement(By.xpath("//button[text()='Purchase']")).click();

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".sweet-alert.showSweetAlert.visible")));
            Assert.fail("DEFECT DF_CO_01: Order placed without mandatory fields");
        } catch (Exception e) {
            System.out.println("✔ Validation works (if fixed)");
        }
    }

    // DF_CO_02 – Invalid credit card accepted
    @Test(priority = 8)
    public void invalidCreditCardAccepted() {

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Place Order']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("orderModal")));

        driver.findElement(By.id("name")).sendKeys("Test User");
        driver.findElement(By.id("card")).sendKeys("abcd1234");
        driver.findElement(By.xpath("//button[text()='Purchase']")).click();

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".sweet-alert.showSweetAlert.visible")));
            Assert.fail("DEFECT DF_CO_02: Invalid credit card accepted");
        } catch (Exception e) {
            System.out.println("✔ Invalid card blocked (if fixed)");
        }
    }

    // DF_CO_03 – Multiple orders on rapid clicks
    @Test(priority = 9)
    public void multipleOrdersOnRapidClicks() throws Exception {

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Place Order']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("orderModal")));

        driver.findElement(By.id("name")).sendKeys("Rapid User");
        driver.findElement(By.id("card")).sendKeys("12235654");

        WebElement purchaseBtn = driver.findElement(By.xpath("//button[text()='Purchase']"));

        purchaseBtn.click();
        Thread.sleep(200);
        purchaseBtn.click();

        List<WebElement> alerts =
                driver.findElements(By.cssSelector(".sweet-alert.showSweetAlert.visible"));

        if (alerts.size() > 1) {
            Assert.fail("DEFECT DF_CO_03: Multiple confirmation alerts shown");
        }
    }

    // DF_CO_04 – Mobile view UI issue
    @Test(priority = 10)
    public void mobileViewPurchaseModalIssue() {

        driver.manage().window().setSize(new Dimension(375, 667));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Place Order']"))).click();
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("orderModal")));

        Assert.assertTrue(modal.isDisplayed(),
                "DEFECT DF_CO_04: Purchase modal not properly displayed in mobile view");

        System.out.println("⚠ Mobile UI defect observed manually");
    }
}