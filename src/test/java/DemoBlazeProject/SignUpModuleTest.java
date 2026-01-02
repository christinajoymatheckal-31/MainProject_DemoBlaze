package DemoBlazeProject;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SignUpModuleTest extends BaseClass {

    WebDriverWait wait;

    // ============================================
    // TEST 1: SIGN UP MODAL OPENS
    // ============================================
    @Test(priority = 1)
    public void signUpModalOpens() {

        System.out.println("\nTEST 1: Sign Up Modal Opens");

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.findElement(By.id("signin2")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signInModal")));

        System.out.println("PASS: Sign Up modal opened successfully");
    }

    // ============================================
    // TEST 2: USERNAME & PASSWORD FIELDS PRESENT
    // ============================================
    @Test(priority = 2)
    public void usernamePasswordFieldsPresent() {

        System.out.println("\nTEST 2: Username & Password Fields Presence");

        WebElement username = driver.findElement(By.id("sign-username"));
        WebElement password = driver.findElement(By.id("sign-password"));

        Assert.assertTrue(username.isDisplayed(), "Username field not visible");
        Assert.assertTrue(password.isDisplayed(), "Password field not visible");

        System.out.println("PASS: Username & Password fields are visible");
    }

    // ============================================
    // TEST 3: EMPTY SIGN UP VALIDATION
    // ============================================
    @Test(priority = 3)
    public void emptySignUpValidation() {

        System.out.println("\nTEST 3: Empty Sign Up Validation");

        driver.findElement(By.xpath("//button[text()='Sign up']")).click();

        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();

        String alertText = alert.getText();
        System.out.println("Alert Message: " + alertText);

        Assert.assertEquals(
                alertText,
                "Please fill out Username and Password.",
                "Validation message mismatch"
        );

        alert.accept();
        System.out.println("PASS: Proper validation message displayed for empty input");
    }

    // ============================================
    // TEST 4: DUPLICATE USERNAME REGISTRATION
    // ============================================
    @Test(priority = 4)
    public void duplicateUsernameRegistration() {

        System.out.println("\nTEST 4: Duplicate Username Registration");

        driver.findElement(By.id("sign-username")).sendKeys("testuser");
        driver.findElement(By.id("sign-password")).sendKeys("test123");
        driver.findElement(By.xpath("//button[text()='Sign up']")).click();

        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();

        System.out.println("Alert Message: " + alert.getText());
        alert.accept();

        System.out.println("PASS: Duplicate username alert displayed");
    }

 // ============================================
 // TEST 5: SUCCESSFUL REGISTRATION (FIXED)
 // ============================================
 @Test(priority = 5)
 public void successfulRegistration() {

     System.out.println("\nTEST 5: Successful Registration");

     wait = new WebDriverWait(driver, Duration.ofSeconds(10));

     // Ensure modal is visible (do NOT click Sign Up again)
     wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signInModal")));

     // Clear old values
     driver.findElement(By.id("sign-username")).clear();
     driver.findElement(By.id("sign-password")).clear();

     // Create unique username
     String newUser = "user" + System.currentTimeMillis();

     driver.findElement(By.id("sign-username")).sendKeys(newUser);
     driver.findElement(By.id("sign-password")).sendKeys("password123");

     // Click Sign up
     driver.findElement(By.xpath("//button[text()='Sign up']")).click();

     // Handle alert
     wait.until(ExpectedConditions.alertIsPresent());
     Alert alert = driver.switchTo().alert();

     String alertText = alert.getText();
     System.out.println("Alert Message: " + alertText);

     alert.accept();

     Assert.assertTrue(
             alertText.toLowerCase().contains("sign up successful"),
             "Registration failed"
     );

     System.out.println("PASS: Successful registration completed");
 }
}

