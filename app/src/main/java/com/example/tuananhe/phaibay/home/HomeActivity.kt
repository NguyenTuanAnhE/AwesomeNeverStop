package com.example.tuananhe.phaibay.home

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.nfc.tech.NfcA
import com.example.tuananhe.phaibay.R
import com.example.tuananhe.phaibay.base.BaseActivity
import com.example.tuananhe.phaibay.base.Navigator
import com.example.tuananhe.phaibay.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity() {

    private lateinit var homeActivityBinding: ActivityHomeBinding
    private lateinit var homeModel: HomeModel

    override fun initBinding(id: Int) {
        homeActivityBinding = DataBindingUtil.setContentView(this, getContentLayout())
        homeModel = ViewModelProviders.of(this, HomeViewModelFactory(application, getNavigator()))
            .get(HomeModel::class.java)
        homeActivityBinding.viewModel = homeModel
    }

    override fun initView() {
    }

    override fun initComponent() {
    }

    override fun getContentLayout(): Int = R.layout.activity_home

}