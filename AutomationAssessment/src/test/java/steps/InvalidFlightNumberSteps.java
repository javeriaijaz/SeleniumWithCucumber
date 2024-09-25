package steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import pages.FlightNumberPage;
import pages.InvalidFlightNumberPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class InvalidFlightNumberSteps {
    WebDriver driver;
    InvalidFlightNumberPage invalidFlightNumberPage;

    @Before
    public void setUp() {
    	if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
        invalidFlightNumberPage = new InvalidFlightNumberPage(driver);
    }

    @Given("I open the flight number page")
    public void openFlightNumberStatusPage() {
        driver.get("https://www.eurowings.com/en/information/at-the-airport/flight-status.html");
        invalidFlightNumberPage.acceptCookies();
    }

    @When("I enter {string} as the Invalid flight number")
    public void enterFlightNumber(String flightNumber) {
        invalidFlightNumberPage.enterFlightNumber(flightNumber);
    }

    @When("I enter {string} as the travel date and click Show flight status by Invalid Flight Number")
    public void enterDateAndClickShowFlightStatusButton(String date) {
        invalidFlightNumberPage.enterDateAndClickShowStatusButton(date);
    }

    @Then("I should see an error message stating {string}")
    public void verifyInvalidFlightNumberMessage(String expectedMessage) {
        String actualMessage = invalidFlightNumberPage.getInvalidFlightNumberMessage();
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
