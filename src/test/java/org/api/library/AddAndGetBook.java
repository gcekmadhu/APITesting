package org.api.library;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import resources.files.Paload;
import resources.utils.JsonUtility;

import java.util.List;

import static io.restassured.RestAssured.given;

public class AddAndGetBook {
    @DataProvider(name="BooksData")
    public Object[][]  getData()
    {
        return new Object[][] {{"Selenium","MK","11000"},{"RPA","cwetee","11001"},{"Python","okmfet","11002"} };
    }
    @Test(dataProvider = "BooksData",dataProviderClass = AddAndGetBook.class)
    public void addandGetBookAPITest(String bookname,String isbn,String aisle){
        RestAssured.baseURI="https://rahulshettyacademy.com";
        Response response=given().header("Content-Type","application/json")
                .body(Paload.addBook("Selenium",isbn,aisle)).when().post("Library/Addbook.php")
                .then().assertThat().statusCode(200).extract().response();
        System.out.println(response.asString());
        JsonPath jsonPath=new JsonPath(response.asString());
        String id=jsonPath.getString("ID");
        System.out.println(id);

        //GetAPI
        String responseforGet=given().queryParams("ID",id).when().get("/Library/GetBook.php")
                .then().assertThat().statusCode(200).extract().response().asString();
        System.out.println(responseforGet);
        JsonPath jsonPath1=new JsonPath(responseforGet);
       /* List<String> booknameString=jsonPath1.getList("book_name");
        System.out.println(bookname);*/


        //DeleteBookAPI
        String responseDelete=given().header("Content-Type","application/json")
                .body("{\n" +
                        " \n" +
                        "\"ID\" : \""+id+"\"\n" +
                        " \n" +
                        "} \n")
                .when().delete("Library/DeleteBook.php")
                .then().assertThat().statusCode(200).extract().response().asString();
        System.out.println(responseDelete);
        JsonPath jsDelete= JsonUtility.rawToJSON(responseDelete);
        Assert.assertEquals("book is successfully deleted",jsDelete.getString("msg"));


    }
}
