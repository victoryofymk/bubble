package com.it.ymk.cucumber.baidu;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author yanmingkun
 * @date 2017-12-14 17:50
 */
public class BaiduSearchStepfs {
    private static WebDriver driver;
    static {
        System.setProperty("webdriver.chrome.driver",
            "/Users/yanmingkun/IdeaProjects/bubble/bubble-component/bubble-component-cucumber/src/test/resources/chromedriver");
    }

    @Given("^Go to baidu home$")
    public void go_to_baidu_home() throws Exception {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://www.baidu.com/");
    }

    @When("^I find baidu logo")
    public WebElement i_find_baidu_logo() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait
            .until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='lg']/img"))));

        return element;
    }

    @And("^Type the search text \"([^\"]*)\"$")
    public void type_the_search_text(String searchText) throws Throwable {
        driver.findElement(By.id("kw")).clear();
        driver.findElement(By.id("kw")).sendKeys(searchText);
    }

    @And("^Click the submit$")
    public void click_the_submit() {
        driver.findElement(By.id("su")).click();
    }

    @Then("^Wait the query result")
    public void wait_the_query_result() throws InterruptedException {
        Thread.sleep(10000);
        Assert.assertEquals("selenium_百度搜索", driver.getTitle());
        driver.close();
        driver.quit();
    }
}
