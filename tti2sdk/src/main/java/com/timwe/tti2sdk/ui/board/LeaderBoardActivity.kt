package com.timwe.tti2sdk.ui.board

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.databinding.ActivityLeaderboardBinding
import com.timwe.tti2sdk.ui.board.adapter.TabAdapter
import com.timwe.tti2sdk.ui.dialog.DialogError
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class LeaderBoardActivity: AppCompatActivity() {

    private lateinit var binding : ActivityLeaderboardBinding
    private val viewModel: LeaderBoardViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    override fun onResume() {
        super.onResume()
        setupListeners()
        setupObservers()
        viewModel.getBoards()
    }

    private fun setupView() = with(binding){

        tabLayoutLeaderBoard.addTab(tabLayoutLeaderBoard.newTab().setCustomView(R.layout.layout_tab_board_all_time_selected))
        tabLayoutLeaderBoard.addTab(tabLayoutLeaderBoard.newTab().setCustomView(R.layout.layout_tab_board_today_selected))
        tabLayoutLeaderBoard.addTab(tabLayoutLeaderBoard.newTab().setCustomView(R.layout.layout_tab_board_week_selected))
        tabLayoutLeaderBoard.addTab(tabLayoutLeaderBoard.newTab().setCustomView(R.layout.layout_tab_board_month_selected))

        val adapter = TabAdapter(this@LeaderBoardActivity)
        viewPagerLeaderBoard.adapter = adapter

        tabLayoutLeaderBoard.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPagerLeaderBoard.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        viewPagerLeaderBoard.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                tabLayoutLeaderBoard.selectTab(tabLayoutLeaderBoard.getTabAt(position))
            }
        })

    }

    private fun setupListeners(){
        binding.btnBackBoard.onDebouncedListener {
            finish()
        }

        binding.btnRefreshBoard?.onDebouncedListener {
            viewModel.getBoards()
        }
    }

    private fun setupObservers(){
        viewModel.boards.observe(this, Observer{ boards ->
            showData(boards = boards)
        })

        viewModel.error.observe(this, Observer { it ->
            DialogError(
                this@LeaderBoardActivity,
                it.errorCode!!,
                object : DialogError.ClickListenerDialogError{
                    override fun reloadClickListener() {
                        viewModel.getBoards()
                    }
                }
            )
        })

        viewModel.loading.observe(this, Observer { it ->
            if(it){
                binding.loadingBoxBoard.visibility = View.VISIBLE
            }else{
                binding.loadingBoxBoard.visibility = View.GONE
            }
        })
    }

    private fun showData(boards: Boards){

    }

    override fun onBackPressed() {
        finish()
    }

}