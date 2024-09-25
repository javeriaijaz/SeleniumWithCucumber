package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class FlightStatusPastDatesPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By dateInput = By.xpath("//label[text()='Departure date']");
    private By cookieConsentButton = By.xpath("//button[.//span[text()='I understand']]");

    public FlightStatusPastDatesPage(WebDriver driver) {
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

    public void selectPastDate(String date) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateInput)).click();
        WebElement disabledDateElement = driver.findElement(By.xpath("//input[@type='radio' and contains(@class, 'calendar-date__input') and @aria-disabled='true' and contains(@aria-label, 'Unfortunately there are no flights available.')]"));
        // No need to click as the date is disabled, just extract the message
    }

    public String getErrorMessageForPastDate() {
        WebElement disabledDateElement = driver.findElement(By.xpath("//input[@aria-disabled='true']"));
        return disabledDateElement.getAttribute("aria-label");
    }
}
