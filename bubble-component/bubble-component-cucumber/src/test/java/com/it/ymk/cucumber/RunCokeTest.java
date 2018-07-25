package com.it.ymk.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * @author yanmingkun
 * @date 2017-12-14 17:36
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = { "src/test/resources/" },
    format = { "pretty", "html:target/cucumber", "json:target/cucumber.json" },
    glue = { "com.cucumber" })
public class RunCokeTest {

}
