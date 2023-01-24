package org.api.jira;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import resources.utils.JsonUtility;

import static io.restassured.RestAssured.given;

public class E2EJIRA {
    String cookieName="";
    String cookieValue="";
    String jsessionid="";
    @Test
    public void generateCookieWithSessionFilter(){
        RestAssured.baseURI="http://localhost:8080";
        SessionFilter sessionFilter=new SessionFilter();
        String cookieResp=given().header("Content-Type","application/json").body("{\"username\": \"gcek.madhu\", \"password\": \"Ashish!1289\"}")
                .filter(sessionFilter).when().post("/rest/auth/1/session")
                .then().assertThat().statusCode(200).extract().response().asString();
        JsonPath jpCookie= JsonUtility.rawToJSON(cookieResp);
        cookieName=jpCookie.getString("session.name");
        cookieValue=jpCookie.getString("session.value");
        System.out.println(cookieName+" "+cookieValue);
        given().log().all().pathParam("issueId","10100").header("Content-Type","application/json")
                .queryParam(cookieName,cookieValue)
                .body("{\n" +
                        "    \"body\": \"Comments from Rest API Automation\",\n" +
                        "    \"visibility\": {\n" +
                        "        \"type\": \"role\",\n" +
                        "        \"value\": \"Administrators\"\n" +
                        "    }\n" +
                        "}")
                .filter(sessionFilter).when().post("/rest/api/2/issue/{issueId}/comment")
                .then().log().all();
    }

    @Test
    public void generateCookieWithoutSessionFilter(){
        RestAssured.baseURI="http://localhost:8080";
        //SessionFilter sessionFilter=new SessionFilter();
        String cookieResp=given().header("Content-Type","application/json")
        .body("{\"username\": \"gcek.madhu\", \"password\": \"Ashish!1289\"}")
                .when().post("/rest/auth/1/session")
                .then().assertThat().statusCode(200).extract().response().asString();
        JsonPath jpCookie= JsonUtility.rawToJSON(cookieResp);
        cookieName=jpCookie.getString("session.name");
        cookieValue=jpCookie.getString("session.value");
        jsessionid=cookieName+"="+cookieValue;
        System.out.println(cookieName+"="+cookieValue);
        given().log().all().pathParam("issueId","10100").header("Content-Type","application/json")
                .queryParam(cookieName,cookieValue)
                .cookie("JSESSIONID",cookieValue)
                .body("{\n" +
                        "    \"body\": \"Comments from Rest API Automation\",\n" +
                        "    \"visibility\": {\n" +
                        "        \"type\": \"role\",\n" +
                        "        \"value\": \"Administrators\"\n" +
                        "    }\n" +
                        "}")
                .when().post("/rest/api/2/issue/{issueId}/comment")
                .then().log().all();
    }

}
