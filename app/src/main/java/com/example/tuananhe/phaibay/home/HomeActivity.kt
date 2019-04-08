package com.example.tuananhe.phaibay.home

import android.arch.lifecycle.ViewModelProviders
import android.content.IntentFilter
import android.databinding.DataBindingUtil
import com.example.tuananhe.phaibay.R
import com.example.tuananhe.phaibay.base.BaseActivity
import com.example.tuananhe.phaibay.databinding.ActivityHomeBinding
import com.example.tuananhe.phaibay.floatingbubble.BubbleReceiver
import com.example.tuananhe.phaibay.floatingbubble.BubbleReceiver.Action.BUBBLE_STARTED

class HomeActivity : BaseActivity() {

    private lateinit var homeActivityBinding: ActivityHomeBinding
    private lateinit var homeModel: HomeModel
    private var bubbleReceiver = BubbleReceiver()

    override fun initBinding(id: Int) {
        homeActivityBinding = DataBindingUtil.setContentView(this, getContentLayout())
        homeModel = ViewModelProviders.of(this, HomeViewModelFactory(application, getNavigator()))
            .get(HomeModel::class.java)
        homeActivityBinding.viewModel = homeModel
    }

    override fun initView() {
    }

    override fun initComponent() {
//        registerReceiver(bubbleReceiver, IntentFilter(BUBBLE_STARTED))
//        bubbleReceiver.activity = this
        homeModel.startBubble()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
//        unregisterReceiver(bubbleReceiver)
    }

    override fun getContentLayout(): Int = R.layout.activity_home

}