package pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InvalidFlightNumberPage {
    WebDriver driver;
    WebDriverWait wait;

    By flightNumberRadioButton = By.xpath("//input[@value='FLIGHT_NUMBER']");
    By flightNumberInput = By.xpath("//input[@name='flightNumber']");
    By dateInput = By.xpath("//label[text()='Departure date']");
    By showStatusButton = By.xpath("//button[@type='submit']");
    By cookieConsentButton = By.xpath("//button[.//span[text()='I understand']]");
    By errorMessageLocator = By.xpath("//h2[contains(text(), 'Unfortunately, we could not find any results for your search.')]");

    public InvalidFlightNumberPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void acceptCookies() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(cookieConsentButton)).click();
        } catch (Exception e) {
            System.out.println("Cookie consent dialog not found or not clickable: " + e.getMessage());
        }
    }

    public void enterFlightNumber(String flightNumber) {
        wait.until(ExpectedConditions.elementToBeClickable(flightNumberRadioButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(flightNumberInput)).sendKeys(flightNumber);
    }

    public void enterDateAndClickShowStatusButton(String date) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateInput)).click();
        WebElement dateElement = driver.findElement(By.xpath("//input[@value='" + date + "']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", dateElement);
        wait.until(ExpectedConditions.elementToBeClickable(showStatusButton)).click();
    }

    public String getInvalidFlightNumberMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessageLocator)).getText();
    }
}
