package resources.utils;

import io.restassured.path.json.JsonPath;

public class JsonUtility {

    public static JsonPath rawToJSON(String response){
        JsonPath js=new JsonPath(response);
        return js;
    }


}
