package com.timwe.tti2sdk.ui.onboarding

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.OnboardingInfo
import com.timwe.tti2sdk.databinding.ActivityOnboardingBinding
import com.timwe.tti2sdk.ui.BaseActivity
import com.timwe.tti2sdk.ui.avatar.AvatarActivity
import com.timwe.tti2sdk.ui.onboarding.adapters.OnBoardingAdapter
import com.timwe.utils.Utils
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs


class OnBoardingActivity: BaseActivity() {

    private val viewModel: OnBoardingViewModel by viewModel()
    private lateinit var binding : ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    override fun onResume() {
        super.onResume()
        setupListeners()
        setupObservers()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        onBackPressedDispatcher.onBackPressed()
    }

    private fun setupView(){
        viewModel.checkIfProfileWasCreated()
    }

    private fun setupListeners(){
        binding.btnBackHelp.onDebouncedListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnSkip.onDebouncedListener {
            if (binding.chkboxTerms.isChecked) {
                val intent = Intent(this, AvatarActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.termsTextView.onDebouncedListener {
            val url = this.getString(R.string.termsAndConditionsURL)
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }

        binding.txtRulesAndPrizes.onDebouncedListener {
            val url = this.getString(R.string.rulesAndPrizesURL)
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }

        binding.btnStart.onDebouncedListener{
            if (binding.chkboxTerms.isChecked) {
                viewModel.getHelpData()
                Utils.showLog("SDK", "FlagStatus: True")
            }
        }
    }

    private fun setupObservers(){
        viewModel.onboardingInfo.observe(this, Observer{
            binding.containerFirstHelpPage.visibility = View.GONE
            showHelpCarousel(helpPages = it)
        })

        viewModel.isProfileCreated.observe(this, Observer{
            Utils.showLog("SDK", "Has profile saved: $it")
        })
    }

    private fun showHelpCarousel(helpPages: List<OnboardingInfo>){
        binding.containerOthersHelpPage.visibility = View.VISIBLE
        binding.helpViewPager.apply {
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = NUMBER_MAX_CARDS.toInt()
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        binding.helpViewPager.adapter = OnBoardingAdapter(
            data = helpPages,
            mGlide = Glide.with(this),
            itemListener = singleClick
        )

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((40*Resources.getSystem().displayMetrics.density).toInt()))
        compositePageTransformer.addTransformer{ page, position ->
            val r = 1 -abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }
        binding.helpViewPager.setPageTransformer(compositePageTransformer)
        TabLayoutMediator(binding.dotsIndicator, binding.helpViewPager){ tab, position ->
        }.attach()

        val lastPosition = helpPages.size -1
        var goToEnd = false

        binding.helpViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Utils.showLog("SDK", "OnBoardingPosition: $position")

                binding.btnSkip.visibility = View.GONE

                if(position == lastPosition){
                    goToEnd = true
                    binding.viewBackground.setBackgroundResource(R.drawable.background_help_activity_blue)
                }else{
                    if(goToEnd){
                        binding.viewBackground.setBackgroundResource(R.drawable.background_help_activity)
                    }
                }
            }
        })
    }

    private val singleClick = { onboardingInfo: OnboardingInfo ->
        if(onboardingInfo.hasButton){
            val intent = Intent(this, AvatarActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    companion object{
        private const val NUMBER_MAX_CARDS = 5
    }

}