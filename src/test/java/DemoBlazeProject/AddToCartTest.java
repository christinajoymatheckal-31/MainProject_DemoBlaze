package DemoBlazeProject;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class AddToCartTest extends BaseClass {

    WebDriverWait wait;
    String selectedProductName;
    String selectedProductPrice;

    @Test(priority = 1)
    public void openCategoryAndProduct() throws Exception {

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        System.out.println("\n===== ADD TO CART FUNCTIONALITY VALIDATION STARTED =====");

        // STEP 1: OPEN PHONES CATEGORY
        System.out.println("=== Step 1: Clicking Phones Category ===");
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Phones"))).click();

        // WAIT PRODUCT LIST
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".card-title a")));

        // FETCH FIRST PRODUCT
        WebElement firstProduct = driver.findElements(By.cssSelector(".card-title a")).get(0);
        selectedProductName = firstProduct.getText();

        selectedProductPrice = driver.findElements(By.cssSelector(".card-block h5")).get(0).getText();

        System.out.println("Selected Product From Listing:");
        System.out.println("Name  → " + selectedProductName);
        System.out.println("Price → " + selectedProductPrice);

        // Click product to open detail page
        firstProduct.click();
        System.out.println("Opened Product Detail Page.\n");
    }

    @Test(priority = 2)
    public void verifyAddToCartButtonVisible() {

        System.out.println("=== Step 2: Checking Add to cart button visibility ===");

        WebElement addBtn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Add to cart']"))
        );

        System.out.println("Add to Cart Button Visible: " + addBtn.isDisplayed());
    }

    @Test(priority = 3)
    public void addProductToCart() throws Exception {

        System.out.println("=== Step 3: Clicking Add to Cart ===");

        WebElement addBtn = driver.findElement(By.xpath("//a[text()='Add to cart']"));
        addBtn.click();

        // Wait for alert
        wait.until(ExpectedConditions.alertIsPresent());

        String alertMsg = driver.switchTo().alert().getText();
        System.out.println("Alert Message: " + alertMsg);

        if (alertMsg.contains("Product added")) {
            System.out.println("✔ Product Added Alert Verified");
        } else {
            System.out.println("✘ Wrong Alert Message");
        }

        driver.switchTo().alert().accept();
        System.out.println("Alert Accepted\n");
    }

    @Test(priority = 4)
    public void openCartPage() {

        System.out.println("=== Step 4: Opening Cart Page ===");

        driver.findElement(By.id("cartur")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));

        System.out.println("Cart Page Loaded");
    }

    @Test(priority = 5)
    public void verifyProductInCart() {

        System.out.println("=== Step 5: Verifying Product In Cart ===");

        List<WebElement> names = driver.findElements(By.xpath("//tr/td[2]"));
        List<WebElement> prices = driver.findElements(By.xpath("//tr/td[3]"));

        boolean found = false;

        for (int i = 0; i < names.size(); i++) {

            String name = names.get(i).getText();
            String price = prices.get(i).getText();

            if (name.equals(selectedProductName)) {

                System.out.println("Product Found in Cart");
                System.out.println("Cart Name  → " + name);
                System.out.println("Cart Price → " + price);

                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Product Not Found in Cart");
        }
    }

    @Test(priority = 6)
    public void verifyTotalCalculation() {

        System.out.println("=== Step 6: Verifying Cart Total Calculation ===");

        List<WebElement> priceList = driver.findElements(By.xpath("//tr/td[3]"));

        int sum = 0;
        for (WebElement p : priceList) {
            sum += Integer.parseInt(p.getText());
        }

        WebElement total = driver.findElement(By.id("totalp"));
        int shownTotal = Integer.parseInt(total.getText());

        System.out.println("Calculated Total: " + sum);
        System.out.println("Displayed Total : " + shownTotal);

        if (sum == shownTotal) {
            System.out.println("Cart Total Verification Passed");
        } else {
            System.out.println("Cart Total Verification Failed");
        }
    }

    @Test(priority = 7)
    public void addSameProductAgain() throws Exception {

        System.out.println("\n=== Step 7: Adding Same Product Again ===");

        driver.navigate().back(); // back to cart
        driver.navigate().back(); // back to product detail

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Add to cart']"))).click();

        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        driver.findElement(By.id("cartur")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));

        int rowCount = driver.findElements(By.xpath("//tr")).size();

        System.out.println("Rows in Cart After Adding Same Product: " + rowCount);

        if (rowCount >= 2) {
            System.out.println("Duplicate Product Added Successfully");
        } else {
            System.out.println("Duplicate Product NOT Added");
        }
    }

}
