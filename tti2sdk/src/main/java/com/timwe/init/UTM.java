package com.timwe.init;

import androidx.annotation.Keep;
import java.io.Serializable;


@Keep
public final class UTM implements Serializable {
    private String utmSource;
    private String utmCampaign;
    private String utmMedium;
    private String utmTerm;
    private String utmContent;

    public UTM(String utmSource, String utmCampaign, String utmMedium, String utmTerm, String utmContent) {
        this.utmSource = utmSource;
        this.utmCampaign = utmCampaign;
        this.utmMedium = utmMedium;
        this.utmTerm = utmTerm;
        this.utmContent = utmContent;
    }

    public String getUtmSource() {
        return utmSource;
    }

    public void setUtmSource(String utmSource) {
        this.utmSource = utmSource;
    }

    public String getUtmCampaign() {
        return utmCampaign;
    }

    public void setUtmCampaign(String utmCampaign) {
        this.utmCampaign = utmCampaign;
    }

    public String getUtmMedium() {
        return utmMedium;
    }

    public void setUtmMedium(String utmMedium) {
        this.utmMedium = utmMedium;
    }

    public String getUtmTerm() {
        return utmTerm;
    }

    public void setUtmTerm(String utmTerm) {
        this.utmTerm = utmTerm;
    }

    public String getUtmContent() {
        return utmContent;
    }

    public void setUtmContent(String utmContent) {
        this.utmContent = utmContent;
    }
}
