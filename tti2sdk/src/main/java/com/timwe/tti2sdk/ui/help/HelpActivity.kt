package com.timwe.tti2sdk.ui.help

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.bumptech.glide.Glide
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.HelpInfo
import com.timwe.tti2sdk.databinding.ActivityHelpBinding
import com.timwe.tti2sdk.ui.help.adapters.HelpAdapter
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class HelpActivity: AppCompatActivity() {

    private val viewModel: HelpViewModel by viewModel()
    private lateinit var binding : ActivityHelpBinding
    private var checked = false
    //private lateinit var helpAdapter : HelpAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()


    }

    fun search() {

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
        //viewModel.getHelpData()
    }

    private fun setupListeners(){
        binding.btnBackHelp.onDebouncedListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnSkip.onDebouncedListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.termsTextView.onDebouncedListener {
            val url = applicationContext.getString(R.string.termsAndConditionsURL)
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }

        binding.txtRulesAndPrizes.onDebouncedListener {
            val url = applicationContext.getString(R.string.rulesAndPrizesURL)
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }

        binding.chkboxTerms.onDebouncedListener {
            checked = binding.chkboxTerms.isChecked
        }

        binding.btnStart.onDebouncedListener{
            if (checked)
            {
                viewModel.getHelpData()
            }
        }
    }

    private fun setupObservers(){
        viewModel.helpInfo.observe(this, Observer{
            binding.containerFirstHelpPage.visibility = View.GONE
            showHelpCarousel(helpPages = it)
        })
    }

    private fun showHelpCarousel(helpPages: List<HelpInfo>){
        binding.containerOthersHelpPage.visibility = View.VISIBLE
        binding.helpViewPager.apply {
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        binding.helpViewPager.adapter = HelpAdapter(
            data = helpPages,
            mGlide = Glide.with(this)
        )

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((40*Resources.getSystem().displayMetrics.density).toInt()))
        compositePageTransformer.addTransformer{ page, position ->
            val r = 1 -abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }
        binding.helpViewPager.setPageTransformer(compositePageTransformer)
    }

}

/*
//        val adapter = HelpViewPagerAdapter(supportFragmentManager)
//
//        adapter.addFragment(HelpMissionsFragment())
//        adapter.addFragment(HelpPrizesFragment())
//        adapter.addFragment(HelpRupiahFragment())
//        adapter.addFragment(HelpEventsFragment())
//        adapter.addFragment(HelpTierRewardsFragment())

        //binding.helpViewPager.adapter = adapter
        //binding.helpTabLayout.setupWithViewPager(binding.helpViewPager)
 */