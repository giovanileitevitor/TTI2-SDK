package com.timwe.init;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;


public class Utils {
    private static final String TAG = "Utils";

    public static String instaceToFormatedJsonString(Object object) {
        String formattedString = "";

        try {
            String jsonString = new Gson().toJson(object);
            JSONObject jsonObject = new JSONObject(jsonString);
            formattedString = jsonObject.toString(4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return formattedString;
    }

}