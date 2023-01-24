package org.api.coop;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CoopAPITest {

    @Test
    public void OauthTest(){
        RestAssured.baseURI="http://coop.apps.symfonycasts.com/";
        given().queryParam("client_id","APILearning11")
                .queryParam("b0de0e730915b9c838d355adfb75cc9f")
                .queryParam("grant_type","authorization_code")
                .when().post("/token")
                .then().log().all();

        RestAssured.baseURI="http://coop.apps.symfonycasts.com/api";

        given().auth().oauth2("b1530b484fff874b4472c3a80e8642eb0e36b7d3")
                .when().post("/4258/barn-unlock")
                .then().log().all();

    }
}
