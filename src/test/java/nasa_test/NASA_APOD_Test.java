package nasa_test;

import org.testng.annotations.Test;
import utils.ConfigReader;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class NASA_APOD_Test {
    String baseURI = ConfigReader.readProperty("baseURI", "src/test/resources/properties/env.properties");
    String API_KEY = ConfigReader.readProperty("api_key", "src/test/resources/properties/env.properties");

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

}
