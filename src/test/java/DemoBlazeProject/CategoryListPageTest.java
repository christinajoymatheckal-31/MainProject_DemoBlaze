package DemoBlazeProject;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class CategoryListPageTest extends BaseClass {

    @Test
    public void verifyCategoryListingPages() throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        System.out.println("\n===== CATEGORY LISTING PAGE VALIDATION STARTED =====\n");

        String[] categories = {"Phones", "Laptops", "Monitors"};

        for (String category : categories) {

            System.out.println("\n---- Checking Category: " + category + " ----");

            wait.until(ExpectedConditions.elementToBeClickable(By.linkText(category))).click();
            Thread.sleep(2000);

            List<WebElement> products = driver.findElements(By.cssSelector("#tbodyid .col-lg-4.col-md-6.mb-4"));
            System.out.println("Products under " + category + ": " + products.size());

            if (products.isEmpty()) {
                System.out.println("No products found!");
                continue;
            }

            // ====== GET FIRST PRODUCT FRESH EACH TIME BEFORE READING ======
            WebElement freshFirstProduct = driver.findElements(
                    By.cssSelector("#tbodyid .col-lg-4.col-md-6.mb-4")).get(0);

            // IMAGE
            System.out.println("Image Visible: " +
                    freshFirstProduct.findElement(By.cssSelector(".card-img-top")).isDisplayed()
            );

            // NAME
            String productName = freshFirstProduct.findElement(By.cssSelector(".card-title")).getText();
            System.out.println("Product Name: " + productName);

            // PRICE
            String productPrice = freshFirstProduct.findElement(By.cssSelector("#tbodyid > div:nth-child(1) > div > div > h5")).getText();
            System.out.println("Product Price: " + productPrice);

            // CLICK PRODUCT -> MUST RE-LOCATE FRESH AGAIN
            System.out.println("Opening Product Detail Page...");
            driver.findElements(By.cssSelector("#tbodyid .card-title a")).get(0).click();

            // Wait for detail page
            WebElement detailTitle = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".name"))
            );
            System.out.println("Product Detail Page Loaded: " + detailTitle.getText());

            // Go back
            driver.navigate().back();
            Thread.sleep(2000);
        }


        // ================= PAGINATION =======================
        System.out.println("\n===== VALIDATING PAGINATION =====");

        WebElement nextButton = driver.findElement(By.id("next2"));
        WebElement prevButton = driver.findElement(By.id("prev2"));

        System.out.println("Next Button Visible: " + nextButton.isDisplayed());
        System.out.println("Previous Button Visible: " + prevButton.isDisplayed());

        nextButton.click();
        Thread.sleep(2000);

        List<WebElement> nextPageProducts = driver.findElements(By.cssSelector("#tbodyid .col-lg-4.col-md-6.mb-4"));
        System.out.println("Products after Next pagination: " + nextPageProducts.size());

        prevButton.click();
        Thread.sleep(2000);

        List<WebElement> prevPageProducts = driver.findElements(By.cssSelector("#tbodyid .col-lg-4.col-md-6.mb-4"));
        System.out.println("Products after Previous pagination: " + prevPageProducts.size());

        // ================= Smooth Navigation =================
        System.out.println("\n===== SMOOTH NAVIGATION =====");

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Phones"))).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Laptops"))).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Monitors"))).click();
        Thread.sleep(1000);

        System.out.println("\n===== CATEGORY LISTING PAGE VALIDATION COMPLETED =====\n");
    }
}