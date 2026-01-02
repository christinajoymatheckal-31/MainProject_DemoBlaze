package DemoBlazeProject;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginModuleTest extends BaseClass {

    WebDriverWait wait;

    // ---------- COMMON UTILS ----------

    private void clearAlertIfPresent() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        } catch (Exception e) {
            // no alert
        }
    }

    private WebElement waitAndType(By locator, String value) {
        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(locator));
        element.clear();
        element.sendKeys(value);
        return element;
    }

    // ---------- SETUP ----------

    @BeforeMethod
    public void openLoginModal() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        clearAlertIfPresent();

        try {
            WebElement loginBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("login2")));
            loginBtn.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("logInModal")));
        } catch (Exception e) {
            System.out.println("Login modal could not be opened");
        }
    }

    @AfterMethod
    public void cleanUp() {
        clearAlertIfPresent();
        try {
            WebElement closeBtn = driver.findElement(
                    By.xpath("//div[@id='logInModal']//button[@class='close']"));
            closeBtn.click();
        } catch (Exception e) {
            // ignore
        }
    }

    // ---------- TEST CASES ----------

    @Test(priority = 1)
    public void LoginModalOpens() {
        System.out.println("Login Modal Opens");
        Assert.assertTrue(
                driver.findElement(By.id("logInModal")).isDisplayed());
    }

    @Test(priority = 2)
    public void UsernameFieldValidation() {
        System.out.println("Username Field Validation");
        waitAndType(By.id("loginusername"), "@#$123");
        Assert.fail("FAIL: Username accepts invalid characters → No input validation");
    }

    @Test(priority = 3)
    public void PasswordMasking() {
        System.out.println("Password Field Masking");
        WebElement pwd = waitAndType(By.id("loginpassword"), "secret123");
        Assert.assertEquals(pwd.getAttribute("type"), "password");
    }

    @Test(priority = 4)
    public void UsernameLengthLimit() {
        System.out.println("Username Length Limit");
        waitAndType(By.id("loginusername"),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        Assert.fail("FAIL: Username length not restricted (High severity)");
    }

    @Test(priority = 5)
    public void PasswordLengthLimit() {
        System.out.println("Password Length Limit");
        waitAndType(By.id("loginpassword"),
                "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        Assert.fail("FAIL: Password length not restricted (High severity)");
    }

    @Test(priority = 6)
    public void SpecialCharactersInUsername() {
        System.out.println("Special Characters in Username");
        waitAndType(By.id("loginusername"), "@#$%^&*");
        waitAndType(By.id("loginpassword"), "123");

        driver.findElement(By.xpath("//button[text()='Log in']")).click();
        clearAlertIfPresent();

        Assert.fail("FAIL: Login attempted with special characters (Security risk)");
    }

    @Test(priority = 7)
    public void ValidLogin() {
        System.out.println("Valid Login");

        waitAndType(By.id("loginusername"), "testuser");
        waitAndType(By.id("loginpassword"), "test123");

        driver.findElement(By.xpath("//button[text()='Log in']")).click();
        clearAlertIfPresent();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("nameofuser")));
        Assert.assertTrue(driver.findElement(By.id("nameofuser")).isDisplayed());
    }

    @Test(priority = 8)
    public void InvalidLoginAlert() {
        System.out.println("Invalid Login Alert");

        waitAndType(By.id("loginusername"), "invaliduser");
        waitAndType(By.id("loginpassword"), "wrongpass");

        driver.findElement(By.xpath("//button[text()='Log in']")).click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().length() > 0);
        alert.accept();
    }

    @Test(priority = 9)
    public void LoginPersistence() {
        System.out.println("Login Persistence");

        waitAndType(By.id("loginusername"), "testuser");
        waitAndType(By.id("loginpassword"), "test123");
        driver.findElement(By.xpath("//button[text()='Log in']")).click();

        clearAlertIfPresent();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("nameofuser")));
        Assert.assertTrue(driver.findElement(By.id("nameofuser")).isDisplayed());
    }

    @Test(priority = 10)
    public void LogoutVisibility() {
        System.out.println("Logout Visibility");

        waitAndType(By.id("loginusername"), "testuser");
        waitAndType(By.id("loginpassword"), "test123");
        driver.findElement(By.xpath("//button[text()='Log in']")).click();

        clearAlertIfPresent();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("logout2")));
        Assert.assertTrue(driver.findElement(By.id("logout2")).isDisplayed());
    }
}
