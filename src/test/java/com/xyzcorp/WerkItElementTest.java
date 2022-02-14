package com.xyzcorp;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.List;


import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WerkItElementTest {

    static WebDriver driver;

   /* @BeforeAll
    static void before() {
        driver.get("https://staging.tiered-planet.net/werk-it");
        driver.manage().window().maximize();
    }*/

    @BeforeEach
    void setup() {
        driver = WebDriverManager.chromedriver().create();
        driver.get("https://staging.tiered-planet.net/werk-it");
        driver.manage().window().maximize();

    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void testRelativeLocators() throws IOException {

        driver.get("https://staging.tiered-planet.net/werk-it");
        List<WebElement> sections = driver.findElements(By.tagName("section"));
    }

    //int statusCode = response.getStatusCode();
    @Test

    public void VerifyThatPasswordAndLoginWork() {
        given()
                .relaxedHTTPSValidation()
                .when()
                .get("https://staging.tiered-planet.net/werk-it-back-end/login/admin/pilot")
                .then()
                .assertThat()
                .statusCode(200);

    }


    @Test
    public void VerifyThatPasswordAndLogindontWork() {
        given()
                .relaxedHTTPSValidation()
                .when()
                .get("https://staging.tiered-planet.net/werk-it-back-end/login/admin/pilot8")
                .then()
                .assertThat()
                .statusCode(401);
    }

    @Test
    public void CreateProfile() {
        //driver.get("https://staging.tiered-planet.net/werk-it");

        driver.findElement(By.linkText("Create a profile")).click();
        driver.findElement(By.name("firstName")).click();
        driver.findElement(By.name("firstName")).sendKeys("Fatou");
        driver.findElement(By.name("lastName")).click();
        driver.findElement(By.name("lastName")).sendKeys("Diouf");
        driver.findElement(By.name("email")).click();
        driver.findElement(By.name("email")).sendKeys("f@gmail.com");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).sendKeys("fatou00");
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).sendKeys("fatouspassword");
        driver.findElement(By.cssSelector("input:nth-child(6)")).click();
        assertThat(driver.switchTo().alert().getText()).isEqualTo("You logged in with fatou00 and fatouspassword");

    }


    @Test
    public void UserLogin() {

        driver.get("https://staging.tiered-planet.net/werk-it"); //open URL
        assertThat(driver.getTitle()).isEqualTo("React App");  // Valider que c'est le bon site
        driver.findElement(By.linkText("Home")).click();  // Ouvrir la home page
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).sendKeys("fatou00");  // Remplir avec le bon username
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).sendKeys("fatouspassword");  // Remplir avec le bon password
        driver.findElement(By.cssSelector("#login > input[type=submit]")).click();   // Submit to log in
    }

    @Test
    public void invalidUsername() {
        driver.get("https://staging.tiered-planet.net/werk-it");
        driver.findElement(By.linkText("Home")).click();
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).sendKeys("abcd");   // utiliser un login erroné
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).sendKeys("fatouspassword");
        driver.findElement(By.cssSelector("#login > input[type=submit]")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement alert = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.cssSelector("#root > div > div > div:nth-child(2) > p")));

        assertEquals("Login Failed", driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/p")).getText());

        //Display invalid username as error message
    }


    @Test
    public void invalidPassword() {
        driver.get("https://staging.tiered-planet.net/werk-it");
        driver.findElement(By.linkText("Home")).click();
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).sendKeys("fatou00");
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).sendKeys("fatou");
        driver.findElement(By.cssSelector("#login > input[type=submit]")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement alert = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.cssSelector("#root > div > div > div:nth-child(2) > p")));

        assertEquals("Login Failed", driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/p")).getText());

        //Display Invalid password as error message
    }

    @Test
    public void blankUsername() {
        driver.get("https://staging.tiered-planet.net/werk-it");
        driver.findElement(By.linkText("Home")).click();
        driver.findElement(By.name("username")).click();
        //  Field empty
        driver.findElement(By.name("username")).sendKeys("");
        driver.findElement(By.name("password")).click();
        //  Using an existing password
        driver.findElement(By.name("password")).sendKeys("fatou");
        driver.findElement(By.cssSelector("#login > input[type=submit]")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement alert = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.cssSelector("#root > div > div > div:nth-child(2) > p")));
        //Error message
        assertEquals("Login Failed", driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/p")).getText());

        // Changer le message d'erreur
    }

    @Test
    public void blankPassword() {
        driver.get("https://staging.tiered-planet.net/werk-it");
        driver.findElement(By.linkText("Home")).click();
        driver.findElement(By.name("username")).click();
        // Using an existing user
        driver.findElement(By.name("username")).sendKeys("fatou00");
        driver.findElement(By.name("password")).click();
        // Field empty
        driver.findElement(By.name("password")).sendKeys("");
        driver.findElement(By.cssSelector("#login > input[type=submit]")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement alert = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.cssSelector("#root > div > div > div:nth-child(2) > p")));
        //Error message
        assertEquals("Login Failed", driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/p")).getText());
        
    }

    @Test
    public void invalidUserNameAndPassword() {
        driver.get("https://staging.tiered-planet.net/werk-it");
        assertThat(driver.getTitle()).isEqualTo("React App");
        driver.findElement(By.linkText("Home")).click();
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).sendKeys("f");
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).sendKeys("fatou");
        driver.findElement(By.cssSelector("#login > input[type=submit]")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement alert = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.cssSelector("#root > div > div > div:nth-child(2) > p")));
        assertEquals("Login Failed", driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/p")).getText());

        // Change error message

    }


    @Test
    public void failedLoginAttempts3Times() {

        driver.findElement(By.linkText("Home")).click();

        for (int i = 0; i < 3; ++i) {

            driver.findElement(By.name("username")).click();
            driver.findElement(By.name("username")).sendKeys("fatou00");
            driver.findElement(By.name("password")).click();
            driver.findElement(By.name("password")).sendKeys("fatou");
            driver.findElement(By.cssSelector("#login > input[type=submit]")).click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            WebElement alert = wait.until(ExpectedConditions
                    .presenceOfElementLocated(By.cssSelector("#root > div > div > div:nth-child(2) > p")));

            assertEquals("Login Failed", driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/p")).getText());
            driver.findElement(By.name("username")).clear();
            driver.findElement(By.name("password")).clear();
        }

        //Then Message Displayed as Invalid Login
        //And       They cannot login for 24 hours
        //And       Remind the user that there is a link to recover password


    }


    @Test
    public void tesTPostUserFailed() {
        //"username": "brenjanth4023",
        //    "email": "brejanth@cgi.com",
        //    "firstName": "Brenjanth",
        //    "lastName": "Rajendran",
        //    "password": "2Dextreme!"
        JSONObject prfilebject = new JSONObject()
                .put("username", "cgi")
                .put("email", "cgi") // Adresse mail novalide
                .put("firstName", "cgi")
                .put("lastName", "cgi")
                .put("password", "cgi");
        given()
                .relaxedHTTPSValidation()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(prfilebject.toString())
                .when()
                .post("https://staging.tiered-planet.net/werk-it-back-end/register")
                .then()
                .assertThat()
                .statusCode(400); // un bug il accept les adress mail non valide
    }

    @Test
    public void tesTPostUserFailed2() {
        //"username": "brenjanth4023",
        //    "email": "brejanth@cgi.com",
        //    "firstName": "Brenjanth",
        //    "lastName": "Rajendran",
        //    "password": "2Dextreme!"
        JSONObject prfilebject = new JSONObject()
                .put("username", "")  // champs vide
                .put("email", "cgi@cgi.com")
                .put("firstName", "cgi")
                .put("lastName", "") // champs vide
                .put("password", "cgi");
        given()
                .relaxedHTTPSValidation()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(prfilebject.toString())
                .when()
                .post("https://staging.tiered-planet.net/werk-it-back-end/register")
                .then()
                .assertThat()
                .statusCode(400); // un bug il accept tous les vides
    }


    @Test
    public void testRegister() {
        //{"id":3,"email":"sally@aol.com","username":"sallys","firstName":"Sally","lastName":"Simpson","password":"point234"}
        given()
                .relaxedHTTPSValidation()
                .when()
                .get("https://staging.tiered-planet.net/werk-it-back-end/login/sallys/point234")
                .then()
                .assertThat()
                .body("id", equalTo(3))
                .body("email", equalTo("sally@aol.com"))
                .body("username", equalTo("sallys"))
                .body("firstName", equalTo("Sally"))
                .body("lastName", equalTo("Simpson"))
                .body("password", equalTo("point234"))
                .statusCode(200);
    }
}
















 /* @Test

    public void invalidUsername2() {
        driver.get("https://staging.tiered-planet.net/werk-it");
        driver.findElement(By.linkText("Home")).click();
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).sendKeys("abcd");
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).sendKeys("fatouspassword");
        driver.findElement(By.cssSelector("#login > input[type=submit]")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement alert = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.cssSelector(".error")));
        assertEquals("Login Failed", driver.findElement(By.cssSelector(".error")).getText());
    }*/












