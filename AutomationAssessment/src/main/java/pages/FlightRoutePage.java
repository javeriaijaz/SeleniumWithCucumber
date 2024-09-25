package pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class FlightRoutePage {
	
    WebDriver driver;
    WebDriverWait wait;

    By departureButton = By.xpath("//button[.//span[text()='Departure airport']]");
    By departureInput = By.xpath("//input[@placeholder='Departure airport']");
    By locationOption = By.xpath("//span[text()='Germany']");
    By destinationButton = By.xpath("//button[.//span[text()='Destination airport']]");
    By destinationInput = By.xpath("//input[@placeholder='Destination airport']");
    By dateInput = By.xpath("//label[text()='Departure date']");
    By showStatusButton = By.xpath("/button//span[text()='Show flight status']");
    By resultInfo = By.className("flight-status__info");
    By cookieConsentButton = By.xpath("//button[.//span[text()='I understand']]");

    public FlightRoutePage(WebDriver driver) {
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

    public void enterDeparture(String departure) {
        wait.until(ExpectedConditions.elementToBeClickable(departureButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(departureInput)).sendKeys(departure);
        wait.until(ExpectedConditions.elementToBeClickable(locationOption)).click();
    }

    public void enterDestination(String destination) {
        wait.until(ExpectedConditions.elementToBeClickable(destinationButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(destinationInput)).sendKeys(destination);
        wait.until(ExpectedConditions.elementToBeClickable(locationOption)).click();
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
