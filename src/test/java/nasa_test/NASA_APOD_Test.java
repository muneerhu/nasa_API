package nasa_test;

import org.junit.Assert;
import org.testng.annotations.Test;
import pojo.NASA_Data;
import utils.ConfigReader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class NASA_APOD_Test {
    String baseURI = ConfigReader.readProperty("baseURI", "src/test/resources/properties/env.properties");
    String API_KEY = ConfigReader.readProperty("api_key", "src/test/resources/properties/env.properties");
    public static final String CURRENT_DATE = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    /**
     * Sending a get request without api key and because it is a required query param
     * request denied.
     */
    @Test(description = "Test without API_key")
    public void test01(){
        given()
                .when()
                .get(baseURI)
                .then()
                .statusCode(403)
                .body("error.code", equalTo("API_KEY_MISSING"))
                .log().body();
    }

    @Test(description = "Test with valid API_key")
    public void test02(){
        given()
                .when()
                .queryParam("api_key",API_KEY)
                .get(baseURI)
                .then()
                .statusCode(200)
                .log().body();
    }

    /**
     * Note: for sending any request api key as query param is required but concept_tags, date, hdare optional
     */
    @Test(description = "Test with valid API_key and all other query params")
    public void test03(){
        given()
                .when()
                .queryParam("api_key", API_KEY)
                .queryParam("date", CURRENT_DATE)
                .queryParam("concept_tags", "true")
                .queryParam("hd", "true")
                .get(baseURI)
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test(description = "Test and validate the date as query param")
    public void test04(){
        given()
                .when()
                .queryParam("api_key", API_KEY)
                .queryParam("date", CURRENT_DATE)
                .get(baseURI)
                .then()
                .statusCode(200)
                .body("date", equalTo(CURRENT_DATE))
                .log().body();
    }

    @Test(description = "Test and validate with valid date but not current")
    public void test05(){
        given()
                .when()
                .queryParam("api_key", API_KEY)
                .queryParam("date", "2020-12-01")
                .get(baseURI)
                .then()
                .statusCode(200)
                .body("date", equalTo("2020-12-01"))
                .log().body();
    }

    @Test(description = "Test with invalid date as query param")
    public void test06(){
        given()
                .queryParam("api_key", API_KEY)
                .queryParam("date", "2021-12-01")
                .get(baseURI)
                .then()
                .statusCode(400)
                .log().body();
    }

    @Test(description = "Deserialization using pojo class")
    public void test07(){
        NASA_Data nasa_data = given()
                .queryParam("api_key", API_KEY)
                .get(baseURI)
                .as(NASA_Data.class);

        Assert.assertEquals(CURRENT_DATE, nasa_data.getDate());
        System.out.println("Explanation: " + nasa_data.getExplanation());
        System.out.println("Title: " + nasa_data.getTitle());
        System.out.println("hdurl: " + nasa_data.getHdurl());
        System.out.println("Url: " + nasa_data.getUrl());

    }

}
