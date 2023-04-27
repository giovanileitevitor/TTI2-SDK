package com.timwe.tti2sdk.ui.board

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.timwe.init.EventType
import com.timwe.init.EventValue
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.Boards
import com.timwe.tti2sdk.databinding.ActivityLeaderboardBinding
import com.timwe.tti2sdk.ui.board.adapter.TabAdapter
import com.timwe.tti2sdk.ui.dialog.DialogError
import com.timwe.utils.Utils
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
        viewModel.sendEvent(eventType = EventType.SCREEN_ACCESS, eventValue = EventValue.LEADERBOARD)
    }

    private fun setupView() = with(binding){
        var mFirstPageCalled = true
        tabLayoutLeaderBoard.addTab(tabLayoutLeaderBoard.newTab().setCustomView(R.layout.layout_tab_board_all_time_selected))
        tabLayoutLeaderBoard.addTab(tabLayoutLeaderBoard.newTab().setCustomView(R.layout.layout_tab_board_today_selected))
        tabLayoutLeaderBoard.addTab(tabLayoutLeaderBoard.newTab().setCustomView(R.layout.layout_tab_board_week_selected))
        tabLayoutLeaderBoard.addTab(tabLayoutLeaderBoard.newTab().setCustomView(R.layout.layout_tab_board_month_selected))

        val adapter = TabAdapter(this@LeaderBoardActivity)
        viewPagerLeaderBoard.adapter = adapter
        viewPagerLeaderBoard.currentItem = 0
        setTabSelected(0)

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
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                Utils.showLog("SDK", "onPageScrollStateChanged: $state")
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Utils.showLog("SDK", "onPageSelected: $position")
                tabLayoutLeaderBoard.selectTab(tabLayoutLeaderBoard.getTabAt(position))
                if(mFirstPageCalled){
                    mFirstPageCalled = false
                }else{
                    setTabSelected(position)
                    val myPossitonsUnselected: ArrayList<Int> = arrayListOf(0, 1, 2, 3)
                    myPossitonsUnselected.remove(position)
                    for (item in myPossitonsUnselected){
                        setTabUnSelected(item)
                    }
                }
            }
        })
    }

    private fun setTabSelected(position: Int){
        val layout = when(position){
            0 -> R.layout.layout_tab_board_all_time_selected
            1 -> R.layout.layout_tab_board_today_selected
            2 -> R.layout.layout_tab_board_week_selected
            3 -> R.layout.layout_tab_board_month_selected
            else -> 0
        }
        binding.tabLayoutLeaderBoard.getTabAt(position)?.setCustomView(layout)
    }

    private fun setTabUnSelected(position: Int){
        val layout = when(position){
            0 -> R.layout.layout_tab_board_all_time_unselected
            1 -> R.layout.layout_tab_board_today_unselected
            2 -> R.layout.layout_tab_board_week_unselected
            3 -> R.layout.layout_tab_board_month_unselected
            else -> 0
        }
        binding.tabLayoutLeaderBoard.getTabAt(position)?.setCustomView(layout)
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
                binding.blankContainer?.visibility = View.VISIBLE
                binding.loadingBoxBoard.visibility = View.VISIBLE
            }else{
                binding.blankContainer?.visibility = View.GONE
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