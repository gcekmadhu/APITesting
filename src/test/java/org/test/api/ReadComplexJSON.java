package org.test.api;

import io.restassured.path.json.JsonPath;



import org.testng.annotations.Test;
import resources.files.Paload;
import resources.utils.JsonUtility;
import org.testng.Assert;
import java.util.List;

public class ReadComplexJSON {
    @Test
    public void test1(){
        //Print no of courses returned by API in paload samplejson
        JsonPath js= JsonUtility.rawToJSON(Paload.sampleJson());
        /*List<String> ls=js.getList("courses");
        System.out.println(ls.size());
        for(int i=0;i<ls.size();i++){
            System.out.println(js.getString("courses["+i+"].title"));
        }*/
        System.out.println(js.getInt("courses.size()"));


        //Print purchase amount
        int purchaseAmt=js.getInt("dashboard.purchaseAmount");
        System.out.println(js.getInt("dashboard.purchaseAmount"));


        //Print title of first course
        System.out.println(js.getString("courses[0].title"));
        List<String> courses1=js.getList("courses");


        //Print all course titles & their respective prices

        for(int i=0;i<js.getInt("courses.size()");i++){
            String title=js.getString("courses["+i+"].title");
            int price=js.getInt("courses["+i+"].price");
            System.out.println("title is-->"+title+"  && Price-->"+price);
        }


        //Print no of copies sold by RPA
        for(int i=0;i<js.getInt("courses.size()");i++){
            String title=js.getString("courses["+i+"].title");
            if(title.equals("RPA")){
                int copies=js.getInt("courses["+i+"].copies");
                System.out.println(title+" copies is/are "+copies);
                break;
            }
        }

        //Verify sum of all courses price matches with purchase amount
        int totalActualAmt=0;
        for(int i=0;i<js.getInt("courses.size()");i++){
            String title=js.getString("courses["+i+"].title");
            int price=js.getInt("courses["+i+"].price");
            int copies=js.getInt("courses["+i+"].copies");

            totalActualAmt=totalActualAmt+ (copies*price);
        }
        System.out.println(totalActualAmt);
        Assert.assertEquals(totalActualAmt,purchaseAmt);

    }
}
