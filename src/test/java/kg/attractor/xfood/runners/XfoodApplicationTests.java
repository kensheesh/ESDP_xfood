package kg.attractor.xfood.runners;

import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static io.cucumber.junit.platform.engine.Constants.*;


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("stepdefs")
@ConfigurationParameters({
        @ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty"),
        @ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "html:data/cucumber/testUI.html"),
        @ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "src/test/resources/features"),
        @ConfigurationParameter(key = EXECUTION_DRY_RUN_PROPERTY_NAME, value = "true"),
})
@SpringBootTest
@CucumberContextConfiguration
class XfoodApplicationTests {
}
