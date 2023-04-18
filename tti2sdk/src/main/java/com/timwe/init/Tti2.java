package com.timwe.init;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.Keep;
import static com.timwe.utils.Constants.DEFAULT_LANG_BA;
import com.timwe.tti2sdk.ui.splash.SplashActivity;

@Keep
public final class Tti2 {

    private static Tti2 instance;
    private String apiKey = "";
    private static ScreenCallback callback;
    private static UserProfile userProfile;
    private static UTM utm;
    private static boolean isDebug = false;

    public static Tti2 newInstance(String apiKey, Boolean isDebug) {
        if (instance == null) {
            instance = new Tti2();
        }
        instance.apiKey = apiKey;
        Tti2.isDebug = isDebug;
        return instance;
    }

    public void ui(Activity activity, UserProfile userProfile, UTM utm,
                   String redirectKey, ScreenCallback callback) throws Tti2RuntimeException {

        Tti2.callback = callback;
        Tti2.userProfile = userProfile;
        Tti2.utm = utm;
        Tti2.isDebug = isDebug;

        if (activity == null) {
            throw new Tti2RuntimeException("activity can't be null");
        } else if (userProfile == null) {
            throw new Tti2RuntimeException("userProfile can't be null");
        } else if (userProfile.getUserMsisdn() == null || userProfile.getUserMsisdn().isEmpty()) {
            throw new Tti2RuntimeException("user msisdn can't be null or empty");
        } else if(utm == null){
            throw new Tti2RuntimeException("utm can't be null");
        } else {
            if (userProfile.getLang() == null || userProfile.getLang().isEmpty()) {
                userProfile.setLang(DEFAULT_LANG_BA);
            }
            if (redirectKey == null || redirectKey.isEmpty()) {
                redirectKey = ScreenKey.SPLASH.name();
            }
            Intent intent = new Intent(activity, SplashActivity.class);
            intent.putExtra("USER_PROFILE_KEY", userProfile);
            intent.putExtra("IS_DEBUGGABLE", isDebug);
            activity.startActivity(intent);
        }
    }

    public void ui(
            Activity activity,
            UserProfile userProfile,
            String redirectKey,
            ScreenCallback callback
    ) throws Tti2RuntimeException {
        ui(activity, userProfile, redirectKey, callback );
    }

    public void getUserProfile(Tti2Request request, ResponseCallback<UserProfileResponse> callBack){
        Log.d("SDK", "getUserProfile: " + request + " " + callBack);
        UserProfileResponse userProfileResponse = new UserProfileResponse();
    }

    public void postInfo(Tti2Request request, PostInfo postInfo, ResponseCallback callback){

    }

    public static UserProfile getUserProfile(){
        return userProfile;
    }

}