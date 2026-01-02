package DemoBlazeProject;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProductDetailPageTest extends BaseClass {

    private WebDriverWait wait;
    private String listProductName;
    private String listProductPrice;

    @BeforeMethod
    public void setup() {
       
        wait = new WebDriverWait(driver, Duration.ofSeconds(100));

    }

    @Test(priority = 1)
    public void openCategoryAndCaptureListDetails() throws InterruptedException {
        System.out.println("=== Step 1: Wait for categories and open Phones ===");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#itemc, .list-group-item")));

        WebElement phones = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Phones")));
        phones.click();
        System.out.println("Clicked Phones category");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".card-title a")));

        Thread.sleep(800);

        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,250)");

        WebElement firstProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".card-title a")));
        listProductName = firstProduct.getText().trim();

        WebElement priceElem = driver.findElement(By.cssSelector(".card-block h5, .card-body h5, .price"));
        listProductPrice = priceElem.getText().trim();

        System.out.println("Product Name From Listing: " + listProductName);
        System.out.println("Product Price From Listing: " + listProductPrice);
    }

    @Test(priority = 2)
    public void openProductDetailPage() {
        System.out.println("=== Step 2: Open Product Detail Page ===");

        WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".card-title a")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", productLink);
        productLink.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".name")));

        System.out.println("Opened Product Detail Page.");
    }

    @Test(priority = 3)
    public void verifyProductTitle() {
        System.out.println("=== Verify Product Title ===");
        String detailTitle = driver.findElement(By.cssSelector(".name")).getText().trim();
        System.out.println("Detail Page Title: " + detailTitle);
        Assert.assertEquals(detailTitle, listProductName, "Product title mismatch between list and detail page");
        System.out.println("Product title matches listing");
    }

    @Test(priority = 4)
    public void verifyProductPrice() {
        System.out.println("=== Verify Product Price ===");
        String detailPrice = driver.findElement(By.cssSelector(".price-container")).getText().trim();
        System.out.println("Detail Page Price: " + detailPrice);
        // detailPrice can contain currency sign and text, so verify it contains the numeric part
        String normalizedListPrice = listProductPrice.replaceAll("[^0-9.]", "");
        Assert.assertTrue(detailPrice.contains(normalizedListPrice),
                "Product price mismatch between list and detail page");
        System.out.println("Product price matches listing");
    }

    @Test(priority = 5)
    public void verifyProductDescription() {
        System.out.println("=== Verify Product Description ===");
        WebElement desc = driver.findElement(By.cssSelector("#more-information, #more-information + p, .description"));
        String text = desc.getText().trim();
        System.out.println("Description length: " + text.length());
        Assert.assertTrue(text.length() > 0, "Product description is missing or empty");
        System.out.println("Product description is present");
    }

    @Test(priority = 6)
    public void verifyProductImage() {
        System.out.println("=== Verify Product Image ===");
        WebElement img = driver.findElement(By.cssSelector("#imgp img, .carousel-inner img, img#imgp"));
        Assert.assertTrue(img.isDisplayed(), "Product image is not displayed");
        System.out.println("Product image is displayed");
    }

    @Test(priority = 7)
    public void verifyAddToCartButtonVisible() {
        System.out.println("=== Verify Add to cart Button Visibility ===");
        WebElement addBtn = driver.findElement(By.xpath("//a[normalize-space()='Add to cart' or contains(text(),'Add to cart')]"));
        Assert.assertTrue(addBtn.isDisplayed(), "Add to cart button is not visible");
        System.out.println(" Add to cart button is visible");
    }

    @Test(priority = 8)
    public void verifyAddToCartFunctionality() {
        System.out.println("=== Verify Add to cart Functionality ===");
        WebElement addBtn = driver.findElement(By.xpath("//a[normalize-space()='Add to cart' or contains(text(),'Add to cart')]"));
        addBtn.click();

        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            System.out.println("Alert text: " + alert.getText());
            Assert.assertTrue(alert.getText().toLowerCase().contains("product added"), "Unexpected alert text");
            alert.accept();
            System.out.println("Add to cart success alert accepted");
        } catch (TimeoutException te) {
            Assert.fail("Expected add-to-cart alert did not appear");
        }
    }

    @Test(priority = 9)
    public void verifyBackNavigation() throws InterruptedException {
        System.out.println("=== Verify Back Navigation ===");
        
        driver.navigate().back();
       System.out.println("Back Navigation Successful → Product list visible");

    }

    @AfterMethod
    public void tearDown() {
        System.out.println("===== PRODUCT DETAIL PAGE VALIDATION COMPLETED =====\n");
       
    }
}