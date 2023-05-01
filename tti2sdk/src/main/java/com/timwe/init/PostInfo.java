package com.timwe.init;

import java.io.Serializable;
import java.util.Map;

public class PostInfo implements Serializable {

    private String action;
    private Map<String, String> additionalParams;

    public String getAction(){
        return action;
    }

    public void setAction(String action){
        this.action = action;
    }

    public Map<String, String> getAdditionalParams() {
        return additionalParams;
    }

    public void setAdditionalParams(Map<String, String> additionalParams) {
        this.additionalParams = additionalParams;
    }
}