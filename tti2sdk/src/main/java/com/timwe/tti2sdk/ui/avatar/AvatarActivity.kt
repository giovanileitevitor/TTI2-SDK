package com.timwe.tti2sdk.ui.avatar

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import app.rive.runtime.kotlin.core.Fit
import com.google.android.material.tabs.TabLayoutMediator
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.databinding.ActivityAvatarBinding
import com.timwe.tti2sdk.ui.FragmentId
import com.timwe.tti2sdk.ui.Navigation
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class AvatarActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAvatarBinding
    private val viewModel: AvatarViewModel by viewModel()
    private  val rotateElement : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_anim) }
    private val avatarView by lazy(LazyThreadSafetyMode.NONE) { binding.avatar }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvatarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    override fun onResume() {
        super.onResume()
        setupObservers()
        setupListeners()
    }

    override fun onBackPressed() {
        onBackPressedDispatcher.onBackPressed()
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

        var  mFirstPageCalled = true
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

        })

    }

    private fun setupListeners(){
        binding.btnBackAvatar.onDebouncedListener {
            onBackPressed()
        }

        binding.btnShareAvatar.onDebouncedListener {

        }

        binding.btnSaveAvatar.onDebouncedListener {

        }
        binding.btnRandomize.onDebouncedListener{
            viewModel.getAvatar()
            viewModel.getAvatarStructure()
        }

    }

    private fun setupObservers(){
        viewModel.avatarStructure.observe(this, Observer{ bytes ->
            avatarView.setRiveBytes(bytes = bytes, fit = Fit.COVER)
        })

        viewModel.avatar.observe(this, Observer {it ->
            mountAvatarImage(avatar = it)
        })

        viewModel.error.observe( this, Observer { it ->
            Toast.makeText(this, "Erro: ${it.errorCode.toString()}", Toast.LENGTH_SHORT).show()
        })

        viewModel.loading.observe(this, Observer { it ->
            if(it){
                binding.loadingBox.visibility = View.VISIBLE
                binding.imgRandomize.apply{
                    startAnimation(rotateElement)
                    animate()
                }
            }else{
                binding.loadingBox.visibility = View.GONE
            }
        })
    }

    private fun mountAvatarImage(avatar: Avatar){
        val a = avatar.bottomClothes.id
        Toast.makeText(this, "Dados recebidos com sucesso: $a", Toast.LENGTH_SHORT).show()
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

    private fun startingIconAnimation() = with(binding){
        imgRandomize.startAnimation(rotateElement)
    }

    companion object{
        private const val HEAD    = 0
        private const val CLOTHES = 1
        private const val SHOES   = 2
        private const val RIDE    = 3
    }
}