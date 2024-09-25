package runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features", // Path to your feature files
    glue = {"steps"}, // Path to your step definitions
    plugin = {"pretty", "html:target/cucumber-reports"}, // Plugin for generating reports
    monochrome = true, // Display the console output in a proper readable format
    strict = true, // Treat undefined and pending steps as errors
    tags = "not @ignore" // Ensure only relevant tests are run
)
public class TestRunner {
    // This class is empty, used only as an entry point for the tests
}
