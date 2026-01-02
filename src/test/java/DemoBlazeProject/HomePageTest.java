package DemoBlazeProject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class HomePageTest extends BaseClass {

    @Test(priority = 1)
    public void verifyHomePageTitle() {
        System.out.println("\n===== HOME PAGE VALIDATION STARTED =====");

        if (driver.getTitle().equals("STORE")) {
            System.out.println("Home Page Loaded Successfully");
        } else {
            System.out.println("Home Page Load Failed");
        }
    }

    @Test(priority = 2)
    public void verifyCategoriesVisible() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        System.out.println("=== Step 2: Verifying Product Categories ===");

        String[] categories = {"Phones", "Laptops", "Monitors"};

        for (String category : categories) {
            WebElement ct = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.linkText(category))
            );
            System.out.println("Category Visible: " + category + " → " + ct.isDisplayed());
        }
    }

    @Test(priority = 3)
    public void verifyProductListLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        System.out.println("=== Step 3: Verifying Product List ===");

        WebElement product = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#tbodyid .card"))
        );

        System.out.println("Product list loaded: " + product.isDisplayed());
    }

    @Test(priority = 4)
    public void verifyPaginationButtons() {

        System.out.println("=== Step 4: Verifying Pagination Buttons ===");

        WebElement nextBtn = driver.findElement(By.id("next2"));
        WebElement prevBtn = driver.findElement(By.id("prev2"));

        System.out.println("Next Button Visible: " + nextBtn.isDisplayed());
        System.out.println("Previous Button Visible: " + prevBtn.isDisplayed());
    }

    @Test(priority = 5)
    public void verifyNavigationMenu() {

        System.out.println("=== Step 5: Verifying Navigation Menu Items ===");

        WebElement home  = driver.findElement(By.xpath("/html/body/nav/div[1]/ul/li[1]/a"));
        WebElement contact = driver.findElement(By.xpath("/html/body/nav/div[1]/ul/li[2]/a"));
        WebElement about = driver.findElement(By.xpath("/html/body/nav/div[1]/ul/li[3]/a"));
        WebElement cart = driver.findElement(By.id("cartur"));
        WebElement login = driver.findElement(By.id("login2"));
        WebElement signup = driver.findElement(By.id("signin2"));

        System.out.println("Home Menu Visible: " + home.isDisplayed());
        System.out.println("Contact Menu Visible: " + contact.isDisplayed());
        System.out.println("About Us Menu Visible: " + about.isDisplayed());
        System.out.println("Cart Menu Visible: " + cart.isDisplayed());
        System.out.println("Login Menu Visible: " + login.isDisplayed());
        System.out.println("Signup Menu Visible: " + signup.isDisplayed());
    }

    @Test(priority = 6)
    public void verifySlidingImage() {

        System.out.println("=== Step 6: Verifying Carousel ===");

        WebElement slidingimage = driver.findElement(By.id("carouselExampleIndicators"));
        System.out.println("Carousel Visible: " + slidingimage.isDisplayed());
    }

    @Test(priority = 7)
    public void verifyResponsiveLayout() throws InterruptedException {

        System.out.println("=== Step 7: Checking Responsive Layout ===");

        driver.manage().window().setSize(new Dimension(400, 800));
        Thread.sleep(2000);

        if (driver.findElement(By.id("nava")).isDisplayed()) {
            System.out.println("Responsive Layout Working (Mobile View)");
        }

        driver.manage().window().maximize();

        System.out.println("===== HOME PAGE VALIDATION COMPLETED =====\n");
    }
}