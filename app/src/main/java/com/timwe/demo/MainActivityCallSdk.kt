package com.timwe.demo

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.timwe.demo.databinding.ActivityMainBinding
import com.timwe.init.*
class MainActivityCallSdk : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    override fun onResume() {
        super.onResume()
        setupListeners()
    }

    private fun setupView(){
        binding.labelVersion.text = "Version: ${BuildConfig.VERSION_NAME}"
        val languages = resources.getStringArray(R.array.Languages)
        val adapterLanguage = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, languages)
        binding.langSpinner.adapter = adapterLanguage

        val tiers = resources.getStringArray(R.array.Tier)
        val adapterTier = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tiers)
        binding.tierSpinner.adapter = adapterTier

        val plans = resources.getStringArray(R.array.Plans)
        val adapterPlans = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, plans)
        binding.planSpinner.adapter = adapterPlans
    }
    private fun setupListeners(){
        binding.btnGoTo.setOnClickListener{
            val msisdn = binding.msisdnEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val language = binding.langSpinner.selectedItem.toString()
            val tier = binding.tierSpinner.selectedItem.toString()
            val plan = binding.planSpinner.selectedItem.toString()
            val location = binding.locationEditText.text.toString().trim()
            val isDebugable = binding.debugCheckbox.isChecked

            if(msisdn.isNullOrBlank()){
                Toast.makeText(this, getString(R.string.msisd_not_null), Toast.LENGTH_SHORT).show()
            }else if(email.isNullOrBlank()){
                Toast.makeText(this, getString(R.string.plan_not_null), Toast.LENGTH_SHORT).show()
            } else {
                Log.i("SDK","msisdn: $msisdn | email: $email  | language: $language | Location: $location | Debugable: $isDebugable")
                startTti2Sdk(msisdn, email, language, tier, plan, location, isDebugable)
            }

        }
    }

    private fun startTti2Sdk(msisdn: String, email: String, lang: String, tier: String, plan: String, location: String, isDebug: Boolean) {

        //Start start sdk
        val userProfile = UserProfile()
        userProfile.userMsisdn = msisdn
        userProfile.email = email
        userProfile.lang = lang
        userProfile.tier = tier
        userProfile.plan = plan
        userProfile.location = location

        val tti2: Tti2 = Tti2.newInstance("a52f8547-650a-49ea-b01d-3f4aaf49d485", isDebug)
        val tti2Request = Tti2Request()
        tti2Request.userProfile = userProfile

        try {
            val screenCallback = ScreenCallback { redirectKey ->
                    Log.d("SDK", "ScreenCallback: $redirectKey")
                }
            val redirectKey = ""
            tti2.ui(
                this@MainActivityCallSdk,
                userProfile,
                UTM(
                    "utmSourceTesteAndroid",
                    "utmCampaignTesteAndroid",
                    "utmMediumTesteAndroid",
                    "utmTermTesteAndroid",
                    "utmContenTesteAndroid"
                ),
                redirectKey,
                screenCallback
            )
        } catch (tti2RuntimeException: Tti2RuntimeException) {
            tti2RuntimeException.printStackTrace()
            Log.d("SDK", "startSdk Tti2RuntimeException: " + tti2RuntimeException.message)
        }
        //End start sdk





        //Start call game status
//        val tti2: Tti2 = Tti2.newInstance("a52f8547-650a-49ea-b01d-3f4aaf49d485", isDebug)
//
//        val userMsisdn: UserMsisdn = UserMsisdn()
//        userMsisdn.msisdn = msisdn
//        userMsisdn.isPrimaryNumber = true
//        userMsisdn.additionalParams = mapOf("1" to "banana", "2" to "orange")
//
//        val userProfile = UserProfileRequest()
//        userProfile.profileId = "1223445"
//        userProfile.userMsisdns = listOf<UserMsisdn>(userMsisdn)
//        userProfile.email = email
//        userProfile.defaultSegment = plan
//        userProfile.lang = lang
//
//        val gameRequest = GameRequest()
//        gameRequest.userProfile = userProfile
//        gameRequest.sessionKey = ""
//        val map: Map<String, String> = mapOf("1" to "banana", "2" to "orange")
//        gameRequest.additionalParams = map
//
//        try {
//            tti2.getStatusGame( gameRequest, object: ResponseCallback<GameStatusResponse> {
//                override fun onResponse(response: GameStatusResponse?, error: NetworkError?) {
//                    //Check
//                    Log.i("SDK", "response $response")
//                }
//            })
//        }catch (e: Tti2RuntimeException){
//            e.printStackTrace()
//            Log.e("SDK", "response ${e.message}")
//        }
        //End call game status




        //Start call user profile
//        val tti2: Tti2 = Tti2.newInstance("a52f8547-650a-49ea-b01d-3f4aaf49d485", isDebug)
//
//        val userMsisdn: UserMsisdn = UserMsisdn()
//        userMsisdn.msisdn = msisdn
//        userMsisdn.isPrimaryNumber = true
//        userMsisdn.additionalParams = mapOf("1" to "banana", "2" to "orange")
//
//        val userProfile = UserProfileRequest()
//        userProfile.profileId = "1223445"
//        userProfile.userMsisdns = listOf<UserMsisdn>(userMsisdn)
//        userProfile.email = email
//        userProfile.defaultSegment = plan
//        userProfile.lang = lang
//
//        val gameRequest = GameRequest()
//        gameRequest.userProfile = userProfile
//        gameRequest.sessionKey = ""
//        val map: Map<String, String> = mapOf("1" to "banana", "2" to "orange")
//        gameRequest.additionalParams = map
//        try {
//            tti2.getUserProfile( gameRequest, object: ResponseCallback<UserGameResponse> {
//                override fun onResponse(response: UserGameResponse?, error: NetworkError?) {
//                    //Check
//                    Log.i("SDK", "response $response")
//                }
//            })
//        }catch (e: Tti2RuntimeException){
//            e.printStackTrace()
//            Log.e("SDK", "response ${e.message}")
//        }
        //End call ser profile

    }
}