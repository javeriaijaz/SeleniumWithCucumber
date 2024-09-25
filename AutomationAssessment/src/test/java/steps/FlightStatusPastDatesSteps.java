package steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import pages.FlightStatusPastDatesPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FlightStatusPastDatesSteps {
    private static WebDriver driver;
    private FlightStatusPastDatesPage flightStatusPastDatesPage;

    @Before
    public void setUp() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
        flightStatusPastDatesPage = new FlightStatusPastDatesPage(driver);
    }

    @Given("I open the flight status page for past dates")
    public void openFlightStatusPageForPastDates() {
        driver.get("https://www.eurowings.com/en/information/at-the-airport/flight-status.html");
        flightStatusPastDatesPage.acceptCookies();
    }

    @When("I enter {string} as the travel date for past dates")
    public void enterTravelDateForPastDates(String date) throws InterruptedException {
        flightStatusPastDatesPage.selectPastDate(date);
    }

    @Then("I should see an error message for past dates stating {string}")
    public void verifyErrorMessageForPastDates(String expectedMessage) {
        String Message = flightStatusPastDatesPage.getErrorMessageForPastDate();
     // Split the input based on the semicolon
        String[] parts = Message.split(";");

        // Check if the second part exists and return it trimmed, or return an empty string if it doesn't
        String actualMessage = parts.length > 1 ? parts[1].trim() : "";
        Assert.assertEquals("Error message did not match!", expectedMessage, actualMessage);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
