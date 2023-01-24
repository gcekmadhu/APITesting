package org.test.api;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import resources.files.Paload;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

import io.restassured.RestAssured;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class BasicsAddPlaceAPI
{
    /**
     * Validate if Add Place API is working as expected.
     * Given: All details for API to submit API
     * when: hit and submit API
     * then: validate response
     */
    @Test
    public void basicTest()
    {

        RestAssured.baseURI="https://rahulshettyacademy.com";
        given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(Paload.addPlace()).when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP"))
                .header("Server",equalTo("Apache/2.4.41 (Ubuntu)"));
    }
    @Test
    public void extractResponse(){
        RestAssured.baseURI="https://rahulshettyacademy.com";
        String response= given().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(Paload.addPlace()).when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope",equalTo("APP"))
                .header("Server",equalTo("Apache/2.4.41 (Ubuntu)"))
                .extract().response().asString();
        System.out.println(response);
    }

    @Test
    public void extractAndParseResponse(){
        RestAssured.baseURI="https://rahulshettyacademy.com";
        String response= given().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(Paload.addPlace()).when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope",equalTo("APP"))
                .header("Server",equalTo("Apache/2.4.41 (Ubuntu)"))
                .extract().response().asString();
        System.out.println(response);
        JsonPath js=new JsonPath(response);
        String placeid=js.getString("place_id");
        System.out.println(placeid);
    }
    @Test
    //how to pass File in body
    public void extractAndParseResponseWithFile(){
        RestAssured.baseURI="https://rahulshettyacademy.com";
        String response= null;
        try {
            response = given().queryParam("key","qaclick123").header("Content-Type","application/json")
                    .body(new String(Files.readAllBytes(Paths.get("C:\\Users\\gcekm\\RestAssuredAPITestProjectJSON's\\AddPlace.json")))).when().post("maps/api/place/add/json")
                    .then().assertThat().statusCode(200).body("scope",equalTo("APP"))
                    .header("Server",equalTo("Apache/2.4.41 (Ubuntu)"))
                    .extract().response().asString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response);
        JsonPath js=new JsonPath(response);
        String placeid=js.getString("place_id");
        System.out.println(placeid);
    }
}
