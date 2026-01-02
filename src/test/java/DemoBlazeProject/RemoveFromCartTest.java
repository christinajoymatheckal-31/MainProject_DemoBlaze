package DemoBlazeProject;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class RemoveFromCartTest extends BaseClass {

    WebDriverWait wait;
    String productName;
    int productPrice;

    // ==========================================================
    // 1. OPEN HOMEPAGE + ADD 2 PRODUCTS TO CART
    // ==========================================================
    @Test(priority = 1)
    public void addProductsToCart() throws Exception {

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("https://demoblaze.com/index.html");

        System.out.println("\n===== REMOVE FROM CART MODULE STARTED =====");

        // STEP 1 → Add Phones category product
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Phones"))).click();
        System.out.println("Opened Phones category");

        WebElement firstProduct = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".card-title a"))
        );

        productName = firstProduct.getText();
        String priceText = driver.findElement(By.cssSelector(".card-block h5")).getText();
        productPrice = Integer.parseInt(priceText.replace("$", "").trim());

        firstProduct.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Add to cart']"))).click();
        wait.until(ExpectedConditions.alertIsPresent()).accept();

        System.out.println("✔ First product added: " + productName);

        // Go Back & add 2nd product for removal testing
        driver.get("https://demoblaze.com/index.html");
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Laptops"))).click();

        WebElement laptop = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".card-title a"))
        );

        laptop.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Add to cart']"))).click();
        wait.until(ExpectedConditions.alertIsPresent()).accept();

        System.out.println("✔ Second product added (Laptop)");
    }

    // ==========================================================
    // 2. OPEN CART PAGE
    // ==========================================================
    @Test(priority = 2)
    public void openCart() {

        System.out.println("\n=== Step 2: Opening Cart Page ===");

        wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));

        System.out.println("✔ Cart page opened");
    }

    // ==========================================================
    // 3. REMOVE FIRST PRODUCT
    // ==========================================================
    @Test(priority = 3)
    public void removeSingleItem() throws Exception {

        System.out.println("\n=== Step 3: Removing Single Item from Cart ===");

        List<WebElement> deleteBtns = driver.findElements(By.xpath("//a[text()='Delete']"));

        if (deleteBtns.size() > 0) {
            deleteBtns.get(0).click();
            System.out.println("✔ First item removed");
        } else {
            System.out.println("✘ No delete buttons found");
        }

        Thread.sleep(2000);
    }

    // ==========================================================
    // 4. REMOVE ALL ITEMS ONE BY ONE
    // ==========================================================
    @Test(priority = 4)
    public void removeAllItems() throws Exception {

        System.out.println("\n=== Step 4: Removing ALL Items One by One ===");

        List<WebElement> deleteBtns = driver.findElements(By.xpath("//a[text()='Delete']"));

        while (deleteBtns.size() > 0) {
            deleteBtns.get(0).click();
            Thread.sleep(1500);
            deleteBtns = driver.findElements(By.xpath("//a[text()='Delete']"));
        }

        System.out.println("✔ All items removed successfully");
    }

    // ==========================================================
    // 5. VERIFY TOTAL AFTER REMOVAL
    // ==========================================================
    @Test(priority = 5)
    public void verifyTotalAfterRemoval() {

        System.out.println("\n=== Step 5: Verifying Total After Removal ===");

        String total = driver.findElement(By.id("totalp")).getText();

        if (total.equals("") || total.equals("0")) {
            System.out.println("✔ Total cleared after removal");
        } else {
            System.out.println("✘ Total NOT reset: " + total);
        }
    }

    // ==========================================================
    // 6. VERIFY EMPTY CART MESSAGE
    // ==========================================================
    @Test(priority = 6)
    public void verifyEmptyCart() {

        System.out.println("\n=== Step 6: Verifying Empty Cart ===");

        List<WebElement> rows = driver.findElements(By.cssSelector("#tbodyid tr"));

        if (rows.size() == 0) {
            System.out.println("✔ Cart is empty");
        } else {
            System.out.println("✘ Cart still has items");
        }
    }

    // ==========================================================
    // 7. USER CAN CONTINUE SHOPPING
    // ==========================================================
    @Test(priority = 7)
    public void verifyContinueShopping() {

        System.out.println("\n=== Step 7: Verifying Continue Shopping ===");

        driver.findElement(By.id("nava")).click(); // click STORE logo

        String title = driver.getTitle();

        if (title.equals("STORE")) {
            System.out.println("✔ User returned to homepage");
        } else {
            System.out.println("✘ User NOT returned to homepage");
        }

        System.out.println("\n===== REMOVE FROM CART MODULE COMPLETED =====");
    }
}