package org.test.api;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;


import org.testng.Assert;
import org.testng.annotations.Test;
import resources.files.Paload;
import resources.utils.JsonUtility;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BasicsUpdatePlaceAPI {
    @Test
    public void addAndUpdatePlaceAPI(){
        RestAssured.baseURI="https://rahulshettyacademy.com";

        //Add Place
        String responseForAdd= given().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(Paload.addPlace()).when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope",equalTo("APP"))
                .header("Server",equalTo("Apache/2.4.41 (Ubuntu)"))
                .extract().response().asString();
        System.out.println(responseForAdd);
        JsonPath jsAdd= JsonUtility.rawToJSON(responseForAdd);
        String placeid=jsAdd.getString("place_id");
        System.out.println(placeid);

        //Update Place
        String address="4, Browning Road Coventry";
        String responseForUpdate=given().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(Paload.updatePlace(placeid,address)).when().put("maps/api/place/update/json")
                .then().assertThat().statusCode(200).extract().response().asString();
        System.out.println(responseForUpdate);
        JsonPath jsUpdate=JsonUtility.rawToJSON(responseForUpdate);
        String msgUpdate=jsUpdate.getString("msg");
        Assert.assertEquals("Address successfully updated",msgUpdate);

        //Get place
        Map map=new HashMap<String,String>();
        map.put("key","qaclick123");
        map.put("place_id",placeid);
        String responseForGet=given().queryParams(map).when().get("maps/api/place/get/json")
                .then().assertThat().statusCode(200).extract().response().asString();
        System.out.println(responseForGet);
        JsonPath jsGet=JsonUtility.rawToJSON(responseForGet);
        String latitude=jsGet.getString("location.latitude");
        System.out.println(latitude);
        String addressResp=jsGet.getString("address");
        Assert.assertEquals("address is same",address,addressResp);

    }

}
