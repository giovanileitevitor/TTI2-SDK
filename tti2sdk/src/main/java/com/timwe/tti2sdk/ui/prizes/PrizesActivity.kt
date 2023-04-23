package com.timwe.tti2sdk.ui.prizes

import android.app.Dialog
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.TypefaceSpan
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import com.google.android.material.tabs.TabLayoutMediator
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.PrizeFlow
import com.timwe.tti2sdk.data.model.response.AvailableReward
import com.timwe.tti2sdk.databinding.ActivityPrizesBinding
import com.timwe.tti2sdk.ui.FragmentId
import com.timwe.tti2sdk.ui.Navigation
import com.timwe.tti2sdk.ui.dialog.DialogError
import com.timwe.tti2sdk.ui.prizes.fragments.AvailableFragment.Companion.PRIZES
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class PrizesActivity: AppCompatActivity() {

    private lateinit var binding: ActivityPrizesBinding
    private val viewModel: PrizesViewModel by viewModel()
    var builderDialogPrize : Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrizesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStartCall()
    }

    override fun onResume() {
        super.onResume()
        setupListeners()
        setupObservers()
    }

    private fun setStartCall() {
        viewModel.getPrizes()
    }

    private fun setAllTabs() = with(binding){
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            Log.i("meus tabs", "${tab} + ${position}")
        }.attach()
        tabLayout.getTabAt(0)?.setCustomView(R.layout.layout_tab_prizes_avaliable_selected)
        tabLayout.getTabAt(1)?.setCustomView(R.layout.layout_tab_prizes_history_unselected)
        tabLayout.getTabAt(0)?.select()
    }

    fun setTabSelected(position: Int){
        val layout = when(position){
            0 -> R.layout.layout_tab_prizes_avaliable_selected
            1 -> R.layout.layout_tab_prizes_history_selected
            else -> 0
        }
        binding.tabLayout.getTabAt(position)?.setCustomView(layout)
    }

    fun setTabUnSelected(position: Int){
        val layout = when(position){
            0 -> R.layout.layout_tab_prizes_avaliable_unselected
            1 -> R.layout.layout_tab_prizes_history_unselected
            else -> 0
        }
        binding.tabLayout.getTabAt(position)?.setCustomView(layout)
    }

    val adapter = AdapterTabFragment(this@PrizesActivity)
    private fun setupView(prize: PrizeFlow? = null) = with(binding){

        binding.btnBackPrizes.setOnClickListener {
            onBackPressed()
        }

        val bundle = Bundle()
        bundle.putSerializable(PRIZES, prize)

        if(viewPager.adapter != null){
            adapter.replaceFragment(
                Navigation.getFragmentFromFragmentId(FragmentId.FRAG_AVALIABLE, bundle),
                0
            )
            adapter.replaceFragment(
                Navigation.getFragmentFromFragmentId(FragmentId.FRAG_HISTORY, bundle),
                1
            )
            adapter.notifyItemRangeChanged(0, 3)
            setAllTabs()

            return
        }

        var mFirstPageCalled = true
        adapter.addFragment(Navigation.getFragmentFromFragmentId(FragmentId.FRAG_AVALIABLE, bundle))
        adapter.addFragment(Navigation.getFragmentFromFragmentId(FragmentId.FRAG_HISTORY, bundle))
        viewPager.adapter = adapter
        viewPager.currentItem = 0
        viewPager.isUserInputEnabled = false
        setAllTabs()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                Log.i("onPageScrollStateChanged", "${state}")
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.i("onPageSelected", "${position}")
                if(mFirstPageCalled){
                    mFirstPageCalled = false

                }else{

                    setTabSelected(position)

                    val myPossitonsUnselected: ArrayList<Int> = arrayListOf(
                        0,
                        1,
                    )
                    myPossitonsUnselected.remove(position)
                    for (item in myPossitonsUnselected){
                        setTabUnSelected(item)
                    }
                }
            }
        })
    }

    fun setSizeTab(tabSelected: Int = 0, size: Int = 0){
        val badge = binding.tabLayout.getTabAt(tabSelected)?.customView?.findViewById<TextView>(R.id.tvBadge)
        if(size == null || size == 0){
            badge?.visibility = View.INVISIBLE
        }else{
            badge?.visibility = View.VISIBLE
            badge?.text = if (size > 9) "9+" else size.toString()
        }
    }

    private fun setupListeners(){
        binding.btnBackPrizes.onDebouncedListener {
            finish()
        }
    }

    override fun onBackPressed() {
        finish()
    }

    private fun setupObservers(){
        viewModel.prizes.observe(this, Observer { prize ->
            setupView(prize = prize)
            setSizeTab(tabSelected = 0, size = prize.availableRewards.size)
            setSizeTab(tabSelected = 1, size = prize.historyRewards.size)
        })

        viewModel.error.observe(this, Observer { it ->
            DialogError(
                this@PrizesActivity,
                it.errorCode!!,
                object : DialogError.ClickListenerDialogError{
                    override fun reloadClickListener() {
                        viewModel.getPrizes()
                    }
                }
            )
        })

        viewModel.loading.observe(this, Observer { it ->
            if(it){
                binding.loadingBoxPrizes.visibility = View.VISIBLE
            }else{
                binding.loadingBoxPrizes.visibility = View.GONE
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun showDialog(availableReward: AvailableReward){
        if (builderDialogPrize == null){
            builderDialogPrize = Dialog(this)
        }
        val inflater = layoutInflater
        val dialogLayoutShare = inflater.inflate(R.layout.dialog_prize, null)
        val bntConfirm = dialogLayoutShare.findViewById<AppCompatButton>(R.id.btnConfirmPrize)
        val progress = dialogLayoutShare.findViewById<ConstraintLayout>(R.id.loadingBoxPrizes)
        val titleVoucher = dialogLayoutShare.findViewById<TextView>(R.id.titleVoucher)
        val iconTop = dialogLayoutShare.findViewById<ImageView>(R.id.ivIconTop)

        if(!availableReward.descr.isNullOrEmpty()){
            val spanString = SpannableString("${availableReward.name}\n${availableReward.descr}")
            val typefaceBold = Typeface.create(ResourcesCompat.getFont(this, R.font.poppins_bold), Typeface.NORMAL)
            val typefaceRegular = Typeface.create(ResourcesCompat.getFont(this, R.font.poppins_regular), Typeface.NORMAL)
            spanString.setSpan(TypefaceSpan(typefaceBold), 0, availableReward.name.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
            spanString.setSpan(TypefaceSpan(typefaceRegular), (availableReward.name.length+1), spanString.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
            titleVoucher?.text = spanString

        }else{
            titleVoucher.text = availableReward.name
        }

        iconTop.load(availableReward.additionalProperties.prizeIcon) {
            decoderFactory { result, options, _ -> SvgDecoder(result.source, options) }
            crossfade(750).build()
            listener(
                onSuccess = { _, _ ->
                    progress?.visibility = View.INVISIBLE
                },
                onError = { request: ImageRequest, _ ->
                    request.error

                    Glide.with(this@PrizesActivity).load(availableReward.additionalProperties.prizeIcon)
                        .priority(Priority.HIGH)
                        .listener(object : RequestListener<Drawable> {

                            override fun onLoadFailed(e: GlideException?, model: Any?,
                                                      target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                                progress?.visibility = View.INVISIBLE
                                return false
                            }

                            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                                         dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                progress?.visibility = View.INVISIBLE
                                return false
                            }
                        })
                        .into(iconTop)

                }
            )
        }

        progress.visibility = View.GONE
        bntConfirm.setOnClickListener{
            builderDialogPrize?.cancel()
        }
        builderDialogPrize?.setContentView(dialogLayoutShare)
        builderDialogPrize?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        builderDialogPrize?.setCancelable(false)
        builderDialogPrize?.show()
    }

    inner class AdapterTabFragment(activity: FragmentActivity?) : FragmentStateAdapter(activity!!) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()

        fun addFragment(fragment: Fragment) {
            mFragmentList.add(fragment)
        }

        fun replaceFragment(fragment: Fragment, position: Int) {
            mFragmentList.removeAt(position)
            mFragmentList.add(position, fragment)
        }

        fun getFragment(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getItemCount(): Int {
            return mFragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return mFragmentList[position]
        }

    }

}