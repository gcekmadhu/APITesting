package org.api.jira;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import resources.utils.JsonUtility;

import static io.restassured.RestAssured.given;

public class GetIssueDetails {

    @Test
    public void getIssueDetails(){
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
        String expectedComments="New API Automation Comment";
        String commentResp=given().log().all().pathParam("issueId","10100").header("Content-Type","application/json")
                .queryParam(cookieName,cookieValue)
                .cookie("JSESSIONID",cookieValue)
                .body("{\n" +
                        "    \"body\": \""+expectedComments+"\",\n" +
                        "    \"visibility\": {\n" +
                        "        \"type\": \"role\",\n" +
                        "        \"value\": \"Administrators\"\n" +
                        "    }\n" +
                        "}")
                .when().post("/rest/api/2/issue/{issueId}/comment")
                .then().log().all().extract().response().asString();
        JsonPath js=JsonUtility.rawToJSON(commentResp);
        String id=js.get("id");


        String response=given().relaxedHTTPSValidation().pathParam("key","AP-3")
                .queryParam("fields","comment")
                .filter(sessionFilter)
                .when().get("/rest/api/2/issue/{key}")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
        System.out.println(response);
        String body="";
        JsonPath js1=JsonUtility.rawToJSON(response);
        int noOfComments=js1.getInt("fields.comment.comments.size()");
        for(int i=0;i<noOfComments;i++){
            String commentid=js1.get("fields.comment.comments["+i+"].id");
            if(commentid.equals(id)){
                body=js1.get("fields.comment.comments["+i+"].body");
                Assert.assertEquals(body,expectedComments);
                break;
            }
        }

    }
}
