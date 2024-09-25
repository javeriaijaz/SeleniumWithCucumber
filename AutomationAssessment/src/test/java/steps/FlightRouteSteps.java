package steps;

import pages.FlightRoutePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FlightRouteSteps {
    WebDriver driver;
    FlightRoutePage flightRoutePage;

    @Before
    public void setUp() {
    	if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
        flightRoutePage = new FlightRoutePage(driver);
    }

    @Given("I open the flight status page")
    public void openFlightStatusPage() {
        driver.get("https://www.eurowings.com/en/information/at-the-airport/flight-status.html");
        flightRoutePage.acceptCookies();
    }

    @When("I enter {string} as the departure airport")
    public void enterDepartureAirport(String departure) {
        flightRoutePage.enterDeparture(departure);
    }

    @When("I enter {string} as the destination airport")
    public void enterDestinationAirport(String destination) {
        flightRoutePage.enterDestination(destination);
    }

    @When("I enter {string} as the travel date and click Show flight status")
    public void enterDateAndClickShowStatusButton(String date) throws InterruptedException {
        flightRoutePage.enterDateAndClickShowStatusButton(date);
    }

    @Then("I should see the flight status for {string} to {string} on {string} with a status that is one of {string}")
    public void verifyFlightStatus(String departure, String destination, String date, String statuses) {
        List<String> statusList = Arrays.asList(statuses.split(",\\s*"));
        flightRoutePage.verifyFlightDetails(departure, destination, date, statusList);
    }
    

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null; 
        }
    }
}
