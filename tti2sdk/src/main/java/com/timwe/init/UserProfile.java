package com.timwe.init;

import androidx.annotation.Keep;
import java.io.Serializable;
import java.util.Map;

@Keep
public final class UserProfile extends BaseModelClass implements Serializable {

    private String profileId;
    private String email;
    private String userMsisdn;
    private String lang;
    private String tier;
    private Map<String, String> additionalParams;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserMsisdn() {
        return userMsisdn;
    }

    public void setUserMsisdn(String userMsisdn) {
        this.userMsisdn = userMsisdn;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public Map<String, String> getAdditionalParams() {
        return additionalParams;
    }

    public void setAdditionalParams(Map<String, String> additionalParams) {
        this.additionalParams = additionalParams;
    }

}