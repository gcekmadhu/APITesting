package org.api.jira;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import resources.utils.JsonUtility;

import java.io.File;

import static io.restassured.RestAssured.given;

public class AddAttachment {
    @Test
    public void addAttachmentJira() {

        String cookieName="";
        String cookieValue="";
        String jsessionid="";
        RestAssured.baseURI = "http://localhost:8080";
        SessionFilter sessionFilter = new SessionFilter();
        String cookieResp = given().header("Content-Type", "application/json").body("{\"username\": \"gcek.madhu\", \"password\": \"Ashish!1289\"}")
                .filter(sessionFilter).when().post("/rest/auth/1/session")
                .then().assertThat().statusCode(200).extract().response().asString();
        JsonPath jpCookie = JsonUtility.rawToJSON(cookieResp);
        cookieName = jpCookie.getString("session.name");
        cookieValue = jpCookie.getString("session.value");
        System.out.println(cookieName + " " + cookieValue);
        given().header("X-Atlassian-Token","no-check").filter(sessionFilter)
                .pathParam("key","AP-3")
                .header("Content-Type","multipart/form-data")
                .multiPart("file",new File("C:\\Users\\gcekm\\2023LearningAutomation\\RestAssuredAPITestProject\\jira.txt"))
                .when().post("/rest/api/2/issue/{key}/attachments")
                .then().log().all().assertThat().statusCode(200);
    }
}
