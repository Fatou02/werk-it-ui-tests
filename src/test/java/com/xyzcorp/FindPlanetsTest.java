package com.xyzcorp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FindPlanetsTest {

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
    void testRelativeLocators() {
        driver.get(
                "https://en.wikipedia.org/wiki/Solar_System");

        List<WebElement> captions = driver.findElements(By.tagName("caption"));
        for (WebElement element: captions) {
            System.out.println(">>>" + element);
        }
    }

}
