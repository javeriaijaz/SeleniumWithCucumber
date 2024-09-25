package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlightNumberPage {
    WebDriver driver;
    WebDriverWait wait;

    By flightNumberRadioButton = By.xpath("//input[@value='FLIGHT_NUMBER']");
    By flightNumberInput = By.xpath("//input[@name='flightNumber']");
    By dateInput = By.xpath("//label[text()='Departure date']");
    By showStatusButton = By.xpath("//button[@type='submit']");
	By cookieConsentButton = By.xpath("//button[.//span[text()='I understand']]");

    public FlightNumberPage(WebDriver driver) {
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
        wait.until(ExpectedConditions.elementToBeClickable(flightNumberInput)).click();
        wait.until(ExpectedConditions.elementToBeClickable(flightNumberInput)).sendKeys(flightNumber);
    }

    public void enterDateAndClickShowStatusButton(String date) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateInput)).click();
        WebElement dateElement = driver.findElement(By.xpath("//input[@value='" + date + "']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", dateElement);
        
        WebElement showStatusButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button//span[text()='Show flight status']")));
    	showStatusButton.click();
        
        
    }

    public void verifyFlightDetails(String expectedDeparture, String expectedDestination, String expectedDate, List<String> acceptableStatuses) {
        List<WebElement> flightCards = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
            By.xpath("//div[@class='o-search-flight-status__card']")
        ));
        String actualDate = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@class='o-search-flight-status__date-navigation__date o-search-flight-status__date-navigation__date--active o-search-flight-status__date-navigation__date--align-center']//div")
            )).getText();
        
        String actDate = actualDate.substring(actualDate.indexOf(' ') + 1, actualDate.lastIndexOf('/'));
        // Convert "2024-08-09" to "09/08"
        String[] dateParts = expectedDate.split("-");
        String expDate = dateParts[2] + "/" + dateParts[1];
        
        for (WebElement flightCard : flightCards) {
            List<WebElement> routeParagraphs = flightCard.findElements(By.xpath(".//div[@class='o-search-flight-status__card-airports']//p"));
            String actualStatus = flightCard.findElement(By.xpath(".//div[contains(@class, 'o-search-flight-status__card-flight-status')]//p")).getText();

            assert routeParagraphs.get(0).getText().contains(expectedDeparture) && routeParagraphs.get(1).getText().contains(expectedDestination) : "Route validation failed!";
            assert expDate.equals(actDate) : "Date validation failed!" + actDate + expDate;
            assert acceptableStatuses.contains(actualStatus) : "Status validation failed! Status found: " + actualStatus;
        }
    }
}
