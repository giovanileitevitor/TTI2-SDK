package com.timwe.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.timwe.demo.databinding.ActivityMainBinding
import com.timwe.tti2sdk.ui.home.HomeActivity
import com.timwe.tti2sdk.ui.splash.SplashActivity

class MainActivity : AppCompatActivity() {

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
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, languages)
        binding.langSpinner.adapter = adapter
    }

    private fun setupListeners(){
        binding.btnGoTo.setOnClickListener{
            val msisdn = binding.msisdnEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val language = binding.langSpinner.selectedItem
            val isDebugable = binding.debugCheckbox.isChecked

            if(msisdn.isNullOrBlank()){
                Toast.makeText(this, getString(R.string.msisd_not_null), Toast.LENGTH_SHORT).show()
            }else if(email.isNullOrBlank()){
                Toast.makeText(this, getString(R.string.plan_not_null), Toast.LENGTH_SHORT).show()
            } else {
                //Toast.makeText(this, "msisdn: $msisdn \nplan: $plan \nlanguage: $language \nDebugable: $isDebugable", Toast.LENGTH_SHORT).show()
                Log.i("SDK","msisdn: $msisdn | email: $email  | language: $language | Debugable: $isDebugable")
                val intent = Intent(this, SplashActivity::class.java)
                startActivity(intent)
            }

        }
    }
}
