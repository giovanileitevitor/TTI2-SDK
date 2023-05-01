package com.timwe.init;

import java.io.Serializable;
import java.util.Map;


public class UserProfileResponse implements Serializable {
    private boolean userRegistered;
    private boolean userTier;
    private long userRegisteredDate;
    private String userProfile;
    private Map<String, String> additionalParams;

    public Boolean isUserRegistered(){
        return userRegistered;
    }

    public void setUserRegistered(boolean userRegistered){
        this.userRegistered = userRegistered;
    }

    public Boolean getUserTier(){
        return userTier;
    }

    public void setUserTier(boolean userTier){
        this.userTier = userTier;
    }

    public long getUserRegisteredDate(){
        return userRegisteredDate;
    }

    public void setUserRegisteredDate(long userRegisteredDate){
        this.userRegisteredDate = userRegisteredDate;
    }

    public void setUserProfile(String userProfile){
        this.userProfile = userProfile;
    }

    public String getUserProfile(){
        return userProfile;
    }

    public Map<String, String> getAdditionalParams() {
        return additionalParams;
    }

    public void setAdditionalParams(Map<String, String> additionalParams) {
        this.additionalParams = additionalParams;
    }

}
