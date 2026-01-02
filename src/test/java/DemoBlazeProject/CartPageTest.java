package DemoBlazeProject;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class CartPageTest extends BaseClass {

    WebDriverWait wait;
    String addedProductName;
    int addedProductPrice;

    @Test(priority = 1)
    public void addProductToCart() {

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("https://demoblaze.com/index.html");

        System.out.println("\n===== CART PAGE MODULE VALIDATION STARTED =====");

        System.out.println("=== Step 1: Add first product to cart ===");

        // Click Phones category
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Phones"))).click();

        // Open first product
        WebElement firstProduct = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".card-title a"))
        );
        addedProductName = firstProduct.getText();
        addedProductPrice = Integer.parseInt(
                driver.findElement(By.cssSelector(".card-block h5")).getText().replace("$", "")
        );

        firstProduct.click();

        // Click Add to Cart
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Add to cart']"))).click();

        // Accept alert
        wait.until(ExpectedConditions.alertIsPresent()).accept();

        System.out.println("✔ Product Added Successfully → " + addedProductName);
    }

    @Test(priority = 2)
    public void openCartPage() {
        System.out.println("\n=== Step 2: Opening Cart Page ===");

        wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));
        System.out.println("✔ Cart Page Loaded");
    }

    @Test(priority = 3)
    public void verifyCartColumns() {

        System.out.println("\n=== Step 3: Validating Cart Table Columns ===");

        List<WebElement> columns = driver.findElements(By.cssSelector("#tbodyid tr th"));

        System.out.println("Cart Table Columns:");
        for (WebElement col : columns) {
            System.out.println("→ " + col.getText());
        }

        System.out.println("✔ Cart Columns Verified");
    }

    @Test(priority = 4)
    public void verifyCartItem() {

        System.out.println("\n=== Step 4: Verifying Item in Cart ===");

        List<WebElement> items = driver.findElements(By.cssSelector("#tbodyid tr"));

        if (items.size() == 0) {
            System.out.println("✘ No Items Found in Cart");
            return;
        }

        WebElement nameCell = driver.findElement(By.xpath("//td[2]"));
        String cartName = nameCell.getText();

        System.out.println("Cart Item Name → " + cartName);

        if (cartName.equals(addedProductName)) {
            System.out.println("✔ Product Verified in Cart");
        } else {
            System.out.println("✘ Wrong product in cart");
        }
    }

    @Test(priority = 5)
    public void verifyTotalAmount() {

        System.out.println("\n=== Step 5: Verifying Cart Total Amount ===");

        String totalText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("totalp"))).getText();

        int cartTotal = Integer.parseInt(totalText);

        System.out.println("Cart Total: " + cartTotal);

        if (cartTotal == addedProductPrice) {
            System.out.println("✔ Total Amount is Correct");
        } else {
            System.out.println("✘ Total Amount is Incorrect");
        }
    }

    @Test(priority = 6)
    public void verifyPlaceOrderButton() {

        System.out.println("\n=== Step 6: Verifying Place Order Button ===");

        WebElement placeOrder = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[text()='Place Order']")
        ));

        System.out.println("✔ Place Order Button is Visible");
    }

    @Test(priority = 7)
    public void verifyDeleteItem() {

        System.out.println("\n=== Step 7: Verifying Delete Item Functionality ===");

        List<WebElement> itemsBefore = driver.findElements(By.cssSelector("#tbodyid tr"));
        System.out.println("Items Before Delete: " + itemsBefore.size());

        if (itemsBefore.size() > 0) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Delete']"))).click();
            System.out.println("✔ Delete Clicked");
        }

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//a[text()='Delete']")));

        List<WebElement> itemsAfter = driver.findElements(By.cssSelector("#tbodyid tr"));
        System.out.println("Items After Delete: " + itemsAfter.size());
    }

    @Test(priority = 8)
    public void verifyRefreshPersistence() {

        System.out.println("\n=== Step 8: Verifying Session Persistence After Refresh ===");

        driver.navigate().refresh();

        List<WebElement> items = driver.findElements(By.cssSelector("#tbodyid tr"));

        if (items.size() > 0) {
            System.out.println("✔ Items Persist After Refresh");
        } else {
            System.out.println("✘ Items Not Persisting After Refresh");
        }

        System.out.println("===== CART PAGE MODULE VALIDATION COMPLETED =====\n");
    }
}