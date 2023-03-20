package com.timwe.tti2sdk.ui.avatar


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel


class AvatarActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAvatarBinding
    private val viewModel: AvatarViewModel by viewModel()
    private val avatarView by lazy(LazyThreadSafetyMode.NONE) { binding.avatar }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvatarBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setupObservers()
        setupListeners()
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

    private fun setAllTabs() = with(binding){
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            Log.i("meus tabs", "${tab} + ${position}")
        }.attach()
        tabLayout.getTabAt(0)?.setCustomView(R.layout.layout_tab_selected_head)
        tabLayout.getTabAt(1)?.setCustomView(R.layout.layout_tab_unselected_clothes)
        tabLayout.getTabAt(2)?.setCustomView(R.layout.layout_tab_unselected_shoes)
        tabLayout.getTabAt(3)?.setCustomView(R.layout.layout_tab_unselected_ride)
        tabLayout.getTabAt(0)?.select()
    }

    val adapter = AdapterTabFragment(this@AvatarActivity)
    private fun setupView(avatar: Avatar) = with(binding){

        val bundle = Bundle()
        bundle.putSerializable(HeadFragment.AVATAR, avatar)

        if(viewPager.adapter != null){
            adapter.replaceFragment(Navigation.getFragmentFromFragmentId(FragmentId.HEAD, bundle), HEAD,)
            adapter.replaceFragment(Navigation.getFragmentFromFragmentId(FragmentId.CLOTHES, bundle), CLOTHES)
            adapter.replaceFragment(Navigation.getFragmentFromFragmentId(FragmentId.SHOES, bundle), SHOES)
            adapter.replaceFragment(Navigation.getFragmentFromFragmentId(FragmentId.RIDE, bundle), RIDE)
            adapter.notifyDataSetChanged()
            setAllTabs()

            return
        }

        var  mFirstPageCalled = true
        adapter.addFragment(Navigation.getFragmentFromFragmentId(FragmentId.HEAD, bundle))
        adapter.addFragment(Navigation.getFragmentFromFragmentId(FragmentId.CLOTHES, bundle))
        adapter.addFragment(Navigation.getFragmentFromFragmentId(FragmentId.SHOES, bundle))
        adapter.addFragment(Navigation.getFragmentFromFragmentId(FragmentId.RIDE, bundle))
        viewPager.adapter = adapter
        viewPager.currentItem = 0
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
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnShareAvatar.onDebouncedListener {

        }

        binding.btnSaveAvatar.onDebouncedListener {

        }
        binding.btnRandomize.onDebouncedListener{
            viewModel.getAvatarStructure()
            viewModel.getAvatar(random = true)
        }
        viewModel.getAvatar()
    }

    private fun setupObservers(){
        viewModel.avatarStructure.observe(this, Observer{ bytes ->
//            avatarView.setRiveBytes(bytes = bytes, fit = Fit.FILL)
//            avatarView.setRiveResource(parameters from backend)
        })

        viewModel.avatar.observe(this, Observer { it ->
            mountAvatarImage(avatar = it)
            setupView(avatar = it)
        })

        viewModel.error.observe( this, Observer { it ->
            Toast.makeText(this, "Erro: ${it.errorCode.toString()}", Toast.LENGTH_SHORT).show()
        })

        viewModel.loading.observe(this, Observer { it ->
            if(it){
                binding.loadingBox.visibility = View.VISIBLE

            }else{
                binding.loadingBox.visibility = View.GONE
            }
        })
    }

    private fun mountAvatarImage(avatar: Avatar){
        val a = avatar.bottomClothes.id
        Toast.makeText(this, "Dados recebidos com sucesso: $a", Toast.LENGTH_SHORT).show()
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

    override fun onBackPressed() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private const val HEAD    = 0
        private const val CLOTHES = 1
        private const val SHOES   = 2
        private const val RIDE    = 3
    }
}