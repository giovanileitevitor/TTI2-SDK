package com.timwe.init

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.timwe.tti2sdk.ui.splash.SplashActivity

internal class TTI {

//    private var apiKey = ""
//    @Throws(TtiRuntimeException::class)
//    fun ui(activity: Activity?, userProfile: UserProfile?, utm: UTM?,
//        redirectKey: String?, callback: ScreenCallback?
//    ) {
//        var redirectKey = redirectKey
//        Companion.callback = callback
//        Companion.userProfile = userProfile
//        Companion.utm = utm
//        isDebug = isDebug
//
//        if (activity == null) {
//            throw TtiRuntimeException("activity can't be null")
//        } else if (userProfile == null) {
//            throw TtiRuntimeException("userProfile can't be null")
//        } else if (userProfile.getUserMsisdn() == null || userProfile.getUserMsisdn().isEmpty()) {
//            throw TtiRuntimeException("user msisdn can't be null or empty")
//        } else if (utm == null) {
//            throw TtiRuntimeException("utm can't be null")
//        } else {
//            if (userProfile.getLang() == null || userProfile.getLang().isEmpty()) {
//                userProfile.setLang(DEFAULT_LANG_AR)
//            }
//            if (redirectKey == null || redirectKey.isEmpty()) {
//                redirectKey = ScreenKey.SPLASH.name()
//            }
//            val intent = Intent(activity, SplashActivity::class.java)
//            intent.putExtra(SplashActivity.USER_PROFILE_KEY, userProfile)
//            intent.putExtra(SplashActivity.IS_DEBUGGABLE, isDebug)
//            activity.startActivity(intent)
//        }
//    }
//
//    @Throws(TtiRuntimeException::class)
//    fun ui(
//        activity: Activity?,
//        userProfile: UserProfile?,
//        redirectKey: String?,
//        callback: ScreenCallback?
//    ) {
//        ui(activity, userProfile, redirectKey, callback)
//    }
//
//    fun getUserProfile(request: TtiRequest, callBack: ResponseCallback<UserProfileResponse?>) {
//        Log.d(TAG, "getUserProfile: $request $callBack")
//        val userProfileResponse = UserProfileResponse()
//        //callback.callback(userProfileResponse.getUserProfile());
//    }
//
//    fun postInfo(request: TtiRequest?, postInfo: PostInfo?, callback: ResponseCallback?) {}
//
//    companion object {
//        private var instance: TTI? = null
//        private const val TAG = "Tti"
//        private var callback: ScreenCallback? = null
//        private var userProfile: UserProfile? = null
//        private var utm: UTM? = null
//        private var isDebug = false
//        fun newInstance(apiKey: String, isDebug: Boolean): TTI? {
//            if (instance == null) {
//                instance = TTI()
//            }
//            instance!!.apiKey = apiKey
//            Companion.isDebug = isDebug
//            return instance
//        }
//
//        fun getUserProfile(): UserProfile? {
//            return userProfile
//        }
//    }
}