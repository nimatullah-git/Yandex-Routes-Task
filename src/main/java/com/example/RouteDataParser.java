package com.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author nimatullah
 */
public class RouteDataParser {
    public static String parseDistance(String jsonResponse) {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        return jsonObject.getAsJsonObject("route").get("distance").getAsString();
    }

    public static String parseTime(String jsonResponse) {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        return jsonObject.getAsJsonObject("route").get("time").getAsString();
    }
}
