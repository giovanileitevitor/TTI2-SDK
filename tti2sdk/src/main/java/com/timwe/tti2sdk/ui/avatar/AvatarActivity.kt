package com.timwe.tti2sdk.ui.avatar

import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.databinding.ActivityAvatarBinding
import com.timwe.tti2sdk.ui.FragmentId
import com.timwe.tti2sdk.ui.Navigation
import org.koin.androidx.viewmodel.ext.android.viewModel

class AvatarActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAvatarBinding
    private val viewModel: AvatarViewModel by viewModel()
    private  val rotateElement : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_anim) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvatarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()

    }

    private fun setupActions() {
//        bindToVM(viewModel.loading, ::processLoading)
//        bindToVM(viewModel.avatar, ::getAvatarRandon)
    }

    private fun processLoading(loading: Boolean) {
//        if(loading){
//            binding.imgRandomize.startAnimation(rotateElement)
//        }else{
//            binding.imgRandomize.animate().cancel()
//        }
    }

    private fun getAvatarRandon(avatar: Avatar) {
        Log.i("getAvatarRandon", "getAvatarRandon")
    }

    override fun onResume() {
        super.onResume()
        setupObservers()
        setupListeners()
        setupActions()
    }

    fun setTabSelected(position: Int){
        val layout = when(position){
            0 -> R.layout.layout_tab_selected_head
            1 -> R.layout.layout_tab_selected_clothes
            2 -> R.layout.layout_tab_selected_shoes
            3 -> R.layout.layout_tab_selected_ride
            else -> 0
        }
        binding.tabLayout.getTabAt(position)?.setCustomView(layout)
    }

    fun setTabUnSelected(position: Int){
        val layout = when(position){
            0 -> R.layout.layout_tab_unselected_head
            1 -> R.layout.layout_tab_unselected_clothes
            2 -> R.layout.layout_tab_unselected_shoes
            3 -> R.layout.layout_tab_unselected_ride
            else -> 0
        }
        binding.tabLayout.getTabAt(position)?.setCustomView(layout)
    }

    private fun setupView() = with(binding){

        var  mFirstPageCalled = true;
        val adapter = AdapterTabFragment(this@AvatarActivity)
        adapter.addFragment(Navigation.getFragmentFromFragmentId(FragmentId.HEAD))
        adapter.addFragment(Navigation.getFragmentFromFragmentId(FragmentId.CLOTHES))
        adapter.addFragment(Navigation.getFragmentFromFragmentId(FragmentId.SHOES))
        adapter.addFragment(Navigation.getFragmentFromFragmentId(FragmentId.RIDE))
        viewPager.adapter = adapter
        viewPager.currentItem = 0
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            Log.i("meus tabs", "${tab} + ${position}")
        }.attach()
        tabLayout.getTabAt(0)?.setCustomView(R.layout.layout_tab_selected_head)
        tabLayout.getTabAt(1)?.setCustomView(R.layout.layout_tab_unselected_clothes)
        tabLayout.getTabAt(2)?.setCustomView(R.layout.layout_tab_unselected_shoes)
        tabLayout.getTabAt(3)?.setCustomView(R.layout.layout_tab_unselected_ride)
        val tab = tabLayout.getTabAt(0)
        tab?.select()
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

                    setTabSelected( position)

                    val myPossitonsUnselected: ArrayList<Int> = arrayListOf(HEAD, CLOTHES, SHOES, RIDE)
                    myPossitonsUnselected.remove(position)
                    for (item in myPossitonsUnselected){
                        setTabUnSelected(item)
                    }

                }

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

        })

    }

    private fun setupListeners(){
        binding.btnBackAvatar.setOnClickListener {
            onBackPressed()
        }

        binding.btnShareAvatar.setOnClickListener {

        }

        binding.btnSaveAvatar.setOnClickListener {

        }
        binding.btnRandomize.setOnClickListener{
            processLoading(true)
            viewModel.getAvatar()
        }

    }

    private fun setupObservers(){
        viewModel.avatar.observe(this, Observer {it ->
            Log.i("Avatar", it.eyeBrows.id.toString())
        })
        viewModel.error.observe( this, Observer { it ->
            Log.i("Erro call avatar", it.toString())
        })
    }


    override fun onBackPressed() {
        onBackPressedDispatcher.onBackPressed()
    }

    internal class AdapterTabFragment(activity: FragmentActivity?) : FragmentStateAdapter(activity!!) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()

        fun addFragment(fragment: Fragment) {
            mFragmentList.add(fragment)
        }

        override fun getItemCount(): Int {
            return mFragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return mFragmentList[position]
        }
    }

    companion object{
        private const val HEAD    = 0
        private const val CLOTHES = 1
        private const val SHOES   = 2
        private const val RIDE    = 3
    }
}