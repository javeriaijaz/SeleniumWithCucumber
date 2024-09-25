package steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import pages.FlightNumberPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FlightNumberSteps {
    private static WebDriver driver; // Make WebDriver static to share across instances
    private FlightNumberPage flightNumberPage;

    @Before
    public void setUp() {
        if (driver == null) { // Initialize the driver only if it's null
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
        flightNumberPage = new FlightNumberPage(driver);
    }

    @Given("I open the flight number status page")
    public void openFlightNumberStatusPage() {
        driver.get("https://www.eurowings.com/en/information/at-the-airport/flight-status.html");
        flightNumberPage.acceptCookies();
    }

    @When("I enter {string} as the flight number")
    public void enterFlightNumber(String flightNumber) {
        flightNumberPage.enterFlightNumber(flightNumber);
    }

    @When("I enter {string} as the travel date and click Show flight status by flight number")
    public void enterDateAndClickShowFlightStatusButtonByNumber(String date) throws InterruptedException {
        flightNumberPage.enterDateAndClickShowStatusButton(date);
    }

    @Then("I should see the flight status by flight number for {string} to {string} on {string} with a status that is one of {string}")
    public void verifyFlightStatusByNumber(String departure, String destination, String date, String statuses) {
        List<String> statusList = Arrays.asList(statuses.split(",\\s*"));
        flightNumberPage.verifyFlightDetails(departure, destination, date, statusList);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null; // Reset the driver to null to ensure a fresh session next time
        }
    }
}
