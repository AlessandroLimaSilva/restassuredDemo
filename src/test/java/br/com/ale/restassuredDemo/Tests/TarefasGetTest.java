package br.com.ale.restassuredDemo.Tests;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features/tarefaGET.feature"},
        glue={
                "classpath:br.com.ale.restassuredDemo.StepDefinitions",
                "classpath:br.com.ale.restassuredDemo.Hooks"
        },
        tags={"@automatizado"},
        monochrome = true,
        strict = true,
        stepNotifications = true,
        plugin = {"pretty","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:","summary"}
)

public class TarefasGetTest {
}
