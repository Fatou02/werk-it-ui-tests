package com.xyzcorp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class PlayWithCGI {

    WebDriver driver;

    @BeforeEach
    void setup() {
        driver = WebDriverManager.chromedriver().create();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void testRelativeLocators() throws IOException {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/locators.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);


        driver.get(
                "https://www.cgi.com/en/search/site?keyword=business%20consulting");

        WebElement articleContainer = driver.findElement(By.cssSelector(properties.getProperty("article-head-css")));
        //sections inside the anchor
        List<WebElement> sections = articleContainer.findElements(By.tagName("section"));

        iterateAllSections(sections);
    }

    private void iterateAllSections(List<WebElement> sections) {
        //for every section
        for (WebElement element : sections) {
            //get the line items
            verifyTags(element);
            verifyTitle(element);
            verifyContent(element);
        }
    }

    private void verifyContent(WebElement element) {

    }

    private void verifyTitle(WebElement element) {

    }

    private void verifyTags(WebElement element) {
        List<WebElement> lineItems = element.findElements(By.cssSelector("div.tags > ul > li"));
        for (WebElement lineItem : lineItems) {
            System.out.println("******" + lineItem.getText());
        }
    }
}
