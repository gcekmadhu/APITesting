package org.api.oauth2concept;

import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import resources.utils.JsonUtility;

import static io.restassured.RestAssured.given;

public class OAuthTest {
    @Test
    public void generateAccessToken(){
        /*//get code
        WebDriver driver=new ChromeDriver();
        driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&redirect_uri=https://rahulshettyacademy.com/getCourse.php&response_type=code");

*/

        //get access token
        String responseAccess=given()
                .urlEncodingEnabled(false)
                .queryParam("code","4%2F0AWgavde8BSbpFZiJ7_wa7mBkXh28K-dBhLs3RN1wkw8UR52JvmytLmgoKtp_VKv3Y0Kuvg")
                .queryParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .queryParam("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
                .queryParam("grant_type","authorization_code")
                .when().post("https://www.googleapis.com/oauth2/v4/token")
                .then().log().all().extract().response().asString();
        JsonPath jsonPathAccess= JsonUtility.rawToJSON(responseAccess);
        String access_token= jsonPathAccess.get("access_token");
        System.out.println(access_token);

        //pass access token
        String response=given().
                queryParam("access_token",access_token)
                .when().get("https://rahulshettyacademy.com/getCourse.php").asString();
        System.out.println(response);

    }
}
