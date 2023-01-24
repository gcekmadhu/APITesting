package org.api.library;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
public class AddBook {

    @Test
    public void addBookAPITest(){
        RestAssured.baseURI="https://rahulshettyacademy.com";

        String response=given().header("Content-Type","application/json")
                .body("{\n" +
                        "\n" +
                        "\"name\":\"Selenium\",\n" +
                        "\"isbn\":\"12\",\n" +
                        "\"aisle\":\"11110\",\n" +
                        "\"author\":\"Madhuri\"\n" +
                        "}\n").when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
        System.out.println(response);
        JsonPath js=new JsonPath(response);
        String id=js.getString("ID");
        System.out.println(id);
    }
}
